package com.pg.crm.ui.hardeningVacuum

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.crm.R
import com.pg.crm.base.BaseFragment
import com.pg.crm.databinding.FragmentHardeningVacuumBinding
import com.pg.crm.interfaces.OnItemClickListener
import com.pg.crm.model.HTHardeningVacuumeProductionDetailsModel
import com.pg.crm.model.HTVacuumeProductionCompletedDetailsModel
import com.pg.crm.model.HardeningProcessVacuumPartialRequest
import com.pg.crm.model.HtVacuumBatchDetailsResponseItem
import com.pg.crm.model.MaterialLocationData
import com.pg.crm.model.SaveInitialRequest
import com.pg.crm.ui.main.MainViewModel
import com.pg.crm.ui.materialInward.MaterialInwardLocationAdapter
import com.pg.crm.utils.AppUtils
import com.pg.crm.utils.Constants
import com.pg.crm.utils.NetworkResult
import com.pg.crm.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class HardeningVacuumFragment :
    BaseFragment<FragmentHardeningVacuumBinding, HardeningVacuumViewModel>(),
    OnItemClickListener<HtVacuumBatchDetailsResponseItem> {
    private val hardeningVacuumViewModel: HardeningVacuumViewModel by viewModels()
    lateinit var fragmentHardeningVacuumBinding: FragmentHardeningVacuumBinding
    private lateinit var materialLocation: java.util.ArrayList<MaterialLocationData>
    lateinit var locationAdapter: MaterialInwardLocationAdapter
    private var sendingrequestDetaildate: String = ""
    private var sendingrequestdate: String = ""
    var orgOfficeNo = 0
    var dataEntryNumber = ""
    var batchScheduleNo = 0
    var htProductionOnID = 0
    var isInitialSave = true
    override fun getBindingVariable(): Int = BR.hardeningVacuumViewModel

    override fun getLayoutId(): Int = R.layout.fragment_hardening_vacuum

    override fun getViewModel(): HardeningVacuumViewModel {
        return hardeningVacuumViewModel
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHardeningVacuumBinding = getViewDataBinding()

        activity?.run {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.updateActionBarTitle(getString(R.string.hardening_process_vaccum))
            viewModel.checkIsVisible(false)

        } ?: throw Throwable("invalid activity")

        setUpUi()
    }

    private fun setUpUi() {
        checkMaterialLocationsResponse()
        checkGridBatchFromLocationResponse()
        checkSaveInitialDataResponse()
        checkSavePartialDataResponse()
        observeFieldsForPartials()

        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_dropdown_item_1line,
            hardeningVacuumViewModel.statusValues
        )
        fragmentHardeningVacuumBinding.statusACT.setAdapter(adapter)

        // Set the default selection to "Pending"
        fragmentHardeningVacuumBinding.statusACT.setText("Pending", false)

        // Optionally, you can set an item click listener to handle user selection
        fragmentHardeningVacuumBinding.statusACT.setOnItemClickListener { _, _, position, _ ->
            val selectedStatus = adapter.getItem(position)
            // Handle the selected status
        }


        val cycleInAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_dropdown_item_1line,
            hardeningVacuumViewModel.cycleRunningInValues
        )
        fragmentHardeningVacuumBinding.cycleRunningIn.setAdapter(cycleInAdapter)

        // Optionally, you can set an item click listener to handle user selection
        fragmentHardeningVacuumBinding.cycleRunningIn.setOnItemClickListener { _, _, position, _ ->
            val selectedStatus = cycleInAdapter.getItem(position)
            // Handle the selected status
        }


        fragmentHardeningVacuumBinding.locationACT.setOnItemClickListener { _, _, i, _ ->
            fragmentHardeningVacuumBinding.locationACT.setText(materialLocation[i].orgCode.toString() + "," + materialLocation[i].orgOfficeName!!)
            orgOfficeNo = materialLocation[i].orgOfficeNo!!
            hardeningVacuumViewModel.getHtProcessVacuumBatchDetails(materialLocation[i].orgOfficeNo.toString())

        }


        fragmentHardeningVacuumBinding.startProcessBT.setOnClickListener {
            fragmentHardeningVacuumBinding.startProcessBT.isEnabled = false
            fragmentHardeningVacuumBinding.startProcessBT.setBackgroundColor(
                fragmentHardeningVacuumBinding.startProcessBT.context.resources
                    .getColor(R.color.button_fade)
            )
            hardeningVacuumViewModel.getMaterialLocations()


            fragmentHardeningVacuumBinding.enteredByET.setText(
                Prefs.getString(
                    Constants.USER_NAME, ""
                )
            )

            fragmentHardeningVacuumBinding.statusACT.isEnabled = true
            fragmentHardeningVacuumBinding.locationACT.isEnabled = true
            fragmentHardeningVacuumBinding.cycleRunningIn.isEnabled = true


            val c = Calendar.getInstance().time

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Using java.time APIs for Android API level 26 (Oreo) and above
                val current = LocalDateTime.now()
                sendingrequestdate = current.toString()
            } else {
                // For older Android versions
                val tz: TimeZone = TimeZone.getTimeZone("UTC")
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.getDefault())
                df.timeZone = tz
                sendingrequestdate = df.format(c)
            }

