package com.pg.crm.ui.materialInward

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pg.crm.R
import com.pg.crm.base.BaseFragment
import com.pg.crm.databinding.FragmentMaterialInwardBinding
import com.pg.crm.model.MaterialLocationData
import com.pg.crm.model.SaveMaterialInwardModel
import com.pg.crm.ui.main.MainViewModel
import com.pg.crm.utils.AppUtils
import com.pg.crm.utils.Constants
import com.pg.crm.utils.NetworkResult
import com.pg.crm.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MaterialInwardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class MaterialInwardFragment :
    BaseFragment<FragmentMaterialInwardBinding, MaterialInwardViewModel>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var materialTypeACTAdapter: MaterialTypeAdapter
    private val materialInwardViewModel: MaterialInwardViewModel by viewModels()
    lateinit var fragmentMaterialInwardBinding: FragmentMaterialInwardBinding
    private var sendingrequestdate: String? = null
    override fun getBindingVariable(): Int = BR.materialInwardViewModel

    override fun getLayoutId(): Int = R.layout.fragment_material_inward

    override fun getViewModel(): MaterialInwardViewModel {
        return materialInwardViewModel
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this

    private lateinit var materialLocation: ArrayList<MaterialLocationData>
    lateinit var materialInwardLocationAdapter: MaterialInwardLocationAdapter
    var picker: DatePickerDialog? = null

    val materialType = arrayListOf<String>(
        "Job Work Material",
        "Product Purchases",
        "General Purchases",
        "Desp. Vehicle",
        "Own"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMaterialInwardBinding = getViewDataBinding()

        activity?.run {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.updateActionBarTitle(getString(R.string.material_inward))
            viewModel.checkIsVisible(false)

        } ?: throw Throwable("invalid activity")
        setupUI()
        checkMaterialLocationsResponse()
        checkIGPNoResponse()
        checkEnableDisableSaveBt()
    }


    fun checkEnableDisableSaveBt() {

        fragmentMaterialInwardBinding.remarkET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (fragmentMaterialInwardBinding.remarkET.text.toString().isEmpty()) {

                    fragmentMaterialInwardBinding.saveBT.isEnabled = true
                    fragmentMaterialInwardBinding.saveBT.setBackgroundColor(
                        fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                            .getColor(R.color.colorPrimaryDark)
                    );

                    fragmentMaterialInwardBinding.newBT.isEnabled = false
                    fragmentMaterialInwardBinding.newBT.setBackgroundColor(
                        fragmentMaterialInwardBinding.newBT.getContext().getResources()
                            .getColor(R.color.button_fade)
                    );

                    fragmentMaterialInwardBinding.findBT.isEnabled = false
                    fragmentMaterialInwardBinding.findBT.setBackgroundColor(
                        fragmentMaterialInwardBinding.findBT.getContext().getResources()
                            .getColor(R.color.button_fade)
                    );

                    fragmentMaterialInwardBinding.modifyBT.isEnabled = false
                    fragmentMaterialInwardBinding.modifyBT.setBackgroundColor(
                        fragmentMaterialInwardBinding.modifyBT.getContext().getResources()
                            .getColor(R.color.button_fade)
                    );

                }
            } else {

                fragmentMaterialInwardBinding.saveBT.isEnabled = false
                fragmentMaterialInwardBinding.saveBT.setBackgroundColor(
                    fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                        .getColor(R.color.button_fade)
                );

                fragmentMaterialInwardBinding.newBT.isEnabled = true
                fragmentMaterialInwardBinding.newBT.setBackgroundColor(
                    fragmentMaterialInwardBinding.newBT.getContext().getResources()
                        .getColor(R.color.colorPrimary)
                );

                fragmentMaterialInwardBinding.findBT.isEnabled = true
                fragmentMaterialInwardBinding.findBT.setBackgroundColor(
                    fragmentMaterialInwardBinding.findBT.getContext().getResources()
                        .getColor(R.color.colorPrimary)
                );

                fragmentMaterialInwardBinding.modifyBT.isEnabled = true
                fragmentMaterialInwardBinding.modifyBT.setBackgroundColor(
                    fragmentMaterialInwardBinding.modifyBT.getContext().getResources()
                        .getColor(R.color.colorPrimary)
                );

            }
        }

    }


    private fun setupUI() {

        fragmentMaterialInwardBinding.vehicleNoET.doOnTextChanged { text, start, before, count ->
            if (count > 0) {
                if (!allowOnlyAlphaNumbers(fragmentMaterialInwardBinding.vehicleNoET.text.toString())) {
                    fragmentMaterialInwardBinding.vehicleNoET.error =
                        "Please enter correct Vehicle No"
                }
            }
        }


        fragmentMaterialInwardBinding.customerNameET.doOnTextChanged { text, start, before, count ->
            if (count > 0) {
                if (!allowOnlyAlpha(fragmentMaterialInwardBinding.customerNameET.text.toString())) {
                    fragmentMaterialInwardBinding.customerNameET.error =
                        "Please enter correct Customer Name"
                }
            }
        }

        fragmentMaterialInwardBinding.placeET.doOnTextChanged { text, start, before, count ->
            if (count > 0) {
                if (!allowOnlyAlpha(fragmentMaterialInwardBinding.placeET.text.toString())) {
                    fragmentMaterialInwardBinding.placeET.error = "Please enter correct place"
                }
            }
        }
        fragmentMaterialInwardBinding.driverNameET.doOnTextChanged { text, start, before, count ->
            if (count > 0) {
                if (!allowOnlyAlpha(fragmentMaterialInwardBinding.driverNameET.text.toString())) {
                    fragmentMaterialInwardBinding.driverNameET.error =
                        "Please enter correct Driver Name"
                }
            }
        }

        materialType.let {
            materialTypeACTAdapter =
                MaterialTypeAdapter(
                    requireActivity(),
                    materialType
                )
            fragmentMaterialInwardBinding.materialTypeAct.setText(materialType.get(0))
            fragmentMaterialInwardBinding.materialTypeAct.setAdapter(
                materialTypeACTAdapter
            )
        }

        fragmentMaterialInwardBinding.newBT.setOnClickListener {

            fragmentMaterialInwardBinding.newBT.isEnabled = false
            fragmentMaterialInwardBinding.newBT.setBackgroundColor(
                fragmentMaterialInwardBinding.newBT.context.resources
                    .getColor(R.color.button_fade)
            )

            materialInwardViewModel.getMaterialLocations()
            val c = Calendar.getInstance().time
            println("Current time => $c")


            val df1 = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
            sendingrequestdate = df1.format(c)

            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate: String = df.format(c)


            val dateAndTime: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
            val dateAndTimeValue: String = dateAndTime.format(c)

            fragmentMaterialInwardBinding.datetimeET.setText(dateAndTimeValue)
            fragmentMaterialInwardBinding.dcDateET.setText(formattedDate)
            fragmentMaterialInwardBinding.enteredByET.setText(
                Prefs.getString(
                    Constants.USER_NAME,
                    ""
                )
            )
        }


        fragmentMaterialInwardBinding.saveBT.setOnClickListener {
            validateForm()
        }

        fragmentMaterialInwardBinding.locationACT.setOnClickListener {
            if (fragmentMaterialInwardBinding.locationACT.text.isEmpty()) {
                fragmentMaterialInwardBinding.locationACT.showDropDown()
            } else {
                fragmentMaterialInwardBinding.locationACT.showDropdown(materialInwardLocationAdapter!!)
            }
        }

        fragmentMaterialInwardBinding.locationACT.setOnItemClickListener { _, _, i, _ ->
            fragmentMaterialInwardBinding.locationACT.setText(materialLocation[i].orgCode.toString() + "," + materialLocation[i].orgOfficeName!!)
        }


        fragmentMaterialInwardBinding.clearBT.setOnClickListener {

            clearAllFeilds()

            fragmentMaterialInwardBinding.newBT.isEnabled = true
            fragmentMaterialInwardBinding.newBT.setBackgroundColor(
                fragmentMaterialInwardBinding.newBT.context.resources
                    .getColor(R.color.colorPrimary)
            )


        }

        fragmentMaterialInwardBinding.datetimeET.setOnClickListener {
            openCalenderPicker(fragmentMaterialInwardBinding.datetimeET)
        }


        fragmentMaterialInwardBinding.dcDateET.setOnClickListener {
            openCalenderPicker(fragmentMaterialInwardBinding.dcDateET)
        }

        fragmentMaterialInwardBinding.materialTypeAct.setOnItemClickListener { _, _, i, _ ->
            fragmentMaterialInwardBinding.materialTypeAct.setText(materialType.get(i))
            //customerItemsNo = productNameList[i].Customer_Items_No
            //  materialInwardViewModel.getMaterialDetails()
        }


        fragmentMaterialInwardBinding.materialTypeAct.setOnClickListener {
            if (fragmentMaterialInwardBinding.materialTypeAct.text.isEmpty()) {
                fragmentMaterialInwardBinding.materialTypeAct.showDropDown()
            } else {

                fragmentMaterialInwardBinding.materialTypeAct.showDropdown(materialTypeACTAdapter!!)
            }
        }
    }


    fun allowOnlyAlphaNumbers(input: String): Boolean {
        val regex = Regex("[A-Za-z0-9]+")
        return regex.matches(input)
    }

    fun allowOnlyAlpha(input: String): Boolean {
        val regex = Regex("[A-Za-z]+")
        return regex.matches(input)
    }


    private fun AutoCompleteTextView.showDropdown(adapter: MaterialTypeAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter.filter.filter(null)
            showDropDown()
        }
    }

    private fun checkMaterialLocationsResponse() {
        materialInwardViewModel.response.observe(requireActivity(), Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        if (it.IsSucceed!!) {
                            materialLocation = ArrayList()
                            materialLocation = it.Data
                            materialInwardLocationAdapter =
                                MaterialInwardLocationAdapter(requireActivity(), materialLocation)
                            fragmentMaterialInwardBinding.locationACT.setAdapter(
                                materialInwardLocationAdapter
                            )
                            // fragmentMaterialInwardBinding.locationACT.setText(materialLocation[0].orgCode.toString() + "," + materialLocation[0].orgOfficeName!!)

                            // checkMaterialResponse()

                            // materialInwardViewModel.getMaterialDetails()
                            materialInwardViewModel.getIGPNo()
                        }

                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentMaterialInwardBinding.newBT.isEnabled = true

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


    private fun checkMaterialResponse() {
        materialInwardViewModel.materialResponse.observe(requireActivity(), Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        Log.d("size", it!!.size.toString())
                        fragmentMaterialInwardBinding.igpNoET.setText((it!!.size + 1).toString())
                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentMaterialInwardBinding.newBT.isEnabled = true
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


    private fun checkIGPNoResponse() {
        materialInwardViewModel.igpno.observe(requireActivity(), Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        Log.d("size", it.toString())
                        fragmentMaterialInwardBinding.igpNoET.setText((it).toString())
                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    fragmentMaterialInwardBinding.newBT.isEnabled = true
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

    private fun AutoCompleteTextView.showDropdown(adapter: MaterialInwardLocationAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter?.filter?.filter(null)
            showDropDown()
        }
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
                editText.setText((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
            },
            year,
            month,
            day
        )
        picker!!.show()
    }

    private fun validateForm() {
        if (fragmentMaterialInwardBinding.locationACT.text.toString().isNullOrBlank()) {
            fragmentMaterialInwardBinding.locationACT.error =
                getString(R.string.validation_location)
        } else if (fragmentMaterialInwardBinding.customerNameET.text.toString().isNullOrBlank()) {
            fragmentMaterialInwardBinding.customerNameET.error =
                getString(R.string.validation_customername)
        } else if (fragmentMaterialInwardBinding.placeET.text.toString().isNullOrBlank()) {
            fragmentMaterialInwardBinding.placeET.error = getString(R.string.validation_place)
        } else if (fragmentMaterialInwardBinding.dcNoET.text.toString().isNullOrBlank()) {
            fragmentMaterialInwardBinding.dcNoET.error = getString(R.string.validation_dcno)
        } else if (fragmentMaterialInwardBinding.vehicleNoET.text.toString().isNullOrBlank()) {
            fragmentMaterialInwardBinding.vehicleNoET.error =
                getString(R.string.validation_vehicleno)
        } else if (fragmentMaterialInwardBinding.driverNameET.text.toString().isNullOrBlank()) {
            fragmentMaterialInwardBinding.driverNameET.error =
                getString(R.string.validation_drivername)
        } else if (fragmentMaterialInwardBinding.materialDatailsET.text.toString()
                .isNullOrBlank()
        ) {
            fragmentMaterialInwardBinding.materialDatailsET.error =
                getString(R.string.validation_materialdetail)
        } else if (fragmentMaterialInwardBinding.remarkET.text.toString().isNullOrBlank()) {
            fragmentMaterialInwardBinding.remarkET.error = getString(R.string.validation_remarks)
        } else {
            checkSaveMaterialResponse()
            materialInwardViewModel.saveMaterialInward(


                SaveMaterialInwardModel(
                    Prefs.getInt(
                        Constants.ORG_OFFICE_CODE
                    ),


                    Prefs.getString(Constants.EMPLOYEE_ID),
                    fragmentMaterialInwardBinding.igpNoET.text.toString(),
                    sendingrequestdate,
                    fragmentMaterialInwardBinding.customerNameET.text.toString(),
                    fragmentMaterialInwardBinding.placeET.text.toString(),
                    sendingrequestdate,
                    fragmentMaterialInwardBinding.dcNoET.text.toString(),
                    fragmentMaterialInwardBinding.vehicleNoET.text.toString(),
                    fragmentMaterialInwardBinding.driverNameET.text.toString(),
                    fragmentMaterialInwardBinding.materialTypeAct.text.toString(),
                    fragmentMaterialInwardBinding.materialDatailsET.text.toString(),
                    fragmentMaterialInwardBinding.remarkET.text.toString()
                )
            )
        }
    }

    private fun checkSaveMaterialResponse() {
        materialInwardViewModel.saveMaterialResponse.observe(
            requireActivity(),
            Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        AppUtils.hideDialog()
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.material_inward_save_successfully),
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is NetworkResult.Error -> {
                        AppUtils.hideDialog()

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


    fun changeColorButton(enable: Boolean) {

        fragmentMaterialInwardBinding.saveBT.isEnabled = enable
        fragmentMaterialInwardBinding.saveBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.button_fade)
        );

        fragmentMaterialInwardBinding.newBT.isEnabled = true
        fragmentMaterialInwardBinding.newBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.colorPrimary)
        );

        fragmentMaterialInwardBinding.findBT.isEnabled = true
        fragmentMaterialInwardBinding.findBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.colorPrimary)
        );

        fragmentMaterialInwardBinding.modifyBT.isEnabled = true
        fragmentMaterialInwardBinding.modifyBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.colorPrimary)
        );


    }

    fun clearAllFeilds() {

        fragmentMaterialInwardBinding.saveBT.isEnabled = false
        fragmentMaterialInwardBinding.saveBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.button_fade)
        );

        fragmentMaterialInwardBinding.newBT.isEnabled = true
        fragmentMaterialInwardBinding.newBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.colorPrimary)
        );

        fragmentMaterialInwardBinding.findBT.isEnabled = true
        fragmentMaterialInwardBinding.findBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.colorPrimary)
        );

        fragmentMaterialInwardBinding.modifyBT.isEnabled = true
        fragmentMaterialInwardBinding.modifyBT.setBackgroundColor(
            fragmentMaterialInwardBinding.saveBT.getContext().getResources()
                .getColor(R.color.colorPrimary)
        );

        fragmentMaterialInwardBinding.locationACT.setText("")
        fragmentMaterialInwardBinding.enteredByET.setText("")
        fragmentMaterialInwardBinding.igpNoET.setText("")
        fragmentMaterialInwardBinding.datetimeET.setText("")
        fragmentMaterialInwardBinding.customerNameET.setText("")
        fragmentMaterialInwardBinding.placeET.setText("")
        fragmentMaterialInwardBinding.dcDateET.setText("")
        fragmentMaterialInwardBinding.dcNoET.setText("")
        fragmentMaterialInwardBinding.vehicleNoET.setText("")
        fragmentMaterialInwardBinding.driverNameET.setText("")
        fragmentMaterialInwardBinding.materialDatailsET.setText("")
        fragmentMaterialInwardBinding.materialTypeAct.setText(materialType.get(0))
        fragmentMaterialInwardBinding.remarkET.setText("")
        fragmentMaterialInwardBinding.materialTypeAct.setText("")
    }
}