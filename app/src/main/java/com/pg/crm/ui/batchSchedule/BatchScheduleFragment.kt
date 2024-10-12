package com.pg.crm.ui.batchSchedule

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pg.crm.R
import com.pg.crm.base.BaseFragment
import com.pg.crm.databinding.FragmentBatchScheduleBinding
import com.pg.crm.model.BatchScheduleCustomerProcessDetailsList
import com.pg.crm.model.BatchScheduleGridResponseItem
import com.pg.crm.model.BatchScheduleMasterDetailsModel
import com.pg.crm.model.BatchScheduleMasterDetailsModel1
import com.pg.crm.model.GetBatchResponse
import com.pg.crm.model.MaterialLocationData
import com.pg.crm.ui.main.MainViewModel
import com.pg.crm.ui.materialInward.MaterialInwardLocationAdapter
import com.pg.crm.ui.materialInward.MaterialTypeAdapter
import com.pg.crm.utils.AppUtils
import com.pg.crm.utils.Constants
import com.pg.crm.utils.NetworkResult
import com.pg.crm.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_batch_schedule.inwardPendingDetail_LL
import kotlinx.android.synthetic.main.fragment_weightment.inwardPending_detail_rv
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class BatchScheduleFragment : BaseFragment<FragmentBatchScheduleBinding, BatchSchViewModel>(),
    TextView.OnEditorActionListener,
    BatchSchCustomerMaterialAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val batchSchViewModel: BatchSchViewModel by viewModels()
    lateinit var fragmentBatchScheduleBinding: FragmentBatchScheduleBinding

    override fun getBindingVariable(): Int = BR.batchSchViewModel

    override fun getLayoutId(): Int = R.layout.fragment_batch_schedule

    override fun getViewModel(): BatchSchViewModel {
        return batchSchViewModel
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this
    lateinit var materialTypeACTAdapter: MaterialTypeAdapter
    private var sendingrequestdate: String = ""
    private var sendingrequestDetaildate: String = ""
    private var org_no: String = ""
    private var cp_Process_Stage_No: String = ""
    private var process_Machine_No: String = ""
    private var process_Name_Code: String = ""
    lateinit var materialInwardLocationAdapter: MaterialInwardLocationAdapter
    private lateinit var materialLocation: ArrayList<MaterialLocationData>
    private var batchListSend = (arrayListOf<BatchScheduleCustomerProcessDetailsList>())
    private lateinit var batchlistDetails: BatchScheduleMasterDetailsModel
    var sum = 0.0
    var tankNo = ""
    val batchShiftType = arrayListOf<String>("General Shift", "Shift A", "Shift B", "Shift C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBatchScheduleBinding = getViewDataBinding()

        activity?.run {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.updateActionBarTitle(getString(R.string.batch_schedule))
            viewModel.checkIsVisible(false)
        } ?: throw Throwable("invalid activity")
        setupUI()
        checkEnableDisableSaveBt()
    }

    private fun checkEnableDisableSaveBt() {

        fragmentBatchScheduleBinding.remarkET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (fragmentBatchScheduleBinding.remarkET.text.toString().isEmpty()) {

                    fragmentBatchScheduleBinding.saveBT.isEnabled = true
                    fragmentBatchScheduleBinding.saveBT.setBackgroundColor(
                        fragmentBatchScheduleBinding.saveBT.context.resources
                            .getColor(R.color.colorPrimaryDark)
                    )

                    fragmentBatchScheduleBinding.newBatchBT.isEnabled = false
                    fragmentBatchScheduleBinding.newBatchBT.setBackgroundColor(
                        fragmentBatchScheduleBinding.newBatchBT.context.resources
                            .getColor(R.color.button_fade)
                    )

                    fragmentBatchScheduleBinding.findBT.isEnabled = false
                    fragmentBatchScheduleBinding.findBT.setBackgroundColor(
                        fragmentBatchScheduleBinding.findBT.context.resources
                            .getColor(R.color.button_fade)
                    )

                    fragmentBatchScheduleBinding.modifyBT.isEnabled = false
                    fragmentBatchScheduleBinding.modifyBT.setBackgroundColor(
                        fragmentBatchScheduleBinding.modifyBT.context.resources
                            .getColor(R.color.button_fade)
                    )

                }
            } else {

                fragmentBatchScheduleBinding.newBatchBT.isEnabled = true
                fragmentBatchScheduleBinding.newBatchBT.setBackgroundColor(
                    fragmentBatchScheduleBinding.newBatchBT.context.resources
                        .getColor(R.color.colorPrimaryDark)
                )

                //fragmentBatchScheduleBinding.saveBT.isEnabled = false
               /* fragmentBatchScheduleBinding.saveBT.setBackgroundColor(
                    fragmentBatchScheduleBinding.saveBT.context.resources
                        .getColor(R.color.button_fade)
                )*/

                fragmentBatchScheduleBinding.findBT.isEnabled = false
                fragmentBatchScheduleBinding.findBT.setBackgroundColor(
                    fragmentBatchScheduleBinding.findBT.context.resources
                        .getColor(R.color.button_fade)
                )

                fragmentBatchScheduleBinding.modifyBT.isEnabled = false
                fragmentBatchScheduleBinding.modifyBT.setBackgroundColor(
                    fragmentBatchScheduleBinding.modifyBT.context.resources
                        .getColor(R.color.button_fade)
                )

            }
        }

    }

    private fun setupUI() {

        checkMaterialLocationsResponse()
        checkCustomerGridResponse()
        checkBatchNoResponse()
        checkSaveInwardBatchProductResponse()
        checkScheduleBatchesGridResponse()

        fragmentBatchScheduleBinding.clearBT.setOnClickListener {
            emptyAllFields()
        }
        batchShiftType.let {
            materialTypeACTAdapter = MaterialTypeAdapter(
                requireActivity(), batchShiftType
            )
            fragmentBatchScheduleBinding.shift.setText(batchShiftType.get(0))
            fragmentBatchScheduleBinding.shift.setAdapter(
                materialTypeACTAdapter
            )
        }
        fragmentBatchScheduleBinding.datetimeET.setOnClickListener {
            if (tankNo != "") {
                mViewModel.getScheduledBatchesDetails(tankNo = tankNo)
            }

        }
        fragmentBatchScheduleBinding.saveBT.setOnClickListener {
            checkValidation()
        }
        fragmentBatchScheduleBinding.newBatchBT.setOnClickListener {
            fragmentBatchScheduleBinding.newBatchBT.isEnabled = false
            fragmentBatchScheduleBinding.newBatchBT.setBackgroundColor(
                fragmentBatchScheduleBinding.newBatchBT.context.resources
                    .getColor(R.color.button_fade)
            )

            fragmentBatchScheduleBinding.saveBT.isEnabled = false
            fragmentBatchScheduleBinding.saveBT.setBackgroundColor(
                fragmentBatchScheduleBinding.saveBT.context.resources
                    .getColor(R.color.button_fade)
            )

            fragmentBatchScheduleBinding.findBT.isEnabled = false
            fragmentBatchScheduleBinding.findBT.setBackgroundColor(
                fragmentBatchScheduleBinding.findBT.context.resources
                    .getColor(R.color.button_fade)
            )

            fragmentBatchScheduleBinding.modifyBT.isEnabled = false
            fragmentBatchScheduleBinding.modifyBT.setBackgroundColor(
                fragmentBatchScheduleBinding.modifyBT.context.resources
                    .getColor(R.color.button_fade)
            )

            batchSchViewModel.getMaterialLocations()   // top grid

            val c = Calendar.getInstance().time

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val current = LocalDateTime.now()
                sendingrequestdate = current.toString()
            } else {
                val tz: TimeZone = TimeZone.getTimeZone("UTC")
                val df: DateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'") // Quoted "Z" to indicate UTC, no timezone offset

                df.setTimeZone(tz)
                sendingrequestdate = df.format(Date())
            }
            val df2 = SimpleDateFormat("EEE MM/dd/yyyy", Locale.getDefault())
            sendingrequestDetaildate = df2.format(c)

            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate: String = df.format(c)

            val dateAndTime: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
            val dateAndTimeValue: String = dateAndTime.format(c)

            fragmentBatchScheduleBinding.datetimeET.setText(dateAndTimeValue)
            fragmentBatchScheduleBinding.proposedSchDateET.setText(formattedDate)

            fragmentBatchScheduleBinding.enteredByET.setText(
                Prefs.getString(
                    Constants.USER_NAME, ""
                )
            )

            fragmentBatchScheduleBinding.locationACT.isEnabled = true
            fragmentBatchScheduleBinding.processGroupET.isEnabled = true
            fragmentBatchScheduleBinding.processNameET.isEnabled = true
            fragmentBatchScheduleBinding.programeNoET.isEnabled = true
            fragmentBatchScheduleBinding.capacityET.isEnabled = true
            fragmentBatchScheduleBinding.totalQuantityET.isEnabled = true
            fragmentBatchScheduleBinding.remarkET.isEnabled = true
            fragmentBatchScheduleBinding.proposedSchTimeET.isEnabled = true
            fragmentBatchScheduleBinding.programDurationET.isEnabled = true
            fragmentBatchScheduleBinding.batchSchNoET.isEnabled = true
            fragmentBatchScheduleBinding.datetimeET.isEnabled = true
            fragmentBatchScheduleBinding.enteredByET.isEnabled = true
        }

        fragmentBatchScheduleBinding.proposedSchTimeET.setClickable(true)
        fragmentBatchScheduleBinding.proposedSchTimeET.setLongClickable(false)
        fragmentBatchScheduleBinding.proposedSchTimeET.setInputType(InputType.TYPE_NULL)

        fragmentBatchScheduleBinding.proposedSchTimeET.setOnClickListener({ showTimePicker() })


        fragmentBatchScheduleBinding.locationACT.setOnClickListener {
            if (fragmentBatchScheduleBinding.locationACT.text.isEmpty()) {
                fragmentBatchScheduleBinding.locationACT.showDropDown()
            } else {

                fragmentBatchScheduleBinding.locationACT.showDropdown(materialInwardLocationAdapter)
            }
        }

        fragmentBatchScheduleBinding.locationACT.setOnItemClickListener { _, _, i, _ ->
            fragmentBatchScheduleBinding.locationACT.setText(materialLocation[i].orgCode.toString() + "," + materialLocation[i].orgOfficeName!!)

            org_no = materialLocation[i].orgOfficeNo.toString()
            batchSchViewModel.getBatchNo(materialLocation[i].orgOfficeNo.toString())
        }

        fragmentBatchScheduleBinding.shift.setOnItemClickListener { _, _, i, _ ->
            fragmentBatchScheduleBinding.shift.setText(batchShiftType.get(i))
        }

        fragmentBatchScheduleBinding.shift.setOnClickListener {
            if (fragmentBatchScheduleBinding.shift.text.isEmpty()) {
                fragmentBatchScheduleBinding.shift.showDropDown()
            } else {

                fragmentBatchScheduleBinding.shift.showDropdown(materialTypeACTAdapter)
            }
        }
    }

    private fun showScheduleBatchDialog(data: ArrayList<BatchScheduleGridResponseItem>) {
        val dialog = Dialog(requireActivity())
        val window = dialog.window
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_schedule_batches)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)
        val rvScheduleBatches: RecyclerView = dialog.findViewById(R.id.rvScheduleBatches)
        val tvClose: TextView = dialog.findViewById(R.id.tvClose)
        tvClose.setOnClickListener {
            dialog.dismiss()
        }
        val adapter =
            ScheduleBatchesAdapter(requireContext(), data)
        rvScheduleBatches.setHasFixedSize(true)
        rvScheduleBatches.addItemDecoration(
            DividerItemDecoration(
                inwardPending_detail_rv.context, DividerItemDecoration.VERTICAL
            )
        )
        rvScheduleBatches.adapter = adapter

        dialog.show()
    }


    private fun AutoCompleteTextView.showDropdown(adapter: MaterialTypeAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter.filter.filter(null)
            showDropDown()
        }
    }


    private fun showTimePicker() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                fragmentBatchScheduleBinding.proposedSchTimeET.setText(
                    String.format("%d:%d", hourOfDay, minute) + ":00"
                )
            }
        }, hour, minute, false)

        mTimePicker.show()
    }

    private fun AutoCompleteTextView.showDropdown(adapter: MaterialInwardLocationAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter.filter.filter(null)
            showDropDown()
        }
    }

    private fun checkScheduleBatchesGridResponse() {


        batchSchViewModel.scheduleList.observe(requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        showScheduleBatchDialog(response.data)
                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentBatchScheduleBinding.newBatchBT.isEnabled = true

                    Toast.makeText(
                        requireActivity(), response.message, Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    AppUtils.showLoadingDialog(requireActivity())
                }
            }
        }
    }

    private fun checkMaterialLocationsResponse() {


        batchSchViewModel.response.observe(requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        if (it.IsSucceed!!) {
                            materialLocation = ArrayList()
                            materialLocation = it.Data
                            materialInwardLocationAdapter = MaterialInwardLocationAdapter(
                                requireActivity(), materialLocation
                            )
                            fragmentBatchScheduleBinding.locationACT.setAdapter(
                                materialInwardLocationAdapter
                            )

                            fragmentBatchScheduleBinding.proposedSchTimeET.isEnabled = true
                            fragmentBatchScheduleBinding.shift.isEnabled = true
                            fragmentBatchScheduleBinding.processGroupET.isEnabled = false
                            fragmentBatchScheduleBinding.processNameET.isEnabled = false
                            fragmentBatchScheduleBinding.programDurationET.isEnabled = true
                            fragmentBatchScheduleBinding.programeNoET.isEnabled = false
                            fragmentBatchScheduleBinding.totalQuantityET.isEnabled = false
                            fragmentBatchScheduleBinding.tankMachineET.isEnabled = false
                            fragmentBatchScheduleBinding.capacityET.isEnabled = false
                            fragmentBatchScheduleBinding.remarkET.isEnabled = true


                        }

                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentBatchScheduleBinding.newBatchBT.isEnabled = true

                    Toast.makeText(
                        requireActivity(), response.message, Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    AppUtils.showLoadingDialog(requireActivity())
                }
            }
        }
    }

    private fun checkCustomerGridResponse() {
        batchSchViewModel.batchResponse.observe(requireActivity(),
            { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.let {
                            Log.d("size", it.size.toString())
                            inwardPendingDetail_LL.visibility = View.VISIBLE

                            val batchSchAdapter =
                                BatchSchCustomerMaterialAdapter(requireContext(), it, this)
                            inwardPending_detail_rv.setHasFixedSize(true)
                            inwardPending_detail_rv.layoutManager =
                                LinearLayoutManager(requireContext())
                            inwardPending_detail_rv.addItemDecoration(
                                DividerItemDecoration(
                                    inwardPending_detail_rv.context, DividerItemDecoration.VERTICAL
                                )
                            )
                            inwardPending_detail_rv?.adapter = batchSchAdapter
                        }

                    }

                    is NetworkResult.Error -> {

                        Toast.makeText(
                            requireActivity(), response.message, Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {

                    }
                }
            })
    }

    private fun checkBatchNoResponse() {
        batchSchViewModel.batchNo.observe(requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let {

                        fragmentBatchScheduleBinding.batchSchNoET.setText((it))
                        batchSchViewModel.getBatchDetails(org_no, sendingrequestDetaildate)
                    }

                }

                is NetworkResult.Error -> {

                    Toast.makeText(
                        requireActivity(), response.message, Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {

                }
            }
        }
    }


    private fun checkValidation() {
        if (fragmentBatchScheduleBinding.proposedSchTimeET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.proposedSchTimeET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.processGroupET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.processGroupET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.processNameET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.processNameET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.tankMachineET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.tankMachineET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.capacityET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.capacityET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.programeNoET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.programeNoET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.programDurationET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.programDurationET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.totalQuantityET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.totalQuantityET.error = "filled not be empty"
        } else if (fragmentBatchScheduleBinding.remarkET.text.isNullOrEmpty()) {
            fragmentBatchScheduleBinding.remarkET.error = "filled not be empty"
        } else {
            val test = BatchScheduleMasterDetailsModel1(
                batchScheduleMasterDetailsModel(),
                batchScheduleCustomerProcessDetailsList()
            )
            Log.i("TAG", "checkValidation: " + test.toString())
            batchSchViewModel.saveBatchSch(
                BatchScheduleMasterDetailsModel1(
                    batchScheduleMasterDetailsModel(),
                    batchScheduleCustomerProcessDetailsList()
                )
            )
        }

    }

    fun batchScheduleCustomerProcessDetailsList(): ArrayList<BatchScheduleCustomerProcessDetailsList> {
        val list = ArrayList<BatchScheduleCustomerProcessDetailsList>()
        val availableMaterialList = batchSchViewModel.batchResponse.value as? NetworkResult.Success
        availableMaterialList?.data?.forEach {
            if (it.isSelected) {
                list.add(
                    BatchScheduleCustomerProcessDetailsList(
                        it.itemwiseEffectiveProcessStages.CP_Process_Stage_No.toString(),
                        it.inwardWeighingProductsDetails.Net_Weight_Material!!,
                        it.inwardWeighingProductsDetails.Weighing_Prod_Sl_No.toString(),
                        it.inwardWeighingMasterDetails.Weighing_Sl_No.toString(),
                        "0",
                        "0",
                        it.inwardWeighingProductsDetails.Net_Weight_Material_Charged.toString()

                    )
                )
            }
        }


        return list
    }

    fun batchScheduleMasterDetailsModel(): BatchScheduleMasterDetailsModel {
        return BatchScheduleMasterDetailsModel(
            fragmentBatchScheduleBinding.batchSchNoET.text.toString().toInt(),
            org_no.toInt(),
            Prefs.getString(Constants.EMPLOYEE_ID).toInt(),
            sendingrequestdate,
            sendingrequestdate,
            fragmentBatchScheduleBinding.proposedSchTimeET.text.toString(),
            fragmentBatchScheduleBinding.shift.text.toString(),
            process_Name_Code,
            cp_Process_Stage_No,
            sum,
            process_Machine_No,
            fragmentBatchScheduleBinding.remarkET.text.toString()
        )

//        batchSchViewModel.saveBatchSch(
//            BatchScheduleMasterDetailsModel1(
//                BatchScheduleMasterDetailsModel(
//                    fragmentBatchScheduleBinding.batchSchNoET.text.toString().toInt(),
//                    org_no.toInt(),
//                    1,
//                    sendingrequestdate,
//                    sendingrequestdate,
//                    fragmentBatchScheduleBinding.proposedSchTimeET.text.toString(),
//                    fragmentBatchScheduleBinding.shift.text.toString(),
//                    process_Name_Code,
//                    cp_Process_Stage_No,
//                    sum,
//                    process_Machine_No,
//                    fragmentBatchScheduleBinding.remarkET.text.toString()
//                )
//            ),
//            batchListSend
//        )

    }

    fun calculateQuantity() {
        var qtyTotal = 0.0
        sum = 0.0
        var qtyTotalModified = 0.0

        val availableMaterialList = batchSchViewModel.batchResponse.value as? NetworkResult.Success
        availableMaterialList?.data?.forEach {
            if (it.isSelected) {
                qtyTotal += it.inwardWeighingProductsDetails.Net_Weight_Material!!
                qtyTotalModified += it.inwardWeighingProductsDetails.Net_Weight_Material_Charged!!
                fragmentBatchScheduleBinding.totalQuantityET.setText(
                    String.format("%.2f", qtyTotal).toDouble()
                        .toString() + " / " + String.format("%.2f", qtyTotalModified).toDouble()
                        .toString()
                )
            }
        }
        sum = qtyTotal

    }


    private fun checkSaveInwardBatchProductResponse() {
        batchSchViewModel.saveBatchResponse.observe(requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let {
                        Toast.makeText(
                            requireActivity(),
                            "Batch Scheduler Data Successfully Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                        emptyAllFields()
                        AppUtils.hideDialog()

                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    Toast.makeText(
                        requireActivity(), response.message, Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    AppUtils.showLoadingDialog(requireActivity())
                }
            }
        }
    }


    private fun emptyAllFields() {
        fragmentBatchScheduleBinding.saveBT.isEnabled = false
        fragmentBatchScheduleBinding.saveBT.setBackgroundColor(
            fragmentBatchScheduleBinding.saveBT.context.resources
                .getColor(R.color.button_fade)
        )

        fragmentBatchScheduleBinding.newBatchBT.isEnabled = true
        fragmentBatchScheduleBinding.newBatchBT.setBackgroundColor(
            fragmentBatchScheduleBinding.newBatchBT.context.resources
                .getColor(R.color.colorPrimaryDark)
        )

        fragmentBatchScheduleBinding.processGroupET.setText("")
        fragmentBatchScheduleBinding.processNameET.setText("")
        fragmentBatchScheduleBinding.programeNoET.setText("")
        fragmentBatchScheduleBinding.capacityET.setText("")
        fragmentBatchScheduleBinding.totalQuantityET.setText("")
        fragmentBatchScheduleBinding.remarkET.setText("")
        fragmentBatchScheduleBinding.proposedSchTimeET.setText("")
        fragmentBatchScheduleBinding.programDurationET.setText("")
        fragmentBatchScheduleBinding.batchSchNoET.setText("")
        fragmentBatchScheduleBinding.datetimeET.setText("")
        fragmentBatchScheduleBinding.enteredByET.setText("")


        fragmentBatchScheduleBinding.locationACT.isEnabled = false
        fragmentBatchScheduleBinding.processGroupET.isEnabled = false
        fragmentBatchScheduleBinding.processNameET.isEnabled = false
        fragmentBatchScheduleBinding.programeNoET.isEnabled = false
        fragmentBatchScheduleBinding.capacityET.isEnabled = false
        fragmentBatchScheduleBinding.totalQuantityET.isEnabled = false
        fragmentBatchScheduleBinding.remarkET.isEnabled = false
        fragmentBatchScheduleBinding.proposedSchTimeET.isEnabled = false
        fragmentBatchScheduleBinding.programDurationET.isEnabled = false
        fragmentBatchScheduleBinding.batchSchNoET.isEnabled = false
        fragmentBatchScheduleBinding.datetimeET.isEnabled = false
        fragmentBatchScheduleBinding.enteredByET.isEnabled = false

        fragmentBatchScheduleBinding.inwardPendingDetailLL.visibility = View.GONE
        materialLocation.clear()


        fragmentBatchScheduleBinding.locationACT.setText("")
        fragmentBatchScheduleBinding.shift.setText("")
    }

    override fun onItemClick(getBatchResponse: GetBatchResponse, position: Int) {
        fragmentBatchScheduleBinding.processGroupET.setText(getBatchResponse.processGroupDetails.Process_Group_Name.toString())
        fragmentBatchScheduleBinding.processNameET.setText(getBatchResponse.processNameDetails.Process_Name.toString())
        fragmentBatchScheduleBinding.programeNoET.setText(getBatchResponse.processMasterDetails.ProgrammeNo.toString())

        fragmentBatchScheduleBinding.tankMachineET.setText(
            getBatchResponse.processMachinesDetailsEntity.Machine_Tank_No.orEmpty()
        )
        fragmentBatchScheduleBinding.capacityET.setText(
            String.format("%.2f", getBatchResponse.processMachinesDetailsEntity.Machine_Capacity)
                .toDouble()
                .toString()
        )

        tankNo = getBatchResponse.Process_Machine_No.toString()
        calculateQuantity()
        /*
                if (data.isSelected) {
                    sum += data.inwardWeighingProductsDetails.Net_Weight_Material!!

                }*/
        cp_Process_Stage_No =
            getBatchResponse.itemwiseEffectiveProcessStages.CP_Process_Stage_No.toString()
        process_Name_Code =
            getBatchResponse.itemwiseEffectiveProcessStages.Process_Name_Code.toString()
        process_Machine_No = getBatchResponse.Process_Machine_No.toString()

    }

    override fun onQuantityModified(getBatchResponse: GetBatchResponse, position: Int) {
        calculateQuantity()
    }

    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        return false
    }

}