// Correct the date format to get the day of the month
            val df2 = SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault())
            sendingrequestDetaildate = df2.format(c)

            val dateAndTime: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
            val dateAndTimeValue: String = dateAndTime.format(c)

            fragmentHardeningVacuumBinding.datetimeET.setText(dateAndTimeValue)

// If you want to set the same date and time value for both EditText fields, use the same value
            fragmentHardeningVacuumBinding.startTimeET.setText(dateAndTimeValue)



            fragmentHardeningVacuumBinding.endProcessBT.isEnabled = false
            fragmentHardeningVacuumBinding.findBT.isEnabled = false
            fragmentHardeningVacuumBinding.modifyBT.isEnabled = false
            fragmentHardeningVacuumBinding.inwardPendingDetailLLs.isVisible = true




            fragmentHardeningVacuumBinding.endProcessBT.setBackgroundColor(
                fragmentHardeningVacuumBinding.endProcessBT.context.resources
                    .getColor(R.color.button_fade)
            )

            fragmentHardeningVacuumBinding.saveBT.setBackgroundColor(
                fragmentHardeningVacuumBinding.saveBT.context.resources
                    .getColor(R.color.button_fade)
            )


            fragmentHardeningVacuumBinding.findBT.setBackgroundColor(
                fragmentHardeningVacuumBinding.findBT.context.resources
                    .getColor(R.color.button_fade)
            )


            fragmentHardeningVacuumBinding.modifyBT.setBackgroundColor(
                fragmentHardeningVacuumBinding.modifyBT.context.resources
                    .getColor(R.color.button_fade)
            )


        }

        fragmentHardeningVacuumBinding.clearBT.setOnClickListener {
            emptyAllFields()
        }

        fragmentHardeningVacuumBinding.statusACT.setOnClickListener {
            fragmentHardeningVacuumBinding.statusACT.showDropDown()
        }


        fragmentHardeningVacuumBinding.cycleRunningIn.setOnClickListener {
            fragmentHardeningVacuumBinding.cycleRunningIn.showDropDown()
        }

        fragmentHardeningVacuumBinding.locationACT.setOnClickListener {
            if (fragmentHardeningVacuumBinding.locationACT.text.isEmpty()) {
                fragmentHardeningVacuumBinding.locationACT.showDropDown()
            } else {

                fragmentHardeningVacuumBinding.locationACT.showDropdown(locationAdapter)
            }
        }


        fragmentHardeningVacuumBinding.loadingTimeInET.setClickable(true)
        fragmentHardeningVacuumBinding.loadingTimeInET.setLongClickable(false)
        fragmentHardeningVacuumBinding.loadingTimeInET.setInputType(InputType.TYPE_NULL)

        fragmentHardeningVacuumBinding.loadingTimeInET.setOnClickListener {
            showTimePicker(
                fragmentHardeningVacuumBinding.loadingTimeInET
            )
            fragmentHardeningVacuumBinding.saveBT.isEnabled = true
            fragmentHardeningVacuumBinding.saveBT.setBackgroundColor(
                fragmentHardeningVacuumBinding.saveBT.context.resources
                    .getColor(R.color.colorPrimaryDark)
            )

        }


        /* fragmentHardeningVacuumBinding.startTimeET.setLongClickable(false)
         fragmentHardeningVacuumBinding.startTimeET.setInputType(InputType.TYPE_NULL)

         fragmentHardeningVacuumBinding.startTimeET.setOnClickListener {
             showTimePicker(
                 fragmentHardeningVacuumBinding.startTimeET
             )
         }

 */





        fragmentHardeningVacuumBinding.pistonPumpTime.setOnClickListener {
            openDateTimePicker(fragmentHardeningVacuumBinding.pistonPumpTime)

        }



        fragmentHardeningVacuumBinding.rootsPumpTimeET.setOnClickListener {
            openDateTimePicker(fragmentHardeningVacuumBinding.rootsPumpTimeET)

        }

        fragmentHardeningVacuumBinding.holdingPumpTimeET.setOnClickListener {
            openDateTimePicker(fragmentHardeningVacuumBinding.holdingPumpTimeET)

        }

        fragmentHardeningVacuumBinding.diffusionPumpTimeET.setOnClickListener {
            openDateTimePicker(fragmentHardeningVacuumBinding.diffusionPumpTimeET)

        }

        fragmentHardeningVacuumBinding.thyristorOnTimeET.setOnClickListener {
            openDateTimePicker(fragmentHardeningVacuumBinding.thyristorOnTimeET)

        }

        fragmentHardeningVacuumBinding.saveBT.setOnClickListener {
            if (isInitialSave) {
                checkValidation()
            } else {
                checkValidationPartialSave()
            }
        }

    }


    private fun observeFieldsForPartials() {


        fragmentHardeningVacuumBinding.pistonBarET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }
        fragmentHardeningVacuumBinding.pistonPumpTime.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.rootsPumpBarET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.rootsPumpTimeET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.holdingPumpBarET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }
        fragmentHardeningVacuumBinding.holdingPumpTimeET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }
        fragmentHardeningVacuumBinding.diffusionPumpBarET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }
        fragmentHardeningVacuumBinding.diffusionPumpTimeET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.thyristorBarET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.thyristorOnTimeET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.ln2InitialET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.ln2FinalET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.ln2FinalMB1ET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }

        fragmentHardeningVacuumBinding.ln2InitialMB1ET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }
        fragmentHardeningVacuumBinding.ln2FinalMB2ET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }
        fragmentHardeningVacuumBinding.ln2InitialMB2ET.doOnTextChanged { text, start, before, count ->
            enableEndTImeDuration()
        }


    }

    private fun enableEndTImeDuration() {
        if (fragmentHardeningVacuumBinding.pistonBarET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.pistonPumpTime.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.rootsPumpBarET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.rootsPumpTimeET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.holdingPumpBarET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.holdingPumpTimeET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.diffusionPumpBarET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.diffusionPumpTimeET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.thyristorBarET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.thyristorOnTimeET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.ln2InitialET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.ln2FinalET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.ln2FinalMB1ET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.ln2InitialMB1ET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.ln2FinalMB2ET.text.isNullOrEmpty()) {
            return
        } else if (fragmentHardeningVacuumBinding.ln2InitialMB2ET.text.isNullOrEmpty()) {
            return
        } else {
            val c = Calendar.getInstance().time
            val dateAndTime: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
            val dateAndTimeValue: String = dateAndTime.format(c)

            fragmentHardeningVacuumBinding.endTimeET.setText(dateAndTimeValue)

            fragmentHardeningVacuumBinding.durationET.setText(
                getTimeDifference(
                    fragmentHardeningVacuumBinding.startTimeET.text.toString(),
                    fragmentHardeningVacuumBinding.endTimeET.text.toString()
                )
            )
            fragmentHardeningVacuumBinding.saveBT.isEnabled = true

            fragmentHardeningVacuumBinding.saveBT.setBackgroundColor(
                fragmentHardeningVacuumBinding.saveBT.context.resources
                    .getColor(R.color.colorPrimaryDark)
            )


        }
    }

    private fun getTimeDifference(startDate: String, endDate: String): String {

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())

        val startDate = dateFormat.parse(startDate)
        val endDate = dateFormat.parse(endDate)


        val diffInMillis = endDate.time - startDate.time

        // Calculate time difference in hours and minutes
        val hours = diffInMillis / (1000 * 60 * 60)
        val minutes = (diffInMillis / (1000 * 60)) % 60

        // Format the result as "HH:mm"
        return String.format("%02d:%02d", hours, minutes)
    }


    private fun checkGridBatchFromLocationResponse() {

        hardeningVacuumViewModel.gridDataResponse.observe(
            requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        fragmentHardeningVacuumBinding.inwardPendingDetailLLs.visibility =
                            View.VISIBLE
                        val pendingBatchAdapter =
                            it.let { it1 ->
                                it1.let { it2 ->
                                    val pendingBatchDetailsAdapter = PendingBatchDetailsAdapter(
                                        requireContext(), it2.toList(), this
                                    )
                                    pendingBatchDetailsAdapter
                                }
                            }
                        fragmentHardeningVacuumBinding.pendingDetailCustomerRV.setHasFixedSize(
                            true
                        )
                        fragmentHardeningVacuumBinding.pendingDetailCustomerRV.layoutManager =
                            LinearLayoutManager(requireContext())
                        fragmentHardeningVacuumBinding.pendingDetailCustomerRV.addItemDecoration(
                            DividerItemDecoration(
                                fragmentHardeningVacuumBinding.pendingDetailCustomerRV.context,
                                DividerItemDecoration.VERTICAL
                            )
                        )
                        fragmentHardeningVacuumBinding.pendingDetailCustomerRV.adapter =
                            pendingBatchAdapter

                    }

                }

                is NetworkResult.Error -> {

                    AppUtils.hideDialog()
                    fragmentHardeningVacuumBinding.startProcessBT.isEnabled = true

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


    private fun checkValidation() {
        if (fragmentHardeningVacuumBinding.locationACT.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.locationACT.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.machineNoEt.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.machineNoEt.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.programNoEt.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.programNoEt.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.tempET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.tempET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.loadingTimeInET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.loadingTimeInET.error = "filled not be empty"
        } else {

            //call the api here
            hardeningVacuumViewModel.saveHTProcessVacuumBatchDetails(
                SaveInitialRequest(
                    fragmentHardeningVacuumBinding.loadingTimeInET.text.toString(),
                    fragmentHardeningVacuumBinding.machineNoEt.text.toString(),
                    "0",
                    fragmentHardeningVacuumBinding.startTimeET.text.toString(),
                    orgOfficeNo.toString(),
                    fragmentHardeningVacuumBinding.programNoEt.text.toString(),
                    Prefs.getString(Constants.EMPLOYEE_ID),
                    fragmentHardeningVacuumBinding.tempET.text.toString(),
                    dataEntryNumber, batchScheduleNo.toString()

                )
            )
        }

    }

    private fun checkValidationPartialSave() {
        if (fragmentHardeningVacuumBinding.pistonBarET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.pistonBarET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.pistonPumpTime.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.pistonPumpTime.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.rootsPumpBarET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.rootsPumpBarET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.rootsPumpTimeET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.rootsPumpTimeET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.holdingPumpBarET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.holdingPumpBarET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.holdingPumpTimeET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.holdingPumpTimeET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.diffusionPumpBarET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.diffusionPumpBarET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.diffusionPumpTimeET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.diffusionPumpTimeET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.thyristorBarET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.thyristorBarET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.thyristorOnTimeET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.thyristorOnTimeET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.ln2InitialET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.ln2InitialET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.ln2FinalET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.ln2FinalET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.ln2FinalMB1ET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.ln2FinalMB1ET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.ln2InitialMB1ET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.ln2InitialMB1ET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.ln2FinalMB2ET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.ln2FinalMB2ET.error = "filled not be empty"
        } else if (fragmentHardeningVacuumBinding.ln2InitialMB2ET.text.isNullOrEmpty()) {
            fragmentHardeningVacuumBinding.ln2InitialMB2ET.error = "filled not be empty"
        } else {

            //call the api here
            hardeningVacuumViewModel.SaveHTHardeningProductionProcessVacuumeDetails(
                HardeningProcessVacuumPartialRequest(
                    HTVacuumeProductionCompletedDetailsModel(
                        fragmentHardeningVacuumBinding.endTimeET.text.toString(),
                        timeToDecimal(fragmentHardeningVacuumBinding.durationET.text.toString()).toString(),
                        htProductionOnID.toString(),
                        "0",
                        batchScheduleNo.toString(),
                    ),
                    HTHardeningVacuumeProductionDetailsModel(
                        "0",
                        fragmentHardeningVacuumBinding.ln2FinalMB1ET.text.toString(),
                        fragmentHardeningVacuumBinding.ln2FinalET.text.toString(),
                        fragmentHardeningVacuumBinding.ln2FinalMB2ET.text.toString(),
                        fragmentHardeningVacuumBinding.ln2InitialMB2ET.text.toString(),
                        fragmentHardeningVacuumBinding.ln2InitialMB1ET.text.toString(),
                        fragmentHardeningVacuumBinding.rootsPumpTimeET.text.toString(),
                        htProductionOnID.toString(),
                        fragmentHardeningVacuumBinding.ln2InitialET.text.toString(),
                        fragmentHardeningVacuumBinding.thyristorOnTimeET.text.toString(),
                        fragmentHardeningVacuumBinding.pistonBarET.text.toString(),
                        fragmentHardeningVacuumBinding.holdingPumpBarET.text.toString(),
                        "0",
                        fragmentHardeningVacuumBinding.pistonPumpTime.text.toString(),
                        fragmentHardeningVacuumBinding.diffusionPumpTimeET.text.toString(),
                        fragmentHardeningVacuumBinding.rootsPumpBarET.text.toString(),
                        batchScheduleNo.toString(),
                        fragmentHardeningVacuumBinding.diffusionPumpBarET.text.toString(),
                        fragmentHardeningVacuumBinding.thyristorBarET.text.toString(),
                        fragmentHardeningVacuumBinding.holdingPumpTimeET.text.toString(),
                    )

                )
            )
        }

    }

    fun timeToDecimal(time: String): Double {
        val parts = time.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        val decimalMinutes = minutes.toDouble() / 60.0
        return hours.toDouble() + decimalMinutes
    }

    private fun checkMaterialLocationsResponse() {
        hardeningVacuumViewModel.response.observe(
            requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        if (it.IsSucceed!!) {
                            materialLocation = ArrayList()
                            materialLocation = it.Data
                            locationAdapter = MaterialInwardLocationAdapter(
                                requireActivity(), materialLocation
                            )
                            fragmentHardeningVacuumBinding.locationACT.setAdapter(
                                locationAdapter
                            )


                        }

                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentHardeningVacuumBinding.startProcessBT.isEnabled = true

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


    private fun showTimePicker(et: AppCompatEditText) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                et.setText(String.format("%d:%d", hourOfDay, minute))

            }
        }, hour, minute, false)

        mTimePicker.show()
    }

    fun openDateTimePicker(editText: EditText) {
        val cldr: Calendar = Calendar.getInstance()
        val currentYear: Int = cldr.get(Calendar.YEAR)
        val currentMonth: Int = cldr.get(Calendar.MONTH)
        val currentDay: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val currentHour: Int = cldr.get(Calendar.HOUR_OF_DAY)
        val currentMinute: Int = cldr.get(Calendar.MINUTE)

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val timePicker = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val selectedDateTime = Calendar.getInstance()
                        selectedDateTime.set(year, monthOfYear, dayOfMonth, hourOfDay, minute)

                        val outputFormat =
                            SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
                        val formattedDateTime = outputFormat.format(selectedDateTime.time)
                        editText.setText(formattedDateTime)
                    },
                    currentHour,
                    currentMinute,
                    true
                )
                timePicker.show()
            },
            currentYear,
            currentMonth,
            currentDay
        )
        datePicker.datePicker.minDate = cldr.timeInMillis // Optional: Set minimum date
        datePicker.show()
    }

    private fun AutoCompleteTextView.showDropdown(adapter: MaterialInwardLocationAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter.filter.filter(null)
            showDropDown()
        }
    }

    private fun emptyAllFields() {
        fragmentHardeningVacuumBinding.saveBT.isEnabled = false
        fragmentHardeningVacuumBinding.saveBT.setBackgroundColor(
            fragmentHardeningVacuumBinding.saveBT.context.resources
                .getColor(R.color.button_fade)
        )

        fragmentHardeningVacuumBinding.startProcessBT.isEnabled = true
        fragmentHardeningVacuumBinding.startProcessBT.setBackgroundColor(
            fragmentHardeningVacuumBinding.startProcessBT.context.resources
                .getColor(R.color.colorPrimaryDark)
        )

        fragmentHardeningVacuumBinding.locationACT.isEnabled = false
        fragmentHardeningVacuumBinding.statusACT.isEnabled = false
        fragmentHardeningVacuumBinding.processingShiftACT.isEnabled = false
        fragmentHardeningVacuumBinding.changeOverShiftACT.isEnabled = false
        fragmentHardeningVacuumBinding.cycleRunningIn.isEnabled = false

        fragmentHardeningVacuumBinding.locationACT.setText("")
        fragmentHardeningVacuumBinding.enteredByET.setText("")
        fragmentHardeningVacuumBinding.processingShiftACT.setText("")
        fragmentHardeningVacuumBinding.helpersET.setText("")
        fragmentHardeningVacuumBinding.helpers1ET.setText("")
        fragmentHardeningVacuumBinding.changeOverShiftACT.setText("")
        fragmentHardeningVacuumBinding.shiftEt.setText("")
        fragmentHardeningVacuumBinding.cycleRunningIn.setText("")
        fragmentHardeningVacuumBinding.fullLoadHeatingEt.setText("")
        fragmentHardeningVacuumBinding.fullLoadCoolingEt.setText("")
        fragmentHardeningVacuumBinding.datetimeET.setText("")
        fragmentHardeningVacuumBinding.machineNoEt.setText("")
        fragmentHardeningVacuumBinding.startTimeET.setText("")
        fragmentHardeningVacuumBinding.programNoEt.setText("")
        fragmentHardeningVacuumBinding.tempET.setText("")
        fragmentHardeningVacuumBinding.loadingTimeInET.setText("")
        fragmentHardeningVacuumBinding.pistonBarET.setText("")
        fragmentHardeningVacuumBinding.pistonPumpTime.setText("")
        fragmentHardeningVacuumBinding.rootsPumpBarET.setText("")
        fragmentHardeningVacuumBinding.rootsPumpTimeET.setText("")
        fragmentHardeningVacuumBinding.holdingPumpBarET.setText("")
        fragmentHardeningVacuumBinding.holdingPumpTimeET.setText("")
        fragmentHardeningVacuumBinding.diffusionPumpBarET.setText("")
        fragmentHardeningVacuumBinding.diffusionPumpTimeET.setText("")
        fragmentHardeningVacuumBinding.thyristorBarET.setText("")
        fragmentHardeningVacuumBinding.thyristorOnTimeET.setText("")
        fragmentHardeningVacuumBinding.ln2FinalET.setText("")
        fragmentHardeningVacuumBinding.ln2InitialET.setText("")
        fragmentHardeningVacuumBinding.ln2FinalMB1ET.setText("")
        fragmentHardeningVacuumBinding.ln2InitialMB1ET.setText("")
        fragmentHardeningVacuumBinding.ln2InitialMB2ET.setText("")
        fragmentHardeningVacuumBinding.ln2FinalMB2ET.setText("")
        fragmentHardeningVacuumBinding.endTimeET.setText("")
        fragmentHardeningVacuumBinding.durationET.setText("")


        fragmentHardeningVacuumBinding.pendingDetailCustomerRV.adapter = null
        fragmentHardeningVacuumBinding.datetimeET.isEnabled = false
        fragmentHardeningVacuumBinding.enteredByET.isEnabled = false

        fragmentHardeningVacuumBinding.inwardPendingDetailLLs.visibility = View.GONE
        materialLocation.clear()


    }

    private fun checkSaveInitialDataResponse() {
        hardeningVacuumViewModel.saveInitialReqResponse.observe(
            requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    isInitialSave = false
                    response.data?.let {

                        htProductionOnID = it
                        Toast.makeText(
                            requireActivity(),
                            "Production Process Details Entered Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        fragmentHardeningVacuumBinding.saveBT.isEnabled = false

                        fragmentHardeningVacuumBinding.saveBT.setBackgroundColor(
                            fragmentHardeningVacuumBinding.saveBT.context.resources
                                .getColor(R.color.button_fade)
                        )

                        val c = Calendar.getInstance().time

                        val dateAndTime: DateFormat =
                            SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
                        val dateAndTimeValue: String = dateAndTime.format(c)

                        fragmentHardeningVacuumBinding.pistonPumpTime.setText(dateAndTimeValue)
                        fragmentHardeningVacuumBinding.rootsPumpTimeET.setText(dateAndTimeValue)
                        fragmentHardeningVacuumBinding.holdingPumpTimeET.setText(dateAndTimeValue)
                        fragmentHardeningVacuumBinding.diffusionPumpTimeET.setText(dateAndTimeValue)
                        fragmentHardeningVacuumBinding.thyristorOnTimeET.setText(dateAndTimeValue)

                        fragmentHardeningVacuumBinding.pistonPumpTime.isEnabled = true
                        fragmentHardeningVacuumBinding.pistonBarET.isEnabled = true
                        fragmentHardeningVacuumBinding.rootsPumpTimeET.isEnabled = true
                        fragmentHardeningVacuumBinding.rootsPumpBarET.isEnabled = true
                        fragmentHardeningVacuumBinding.holdingPumpTimeET.isEnabled = true
                        fragmentHardeningVacuumBinding.holdingPumpBarET.isEnabled = true
                        fragmentHardeningVacuumBinding.diffusionPumpTimeET.isEnabled = true
                        fragmentHardeningVacuumBinding.diffusionPumpBarET.isEnabled = true
                        fragmentHardeningVacuumBinding.thyristorOnTimeET.isEnabled = true
                        fragmentHardeningVacuumBinding.thyristorBarET.isEnabled = true
                        fragmentHardeningVacuumBinding.ln2FinalET.isEnabled = true
                        fragmentHardeningVacuumBinding.ln2InitialET.isEnabled = true
                        fragmentHardeningVacuumBinding.ln2InitialMB1ET.isEnabled = true
                        fragmentHardeningVacuumBinding.ln2FinalMB1ET.isEnabled = true
                        fragmentHardeningVacuumBinding.ln2InitialMB2ET.isEnabled = true
                        fragmentHardeningVacuumBinding.ln2FinalMB2ET.isEnabled = true

                        //emptyAllFields()
                        AppUtils.hideDialog()


                    }
                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentHardeningVacuumBinding.startProcessBT.isEnabled = true
                    isInitialSave = true

                    Toast.makeText(
                        requireActivity(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    AppUtils.showLoadingDialog(requireActivity())
                }
            }
        }

    }

    private fun checkSavePartialDataResponse() {
        hardeningVacuumViewModel.savePartialReqResponse.observe(
            requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    //    isInitialSave = false
                    response.data?.let {
                        Toast.makeText(
                            requireActivity(),
                            "Production Process Details Entered Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        fragmentHardeningVacuumBinding.saveBT.isEnabled = false

                        fragmentHardeningVacuumBinding.saveBT.setBackgroundColor(
                            fragmentHardeningVacuumBinding.saveBT.context.resources
                                .getColor(R.color.button_fade)
                        )
                        //emptyAllFields()
                        AppUtils.hideDialog()

                    }
                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentHardeningVacuumBinding.startProcessBT.isEnabled = true
                    isInitialSave = true

                    Toast.makeText(
                        requireActivity(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    AppUtils.showLoadingDialog(requireActivity())
                }
            }
        }

    }


    override fun onItemClick(data: HtVacuumBatchDetailsResponseItem, sl: Int, position: Int) {
        dataEntryNumber = data.batchScheduleMasterDetails?.dataEntryDateTime.toString()
        batchScheduleNo = data.batchScheduleMasterDetails?.batchSchNo!!
        fragmentHardeningVacuumBinding.machineNoEt.isEnabled = true
        fragmentHardeningVacuumBinding.programNoEt.isEnabled = true
        fragmentHardeningVacuumBinding.tempET.isEnabled = true
        fragmentHardeningVacuumBinding.loadingTimeInET.isEnabled = true



        fragmentHardeningVacuumBinding.machineNoEt.setText(data.batchScheduleMasterDetails.processMachineNo.toString())
        fragmentHardeningVacuumBinding.programNoEt.setText(data.batchScheduleMasterDetails.cPProcessStageNo.toString())


    }


}
