package com.pg.crm.ui.customerwiseitemandratedetils

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.pg.crm.R
import com.pg.crm.base.BaseFragment
import com.pg.crm.databinding.FragmentCustomerWiseItemRateDetailsBinding
import com.pg.crm.model.CustomerInformationModel
import com.pg.crm.model.CustomerUOMDetailsModel
import com.pg.crm.model.MaterialLocationData
import com.pg.crm.model.ProductNameModel
import com.pg.crm.ui.main.MainViewModel
import com.pg.crm.ui.materialInward.MaterialInwardLocationAdapter
import com.pg.crm.ui.weighment.CustomerInfoAdapter
import com.pg.crm.ui.weighment.ProductNameAdapter
import com.pg.crm.utils.AppUtils
import com.pg.crm.utils.AppUtils.Companion.toastMsg
import com.pg.crm.utils.Constants
import com.pg.crm.utils.NetworkResult
import com.pg.crm.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CustomerWiseItemAndRateDetilsFragment :
    BaseFragment<FragmentCustomerWiseItemRateDetailsBinding, CustomerWiseItemAndRateDetailsViewModel>() {

    private lateinit var _binding: FragmentCustomerWiseItemRateDetailsBinding
    private val customerWiseItemAndRateDetailsViewModel: CustomerWiseItemAndRateDetailsViewModel by viewModels()
    var picker: DatePickerDialog? = null
    private var customerOrgID: Int? = 0
    private var customerItemsNo: Int? = 0

    private lateinit var materialLocation: ArrayList<MaterialLocationData>
    private lateinit var customerList: ArrayList<CustomerInformationModel>
    private lateinit var productNameList: ArrayList<ProductNameModel>
    private lateinit var customreUOMDetailsList: ArrayList<CustomerUOMDetailsModel>

    lateinit var locationAdapter: MaterialInwardLocationAdapter
    lateinit var customerInfoAdapter: CustomerInfoRateDetailsAdapter
    lateinit var productNameACTAdapter: ProductNameAdapter


    override fun getBindingVariable(): Int = BR.customerWiseItemAndRateDetailsViewModel

    override fun getLayoutId(): Int =
        R.layout.fragment_customer_wise_item_rate_details


    override fun getViewModel(): CustomerWiseItemAndRateDetailsViewModel {
        return customerWiseItemAndRateDetailsViewModel
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = getViewDataBinding()

        activity?.run {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.updateActionBarTitle(getString(R.string.customer_wise_item_rate_details))
            viewModel.checkIsVisible(false)
        } ?: throw Throwable("invalid activity")
        init()
    }

    fun init() {
        _binding.EffectiveDateET.setOnClickListener {
            openCalenderPicker(_binding.EffectiveDateET)
        }
        _binding.saveBT.setOnClickListener {
            if (checkValidation()) {

            }
        }
        _binding.clearBT.setOnClickListener {
            allClear()
        }
        _binding.CreateCustomerRatesBT.setOnClickListener {
            setUI()
            _binding.CreateCustomerRatesBT.isEnabled = false
            _binding.saveBT.isEnabled = true

        }
        _binding.locationACT.setOnClickListener {
            if (_binding.locationACT.text.isEmpty()) {
                _binding.locationACT.showDropDown()
            } else {
                _binding.locationACT.showDropdown(locationAdapter)
            }
        }
        _binding.CustomerNameACT.setOnClickListener {
            if (_binding.CustomerNameACT.text.isEmpty()) {
                _binding.CustomerNameACT.showDropDown()
            } else {
                _binding.CustomerNameACT.showDropdown(customerInfoAdapter!!)
            }
        }
        _binding.ItemNameACT.setOnClickListener {
            if (_binding.ItemNameACT.text.isEmpty()) {
                _binding.ItemNameACT.showDropDown()
            } else {
                _binding.ItemNameACT.showDropdown(productNameACTAdapter!!)
            }
        }
    }

    fun setUI() {
        val c = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate: String = df.format(c)

        val dateAndTime: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
        val dateAndTimeValue: String = dateAndTime.format(c)

        _binding.DateTimeACT.setText(dateAndTimeValue)
        _binding.EffectiveDateET.setText(formattedDate)
        _binding.EnteredByET.setText(
            Prefs.getString(
                Constants.USER_NAME, ""
            )
        )
        customerWiseItemAndRateDetailsViewModel.getMaterialLocations()
        customerWiseItemAndRateDetailsViewModel.getCustomerInfo()

        checkMaterialLocationsResponse()
        checkCustomerResponse()
        checkProductNameResponse()
        checkCustomerUOMDetailsResponse()

    }

    private fun checkMaterialLocationsResponse() {
        customerWiseItemAndRateDetailsViewModel.response.observe(
            requireActivity()
        ) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    AppUtils.hideDialog()
                    response.data?.let {
                        if (it.IsSucceed!!) {
                            materialLocation = ArrayList()
                            materialLocation = it.Data
                            locationAdapter =
                                MaterialInwardLocationAdapter(requireActivity(), materialLocation)
                            _binding.locationACT.setAdapter(locationAdapter)

                            _binding.locationACT.setOnItemClickListener { _, _, i, _ ->
                                _binding.locationACT.setText(materialLocation[i].orgCode.toString() + "," + materialLocation[i].orgOfficeName!!)
//                                orgOfficeNo = materialLocation[i].orgOfficeNo!!
//                                hardeningVacuumViewModel.getHtProcessVacuumBatchDetails(materialLocation[i].orgOfficeNo.toString())
                            }
                        }
                    }

                }

                is NetworkResult.Error -> {
                    AppUtils.hideDialog()
                    //fragmentHardeningVacuumBinding.startProcessBT.isEnabled = true
                    Toast.makeText(requireActivity(), response.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    AppUtils.showLoadingDialog(requireActivity())
                }
            }
        }

    }

    private fun checkCustomerResponse() {
        customerWiseItemAndRateDetailsViewModel.customerResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {
                            Log.d("size", it!!.size.toString())
                            customerList = java.util.ArrayList()
                            customerList = it!!
                            customerInfoAdapter =
                                CustomerInfoRateDetailsAdapter(
                                    requireActivity(),
                                    customerList
                                )
                            _binding.CustomerNameACT.setAdapter(
                                customerInfoAdapter
                            )
                            _binding.CustomerNameACT.setOnItemClickListener { _, _, i, _ ->
                                _binding.CustomerNameACT.setText(customerList[i].customerManagementDesignation)
                                customerWiseItemAndRateDetailsViewModel.getProductDetails(
                                    customerList[i].customerOrgID!!
                                )
                                customerWiseItemAndRateDetailsViewModel.getCustomerUOMDetails(
                                    customerList[i].customerOrgID!!
                                )
                                customerOrgID = customerList[i].customerOrgID!!
//                                for (item in placesList) {
//                                    if (item.PlaceCode == customerList[i].placeCode) {
//                                        fragmentWeightmentBinding.placeET.setText(item.PlaceName)
//
//                                    }
//                                }

                            }

                        }

                    }

                    is NetworkResult.Error -> {
                        Toast.makeText(
                            requireActivity(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {

                    }
                }
            })
    }

    private fun checkProductNameResponse() {
        customerWiseItemAndRateDetailsViewModel.productNameResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {
                            productNameList = java.util.ArrayList()
                            productNameList = it!!
                            productNameACTAdapter =
                                ProductNameAdapter(
                                    requireActivity(),
                                    productNameList
                                )
                            _binding.ItemNameACT.setAdapter(
                                productNameACTAdapter
                            )
                            _binding.ItemNameACT.setOnItemClickListener { _, _, i, _ ->
                                _binding.ItemNameACT.setText(productNameList[i].Customer_Item_Name + "," + productNameList[i].Customer_Item_Code!! + "," + productNameList[i].Customer_Part_No!!)
                                customerItemsNo = productNameList[i].Customer_Items_No

                                _binding.ItemCodeACT.setText(productNameList[i].Customer_Item_Code)
                                _binding.PartNoEt.setText(productNameList[i].Customer_Part_No)
                                _binding.WeightET.setText(productNameList[i].Item_Weight)

                                /*if (customreUOMDetailsList != null) {
                                    for (item in customreUOMDetailsList) {
                                        if (item.uomCode == productNameList[i].Item_Weight_UOM_Code) {
                                            _binding.UOMET.setText(item.uomName)
                                            _binding.UOMEt.setText(item.uomName)
                                        }
                                    }
                                }*/
                            }
                        }

                    }

                    is NetworkResult.Error -> {
                        Toast.makeText(
                            requireActivity(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {

                    }
                }
            })
    }

    private fun checkCustomerUOMDetailsResponse() {
        customerWiseItemAndRateDetailsViewModel.customerUOMDetailsResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {
                            customreUOMDetailsList = ArrayList()
                            customreUOMDetailsList = it!!
                        }
                    }

                    is NetworkResult.Error -> {
                        Toast.makeText(
                            requireActivity(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {

                    }
                }
            })
    }


    fun checkValidation(): Boolean {
        _binding.run {
            if (locationACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Select Location")
                return false
            }
            if (EnteredByET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Entered By")
                return false
            }
            if (DateTimeACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Entry Date & Time")
                return false
            }
            if (CustomerNameACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Select Customer Name")
                return false
            }
            if (ItemNameACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Select Item Name")
                return false
            }
            if (ItemCodeACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Item Code")
                return false
            }
            if (PartNoEt.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Part No Item Name")
                return false
            }
            if (UOMET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter UOM")
                return false
            }
            if (WeightET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Weight")
                return false
            }
            if (UOMEt.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter UOM")
                return false
            }
            if (EffectiveDateET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Effective Date")
                return false
            }
            if (ProcessGroupACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Select Process Group")
                return false
            }
            if (ProcessNameACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Select Process Name")
                return false
            }
            if (AverageMaterialSupplyET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Average Material Supply (KGS)")
                return false
            }
            if (RateNoET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Rate No.")
                return false
            }
            if (RateKGET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Rate / KG.")
                return false
            }
            if (BillingInACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Select Billing In")
                return false
            }
            if (FreightACT.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Select Freight")
                return false
            }
            if (ProcessChargeET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Process Charge")
                return false
            }
            if (FreightRateET.text.isNullOrBlank()) {
                toastMsg(requireActivity(), "Please Enter Freight Rate")
                return false
            }
            return true
        }
    }

    fun allClear() {
        _binding.run {
            locationACT.setText("")
            EnteredByET.setText("")
            DateTimeACT.setText("")
            CustomerNameACT.setText("")
            ItemNameACT.setText("")
            ItemCodeACT.setText("")
            PartNoEt.setText("")
            UOMET.setText("")
            WeightET.setText("")
            UOMEt.setText("")
            EffectiveDateET.setText("")
            ProcessGroupACT.setText("")
            ProcessNameACT.setText("")
            AverageMaterialSupplyET.setText("")
            RateNoET.setText("")
            RateKGET.setText("")
            BillingInACT.setText("")
            FreightACT.setText("")
            ProcessChargeET.setText("")
            FreightRateET.setText("")
            root.clearFocus()
            _binding.CreateCustomerRatesBT.isEnabled = true
            _binding.saveBT.isEnabled = false
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

    private fun AutoCompleteTextView.showDropdown(adapter: MaterialInwardLocationAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter.filter.filter(null)
            showDropDown()
        }
    }

    private fun AutoCompleteTextView.showDropdown(adapter: CustomerInfoRateDetailsAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter?.filter?.filter(null)
            showDropDown()
        }
    }

    private fun AutoCompleteTextView.showDropdown(adapter: ProductNameAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter?.filter?.filter(null)
            showDropDown()
        }
    }
}