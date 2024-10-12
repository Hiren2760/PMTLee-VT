package com.pg.crm.ui.hardening

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
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.pg.crm.R
import com.pg.crm.base.BaseFragment
import com.pg.crm.databinding.FragmentHardeningProcessBinding
import com.pg.crm.model.MaterialLocationData
import com.pg.crm.model.SaveHTProcessBatchDetailsRequest
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
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class HardeningProcessFragment :
    BaseFragment<FragmentHardeningProcessBinding, HardeningProcessViewModel>() {
    private val hardeningProcessViewModel: HardeningProcessViewModel by viewModels()
    var picker: DatePickerDialog? = null

    lateinit var fragmentHardeningBinding: FragmentHardeningProcessBinding
    private var sendingrequestDetaildate: String = ""
    private var sendingrequestdate: String = ""
    private var org_code_location: String = ""
    override fun getBindingVariable(): Int = BR.hardeningProcessViewModel

    override fun getLayoutId(): Int = R.layout.fragment_hardening_process

    override fun getViewModel(): HardeningProcessViewModel {
        return hardeningProcessViewModel
    }

    private lateinit var materialLocation: java.util.ArrayList<MaterialLocationData>
    lateinit var locationAdapter: MaterialInwardLocationAdapter

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHardeningBinding = getViewDataBinding()

        activity?.run {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.updateActionBarTitle(getString(R.string.hardening_process))
            viewModel.checkIsVisible(false)

        } ?: throw Throwable("invalid activity")


        setUpUi()
    }

    fun setUpUi() {
        checkMaterialLocationsResponse()

        SaveHTProcessBatchDetailsResponse()

        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_dropdown_item_1line,
            hardeningProcessViewModel.statusValues
        )
        fragmentHardeningBinding.statusAct.setAdapter(adapter)

        // Set the default selection to "Pending"
        fragmentHardeningBinding.statusAct.setText("Pending", false)

        // Optionally, you can set an item click listener to handle user selection
        fragmentHardeningBinding.statusAct.setOnItemClickListener { _, _, position, _ ->
            val selectedStatus = adapter.getItem(position)
            // Handle the selected status
        }

        fragmentHardeningBinding.statusAct.setOnClickListener {
            fragmentHardeningBinding.statusAct.showDropDown()
        }

        fragmentHardeningBinding.locationACT.setOnClickListener {
            if (fragmentHardeningBinding.locationACT.text.isEmpty()) {
                fragmentHardeningBinding.locationACT.showDropDown()
            } else {

                fragmentHardeningBinding.locationACT.showDropdown(locationAdapter!!)
            }
        }

        fragmentHardeningBinding.locationACT.setOnItemClickListener { _, _, i, _ ->
            fragmentHardeningBinding.locationACT.setText(materialLocation[i].orgCode.toString() + "," + materialLocation[i].orgOfficeName!!)
            org_code_location = materialLocation[i].orgOfficeNo.toString()

            fragmentHardeningBinding.saveBT.isEnabled = true
            fragmentHardeningBinding.saveBT.setBackgroundColor(
                fragmentHardeningBinding.saveBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );        

        }


        fragmentHardeningBinding.startProcessBT.setOnClickListener {
            fragmentHardeningBinding.startProcessBT.isEnabled = false
            fragmentHardeningBinding.startProcessBT.setBackgroundColor(
                fragmentHardeningBinding.startProcessBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );
            hardeningProcessViewModel.getMaterialLocations()


            fragmentHardeningBinding.enteredByET.setText(
                Prefs.getString(
                    Constants.USER_NAME,
                    ""
                )
            )

            fragmentHardeningBinding.statusAct.isEnabled = true

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

            val df2 = SimpleDateFormat("EEE MM/dd/yyyy", Locale.getDefault())
            sendingrequestDetaildate = df2.format(c)

            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate: String = df.format(c)

            val dateAndTime: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
            val dateAndTimeValue: String = dateAndTime.format(c)

            fragmentHardeningBinding.datetimeET.setText(dateAndTimeValue)

            fragmentHardeningBinding.startingTimeET.setText(dateAndTimeValue)
            fragmentHardeningBinding.coolingStarET.setText(dateAndTimeValue)


            fragmentHardeningBinding.machineNoEt.isEnabled = true
            fragmentHardeningBinding.programeNoET.isEnabled = true
            fragmentHardeningBinding.oilTempET.isEnabled = true
            fragmentHardeningBinding.timeInET.isEnabled = true

            fragmentHardeningBinding.endProcessBT.isEnabled = false
            fragmentHardeningBinding.saveBT.isEnabled = false
            fragmentHardeningBinding.findBT.isEnabled = false
            fragmentHardeningBinding.modifyBT.isEnabled = false
            fragmentHardeningBinding.inwardPendingDetailLL.isVisible = true

            fragmentHardeningBinding.coolingStarET.setOnClickListener {
                openCalenderPicker(fragmentHardeningBinding.coolingStarET)
            }
            fragmentHardeningBinding.endProcessBT.setBackgroundColor(
                fragmentHardeningBinding.endProcessBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );

            fragmentHardeningBinding.saveBT.setBackgroundColor(
                fragmentHardeningBinding.saveBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );


            fragmentHardeningBinding.findBT.setBackgroundColor(
                fragmentHardeningBinding.findBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );


            fragmentHardeningBinding.modifyBT.setBackgroundColor(
                fragmentHardeningBinding.modifyBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );


        }

        fragmentHardeningBinding.saveBT.setOnClickListener {
            checkValidation()
        }


        fragmentHardeningBinding.timeInET.setClickable(true)
        fragmentHardeningBinding.timeInET.setLongClickable(false)
        fragmentHardeningBinding.timeInET.setInputType(InputType.TYPE_NULL)

        fragmentHardeningBinding.timeInET.setOnClickListener(View.OnClickListener { showTimePicker() })


        fragmentHardeningBinding.clearBT.setOnClickListener {

        }

    }


/*    private fun emptyAllFields() {
        fragmentHardeningVacuumBinding.saveBT.isEnabled = false
        fragmentHardeningVacuumBinding.saveBT.setBackgroundColor(
            fragmentHardeningVacuumBinding.saveBT.getContext().getResources()
                .getColor(R.color.button_fade)
        );

        fragmentHardeningVacuumBinding.startProcessBT.isEnabled = true
        fragmentHardeningVacuumBinding.startProcessBT.setBackgroundColor(
            fragmentHardeningVacuumBinding.startProcessBT.getContext().getResources()
                .getColor(R.color.colorPrimaryDark)
        );

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



        fragmentHardeningVacuumBinding.datetimeET.isEnabled = false
        fragmentHardeningVacuumBinding.enteredByET.isEnabled = false

        fragmentHardeningVacuumBinding.inwardPendingDetailLL.visibility = View.GONE
        materialLocation.clear()


    }
    */

    fun checkEnableDisableSaveBt() {
        fragmentHardeningBinding.locationACT.doOnTextChanged { text, start, before, count ->

            if (count > 0) {
                fragmentHardeningBinding.saveBT.isEnabled = true
            }
        }

    }

    private fun AutoCompleteTextView.showDropdown(adapter: MaterialInwardLocationAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter?.filter?.filter(null)
            showDropDown()
        }
    }


    private fun checkValidation() {
        if (fragmentHardeningBinding.locationACT.text.isNullOrEmpty()) {
            fragmentHardeningBinding.locationACT.error = "filled not be empty"
        } else if (fragmentHardeningBinding.statusAct.text.isNullOrEmpty()) {
            fragmentHardeningBinding.statusAct.error = "filled not be empty"
        } else if (fragmentHardeningBinding.machineNoEt.text.isNullOrEmpty()) {
            fragmentHardeningBinding.machineNoEt.error = "filled not be empty"
        } else if (fragmentHardeningBinding.programeNoET.text.isNullOrEmpty()) {
            fragmentHardeningBinding.programeNoET.error = "filled not be empty"
        } else if (fragmentHardeningBinding.oilTempET.text.isNullOrEmpty()) {
            fragmentHardeningBinding.oilTempET.error = "filled not be empty"
        } else if (fragmentHardeningBinding.timeInET.text.isNullOrEmpty()) {
            fragmentHardeningBinding.timeInET.error = "filled not be empty"
        } else {

          /*  hardeningProcessViewModel.SaveHTProcessBatchDetails(
                SaveHTProcessBatchDetailsRequest(
                    fragmentHardeningBinding.timeInET.text.toString(),
                    fragmentHardeningBinding.machineNoEt.text.toString(),
                    "0",
                    fragmentHardeningBinding.startingTimeET.text.toString(),
                    org_code_location,
                    fragmentHardeningBinding.programeNoET.text.toString(),
                    Prefs.getString(Constants.EMPLOYEE_ID),
                    fragmentHardeningBinding.oilTempET.text.toString(),
                    fragmentHardeningBinding.datetimeET.text.toString()
                )
            )*/
            //call the api here
        }

    }

    private fun checkMaterialLocationsResponse() {


        hardeningProcessViewModel.response.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        AppUtils.hideDialog()
                        response.data?.let {
                            if (it.IsSucceed!!) {
                                materialLocation = ArrayList()
                                materialLocation = it.Data
                                locationAdapter =
                                    MaterialInwardLocationAdapter(
                                        requireActivity(),
                                        materialLocation
                                    )
                                fragmentHardeningBinding.locationACT.setAdapter(
                                    locationAdapter
                                )
                            }

                        }

                    }

                    is NetworkResult.Error -> {
                        AppUtils.hideDialog()
                        fragmentHardeningBinding.startProcessBT.isEnabled = true

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
            })
    }


    private fun SaveHTProcessBatchDetailsResponse() {


        hardeningProcessViewModel.saveBatchResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {
                            Toast.makeText(
                                requireActivity(),
                                "Production Process Details Entered Sucessfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            //emptyAllFields()
                            AppUtils.hideDialog()

                        }
                    }

                    is NetworkResult.Error -> {
                        AppUtils.hideDialog()
                        fragmentHardeningBinding.startProcessBT.isEnabled = true

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
            })
    }


    private fun showTimePicker() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                fragmentHardeningBinding.timeInET.setText(
                    String.format("%d:%d", hourOfDay, minute)
                )
            }
        }, hour, minute, false)

        mTimePicker.show()
    }


    fun openCalenderPicker(editText: EditText) {
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val month: Int = cldr.get(Calendar.MONTH)
        val year: Int = cldr.get(Calendar.YEAR)
        // date picker dialog
        // date picker dialog
        picker = DatePickerDialog(
            requireActivity(),
            { view, year, monthOfYear, dayOfMonth ->
                editText.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year)
            },
            year,
            month,
            day
        )
        picker!!.show()
    }

}