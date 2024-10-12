package com.pg.crm.ui.weighment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.collection.ArraySet
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.crm.R
import com.pg.crm.base.BaseFragment
import com.pg.crm.ble.ScaleBluetoothSerial
import com.pg.crm.databinding.FragmentWeightmentBinding
import com.pg.crm.interfaces.OnItemClickListener
import com.pg.crm.model.*
import com.pg.crm.ui.main.MainViewModel
import com.pg.crm.ui.materialInward.MaterialInwardLocationAdapter
import com.pg.crm.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weightment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [weightmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WeightmentFragment : BaseFragment<FragmentWeightmentBinding, WeightmentViewModel>(),
    OnItemClickListener<GetMaterialResponse>,
    TextView.OnEditorActionListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val weightmentViewModel: WeightmentViewModel by viewModels()
    lateinit var fragmentWeightmentBinding: FragmentWeightmentBinding

    override fun getBindingVariable(): Int = BR.weightmentViewModel

    override fun getLayoutId(): Int = R.layout.fragment_weightment

    override fun getViewModel(): WeightmentViewModel {
        return weightmentViewModel
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this
    lateinit var materialInwardLocationAdapter: MaterialInwardLocationAdapter
    lateinit var productNameACTAdapter: ProductNameAdapter
    lateinit var customerInfoAdapter: CustomerInfoAdapter
    var picker: DatePickerDialog? = null

    private lateinit var materialLocation: ArrayList<MaterialLocationData>
    private lateinit var productNameList: ArrayList<ProductNameModel>
    private lateinit var placesList: ArrayList<PlacesModel>
    private lateinit var customerList: ArrayList<CustomerInformationModel>
    private var orgOfficeNo: Int? = 0
    private var employeeID: String = ""
    private var dataEntryDateTime: String = ""
    private var inwardGateEntryNo: Int? = 0
    private var weighingSlNo: Int? = 0
    private var customerOrgID: Int? = 0
    private var sendingrequestdate: String = ""
    private var customerItemsNo: Int? = 0
    var grossWeight = 0f
    private var selectedRbText: String = ""
    private var weighStatus: String = ""
    private var newList: MutableList<GetAllInwardWeighingProductsResponse>? = arrayListOf()
    private var updatedata: MutableList<GetAllInwardWeighingProductsResponse>? = arrayListOf()

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var scalesList: ArrayList<BluetoothDevice>? = null
    var scaleBleSerial: ScaleBluetoothSerial? = null
    private var mArrayAdapter: BluetoothDevicesAdapter? = null

    var continueCountClick = 0
    private var isClickable = true
    private var radioClear = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentWeightmentBinding = getViewDataBinding()

        activity?.run {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.updateActionBarTitle(getString(R.string.weighment))
            viewModel.checkIsVisible(false)
        } ?: throw Throwable("invalid activity")
        radioButton()
        setupUI()
        checkDisableEnableButton()
    }

    private fun radioButton() {
        val selectedRadioButtonId: Int =
            fragmentWeightmentBinding.radioGrp.getCheckedRadioButtonId()
        if (selectedRadioButtonId != -1) {
            selectedRbText = fragmentWeightmentBinding.radioBtn.getText().toString()
        }
        fragmentWeightmentBinding.radioGrp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                if (checkedId != -1 && !radioClear) {
                    if (R.id.radio_btn == checkedId)
                        selectedRbText = "Manual" else
                        selectedRbText = "Automatic"
                    Toast.makeText(context, selectedRbText, LENGTH_SHORT).show()

                    if (selectedRbText == "Automatic") {
                        grosswtET.isEnabled = false
                        grosswtET.isFocusable = false

                        if (mBluetoothAdapter != null && mBluetoothAdapter?.isEnabled == true) {
                            showMoveFolderDialog()
                        } else {
                            turnonBluetooth()
                            // showMoveFolderDialog()
                        }
                    } else if (selectedRbText == "Manual") {
                        grosswtET.isEnabled = true
                        grosswtET.isFocusable = true
                        grosswtET.isFocusableInTouchMode = true
                    }

                } else {
                    radioClear = false
                }
            })
    }


    private val requestBluetoothPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showMoveFolderDialog()
            // Permission is granted, proceed with Bluetooth operations
            // Perform your Bluetooth operations here
        } else {
            // Permission is denied, handle accordingly (e.g., show a message to the user)
        }
    }

    /* private var requestBluetooth =
         registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
             if (result.resultCode == RESULT_OK) {
                 //granted
                 showMoveFolderDialog()

             } else {
                 //deny
             }
         }*/

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            permissions.entries.forEach {
                Log.d("test006", "${it.key} = ${it.value}")
            }

        }

    private var requestBluetooth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                //granted
                showMoveFolderDialog()
            } else {
                //deny
            }
        }

    private fun showMoveFolderDialog() {

        if (mBluetoothAdapter?.isEnabled == true) {

            val dialog = Dialog(context!!)
            val window = dialog.window
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_bluetoothlist)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            window?.setGravity(Gravity.CENTER);
            scalesList = ArrayList<BluetoothDevice>()


            val devices = mBluetoothAdapter?.bondedDevices
            val scaleDevices: MutableSet<BluetoothDevice> = ArraySet()
            if (devices != null) {
                for (device in devices) {
                    Log.v("BLE", "Found device: " + device.name)
                    scaleDevices.add(device)
                }
            }

            scalesList.let {
                scalesList!!.addAll(scaleDevices)
                mArrayAdapter?.clear()
                mArrayAdapter?.addAll(scalesList!!)
                mArrayAdapter?.notifyDataSetChanged()
            }

            val ble_rv = dialog.findViewById<ListView>(R.id.ble_rv)
            mArrayAdapter = BluetoothDevicesAdapter(context!!, scalesList)
            ble_rv?.adapter = mArrayAdapter
            dialog.show()




            ble_rv?.setOnItemClickListener { parent, view, position, id ->
                if (isClickable) {
                    isClickable = false
                    Log.v("BLE", "pressed row $position")
                    val device = parent.adapter.getItem(position) as BluetoothDevice

                    scaleBleSerial = ScaleBluetoothSerial(device, requireActivity())

                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            scaleBleSerial!!.connect();
                            getBleData()
                            withContext(Dispatchers.Main) {
                                dialog.dismiss()

                                // Re-enable the button after a delay
                                delay(1000) // Adjust the duration as needed
                                isClickable = true
                            }


                        } catch (e: Exception) {
                            Log.v(TAG, "Failed to connect to scale! Reason: $e");
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(
                                    requireActivity(),
                                    "Failed to connect to scale. Make sure you selected a valid scale.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            /* Toast.makeText(
                                 requireActivity(),
                                 "Failed to connect to scale. Make sure you selected a valid scale.",
                                 Toast.LENGTH_SHORT
                             ).show()*/
                            // Re-enable the button after a delay
                            delay(1000) // Adjust the duration as needed
                            isClickable = true
                            dialog.dismiss()

//                   mActivity?.runOnUiThread(new Runnable () {
//                        @Override
//                        public void run() {
//                            Toast.makeText(
//                                getContext(),
//                                "Failed to connect to scale. Make sure you selected a valid scale.",
//                                Toast.LENGTH_SHORT
//                            ).show();
//                        }
//                    });


                        }


                    }
                }
            }

            // }

        }
    }


    private fun setupUI() {
        checkMaterialResponse()
        checkCustomerResponse()
        checkPlacesResponse()
        checkProductNameResponse()
        checkSaveInwardWeightResponse()
        checkSaveInwardWeightProductResponse()
        checkMaterialLocationsResponse()
        checkInwardWeighProductDetailsResponse()
        checkSerialNoResponse()


        fragmentWeightmentBinding.clearBT.setOnClickListener {
            emptyAllFields()
        }

        fragmentWeightmentBinding.grosswtET.setOnEditorActionListener(this)

        fragmentWeightmentBinding.saveBT.setOnClickListener {

            checkValidation()


        }

        fragmentWeightmentBinding.newWeightBT.setOnClickListener {
            fragmentWeightmentBinding.newWeightBT.isEnabled = false
            fragmentWeightmentBinding.newWeightBT.setBackgroundColor(
                fragmentWeightmentBinding.newWeightBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );

            fragmentWeightmentBinding.saveBT.isEnabled = false
            fragmentWeightmentBinding.saveBT.setBackgroundColor(
                fragmentWeightmentBinding.saveBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );

            fragmentWeightmentBinding.continueWeightBT.isEnabled = false
            fragmentWeightmentBinding.continueWeightBT.setBackgroundColor(
                fragmentWeightmentBinding.continueWeightBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );

            fragmentWeightmentBinding.modifyBT.isEnabled = false
            fragmentWeightmentBinding.modifyBT.setBackgroundColor(
                fragmentWeightmentBinding.modifyBT.getContext().getResources()
                    .getColor(R.color.button_fade)
            );
            fragmentWeightmentBinding.locationACT.isEnabled = true

            weightmentViewModel.getMaterialLocations()   // top grid

            val c = Calendar.getInstance().time
            println("Current time => $c")

            val df1 = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            sendingrequestdate = df1.format(c)

            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate: String = df.format(c)


            val dateAndTime: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
            val dateAndTimeValue: String = dateAndTime.format(c)


            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
            val date = format.format(c)
            dataEntryDateTime = date

            fragmentWeightmentBinding.datetimeET.setText(dateAndTimeValue)

            fragmentWeightmentBinding.enteredByET.setText(
                Prefs.getString(
                    Constants.USER_NAME,
                    ""
                )
            )
        }


        fragmentWeightmentBinding.locationACT.setOnClickListener {
            if (fragmentWeightmentBinding.locationACT.text.isEmpty()) {
                fragmentWeightmentBinding.locationACT.showDropDown()
            } else {
                fragmentWeightmentBinding.locationACT.showDropdown(materialInwardLocationAdapter!!)
            }
        }

        fragmentWeightmentBinding.locationACT.setOnItemClickListener { _, _, i, _ ->

            fragmentWeightmentBinding.locationACT.setText(materialLocation[i].orgCode.toString() + "," + materialLocation[i].orgOfficeName!!)

            weightmentViewModel.getSerialNo(materialLocation[i].orgOfficeNo.toString())
            weightmentViewModel.getMaterialDetails()
        }

        fragmentWeightmentBinding.customerNameACT.setOnClickListener {
            if (fragmentWeightmentBinding.customerNameACT.text.isEmpty()) {
                fragmentWeightmentBinding.customerNameACT.showDropDown()
            } else {

                fragmentWeightmentBinding.customerNameACT.showDropdown(customerInfoAdapter!!)
            }
        }

        fragmentWeightmentBinding.customerNameACT.setOnItemClickListener { _, _, i, _ ->
            fragmentWeightmentBinding.customerNameACT.setText(customerList[i].customerManagementDesignation)
            weightmentViewModel.getProductDetails(customerList[i].customerOrgID!!)
            customerOrgID = customerList[i].customerOrgID!!

            for (item in placesList) {
                if (item.PlaceCode == customerList[i].placeCode) {
                    fragmentWeightmentBinding.placeET.setText(item.PlaceName)

                }
            }

        }

        fragmentWeightmentBinding.productNameACT.setOnItemClickListener { _, _, i, _ ->
            fragmentWeightmentBinding.productNameACT.setText(productNameList[i].Customer_Item_Name + "," + productNameList[i].Customer_Item_Code!! + "," + productNameList[i].Customer_Part_No!!)
            customerItemsNo = productNameList[i].Customer_Items_No


        }

        fragmentWeightmentBinding.productNameACT.setOnClickListener {
            if (fragmentWeightmentBinding.productNameACT.text.isEmpty()) {
                fragmentWeightmentBinding.productNameACT.showDropDown()
            } else {

                fragmentWeightmentBinding.productNameACT.showDropdown(productNameACTAdapter!!)
            }
        }


        fragmentWeightmentBinding.tareWeightContainerET.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                try {
                    if (fragmentWeightmentBinding.tareWeightContainerET.hasFocus()) {
                        if (p0.toString().isNotEmpty()) {

                            if (fragmentWeightmentBinding.containerET.text.toString()
                                    .isNotEmpty()
                            ) {
                                fragmentWeightmentBinding.totalTareWtET.setText(
                                    "${
                                        fragmentWeightmentBinding.containerET.text.toString()
                                            .toFloat() * fragmentWeightmentBinding.tareWeightContainerET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            } else {
                                fragmentWeightmentBinding.totalTareWtET.setText(
                                    "${
//                                        p0.toString().toFloat()
                                        0
                                    }"
                                )
                            }


                            if (fragmentWeightmentBinding.grosswtET.text.toString()
                                    .isNotEmpty()
                            ) {
//                            var netWt = s.toString().toFloat() - tareWtEt.text.toString().toFloat()
                                fragmentWeightmentBinding.netMaterialWeightET.setText(
                                    "${
                                        fragmentWeightmentBinding.grosswtET.text.toString()
                                            .toFloat() - fragmentWeightmentBinding.totalTareWtET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            } else {
                                fragmentWeightmentBinding.netMaterialWeightET.setText(
                                    "${
                                        fragmentWeightmentBinding.totalTareWtET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            }

                            if (fragmentWeightmentBinding.totalTareWtET.text.toString()
                                    .toFloat() > fragmentWeightmentBinding.grosswtET.text.toString()
                                    .toFloat()
                            ) {
                                Toast.makeText(
                                    requireActivity(),
                                    "Gross weight should be greater then total Tare weight",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            fragmentWeightmentBinding.totalTareWtET.setText("${0}")
                            fragmentWeightmentBinding.netMaterialWeightET.setText(
                                "${0}"
                            )
                        }

                    }
                } catch (e: Exception) {

                }
            }
        })


        fragmentWeightmentBinding.containerET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                try {
                    if (fragmentWeightmentBinding.containerET.hasFocus()) {
                        if (p0.toString().isNotEmpty()) {

                            if (fragmentWeightmentBinding.containerET.text.toString()
                                    .isNotEmpty()
                            ) {
                                fragmentWeightmentBinding.totalTareWtET.setText(
                                    "${
                                        fragmentWeightmentBinding.containerET.text.toString()
                                            .toFloat() * fragmentWeightmentBinding.tareWeightContainerET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            } else {
                                fragmentWeightmentBinding.totalTareWtET.setText(
                                    "${
                                        0
                                    }"
                                )
                            }

                            if (fragmentWeightmentBinding.grosswtET.text.toString()
                                    .isNotEmpty()
                            ) {
//                            var netWt = s.toString().toFloat() - tareWtEt.text.toString().toFloat()
                                fragmentWeightmentBinding.netMaterialWeightET.setText(
                                    "${
                                        fragmentWeightmentBinding.grosswtET.text.toString()
                                            .toFloat() - fragmentWeightmentBinding.totalTareWtET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            } else {
                                fragmentWeightmentBinding.netMaterialWeightET.setText(
                                    "${
                                        fragmentWeightmentBinding.totalTareWtET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            }
                        }

                    }
                } catch (e: Exception) {

                }
            }
        })

        fragmentWeightmentBinding.grosswtET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                try {
                    if (fragmentWeightmentBinding.grosswtET.hasFocus()) {
                        if (p0.toString().isNotEmpty()) {

                            if (fragmentWeightmentBinding.containerET.text.toString()
                                    .isNotEmpty()
                            ) {
                                fragmentWeightmentBinding.totalTareWtET.setText(
                                    "${
                                        fragmentWeightmentBinding.containerET.text.toString()
                                            .toFloat() * fragmentWeightmentBinding.tareWeightContainerET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            } else {
                                fragmentWeightmentBinding.totalTareWtET.setText(
                                    "${
                                        p0.toString().toFloat()
                                    }"
                                )
                            }

                            if (fragmentWeightmentBinding.grosswtET.text.toString()
                                    .isNotEmpty()
                            ) {
//                            var netWt = s.toString().toFloat() - tareWtEt.text.toString().toFloat()
                                fragmentWeightmentBinding.netMaterialWeightET.setText(
                                    "${
                                        fragmentWeightmentBinding.grosswtET.text.toString()
                                            .toFloat() - fragmentWeightmentBinding.totalTareWtET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            } else {
                                fragmentWeightmentBinding.netMaterialWeightET.setText(
                                    "${
                                        fragmentWeightmentBinding.totalTareWtET.text.toString()
                                            .toFloat()
                                    }"
                                )
                            }
                        }

                    }
                } catch (e: Exception) {

                }
            }
        })

    }

    private fun checkMaterialLocationsResponse() {


        weightmentViewModel.response.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        AppUtils.hideDialog()
                        response.data?.let {
                            if (it.IsSucceed!!) {
                                materialLocation = ArrayList()
                                materialLocation = it.Data
                                materialInwardLocationAdapter =
                                    MaterialInwardLocationAdapter(
                                        requireActivity(),
                                        materialLocation
                                    )
                                fragmentWeightmentBinding.locationACT.setAdapter(
                                    materialInwardLocationAdapter
                                )
                                // fragmentWeightmentBinding.locationACT.setText(materialLocation[0].orgCode.toString() + "," + materialLocation[0].orgOfficeName!!)

                                fragmentWeightmentBinding.igpNoET.isEnabled = false
                                fragmentWeightmentBinding.slNoET.isEnabled = true
                                fragmentWeightmentBinding.placeET.isEnabled = true
                                fragmentWeightmentBinding.remarkET.isEnabled = true
                                fragmentWeightmentBinding.qunatityET.isEnabled = true
                                fragmentWeightmentBinding.containerET.isEnabled = true
                                fragmentWeightmentBinding.tareWeightContainerET.isEnabled = true
                                fragmentWeightmentBinding.grosswtET.isEnabled = true

                                weightmentViewModel.getCustomerInfo()
                                weightmentViewModel.getPlaces()
                            }

                        }

                    }

                    is NetworkResult.Error -> {
                        AppUtils.hideDialog()
                        fragmentWeightmentBinding.newWeightBT.isEnabled = true

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


    private fun checkInwardWeighProductDetailsResponse() {
        weightmentViewModel.allInwardWeighingProductResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        //      AppUtils.hideDialog()
                        response.data?.let {
                            Log.d("size", it!!.size.toString())

                            weighment_detail_LL.visibility = View.VISIBLE

                            var allInwardWeightmentProductAdapter =
                                AllInwardWeightmentProductAdapter(requireContext(), it)
                            weightment_detail_rv.setHasFixedSize(true)
                            weightment_detail_rv.layoutManager =
                                LinearLayoutManager(requireContext())
                            weightment_detail_rv.addItemDecoration(
                                DividerItemDecoration(
                                    weightment_detail_rv.context,
                                    DividerItemDecoration.VERTICAL
                                )
                            )
                            weightment_detail_rv?.adapter = allInwardWeightmentProductAdapter
                        }

                    }

                    is NetworkResult.Error -> {
                        //   AppUtils.hideDialog()
                        fragmentWeightmentBinding.newWeightBT.isEnabled = true

                        Toast.makeText(
                            requireActivity(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {
                        //  AppUtils.showLoadingDialog(requireActivity())
                    }
                }
            })
    }


    private fun checkMaterialResponse() {
        weightmentViewModel.materialResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.let {
                            Log.d("size", it!!.size.toString())

                            inwardPendingDetail_LL.visibility = View.VISIBLE
                            fragmentWeightmentBinding.inwardPendingDetailRv.visibility =
                                View.VISIBLE

                            var materialIssueSummaryDetailAdapter =
                                WeighmentPendingInwardAdapter(requireContext(), it!!, this)
                            inwardPending_detail_rv.setHasFixedSize(true)
                            inwardPending_detail_rv.layoutManager =
                                LinearLayoutManager(requireContext())
                            inwardPending_detail_rv.addItemDecoration(
                                DividerItemDecoration(
                                    inwardPending_detail_rv.context,
                                    DividerItemDecoration.VERTICAL
                                )
                            )
                            inwardPending_detail_rv?.adapter = materialIssueSummaryDetailAdapter
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


    private fun AutoCompleteTextView.showDropdown(adapter: MaterialInwardLocationAdapter) {
        if (!TextUtils.isEmpty(this.text.toString())) {
            adapter?.filter?.filter(null)
            showDropDown()
        }
    }

    private fun AutoCompleteTextView.showDropdown(adapter: CustomerInfoAdapter) {
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


    override fun onItemClick(data: GetMaterialResponse, sl: Int, position: Int) {
        fragmentWeightmentBinding.igpNoET.setText(data.inwardGateEntryNo.toString())
        //  fragmentWeightmentBinding.slNoET.setText((sl + 1).toString())
        fragmentWeightmentBinding.remarkET.setText(data.igeRemarks)
        val localDateTime = LocalDateTime.parse(data.dataEntryDateTime)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val output: String = formatter.format(localDateTime)

        fragmentWeightmentBinding.datetimeET.setText(output)
        orgOfficeNo = data.orgOfficeNo
        employeeID = data.employeeID.toString()
        dataEntryDateTime = data.dataEntryDateTime!!
        inwardGateEntryNo = data.inwardGateEntryNo
        weighingSlNo = (sl + 1)

    }

    private fun checkCustomerResponse() {
        weightmentViewModel.customerResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.let {
                            Log.d("size", it!!.size.toString())

                            customerList = ArrayList()
                            customerList = it!!
                            customerInfoAdapter =
                                CustomerInfoAdapter(
                                    requireActivity(),
                                    customerList
                                )
                            fragmentWeightmentBinding.customerNameACT.setAdapter(
                                customerInfoAdapter
                            )
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

    private fun checkPlacesResponse() {
        weightmentViewModel.placesResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        //     AppUtils.hideDialog()
                        placesList = ArrayList()
                        placesList = response.data!!


                    }

                    is NetworkResult.Error -> {
                        //  AppUtils.hideDialog()
                        Toast.makeText(
                            requireActivity(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {
                        //AppUtils.showLoadingDialog(requireActivity())
                    }
                }
            })
    }

    private fun checkProductNameResponse() {
        weightmentViewModel.productNameResponse.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.let {
                            productNameList = ArrayList()
                            productNameList = it!!
                            productNameACTAdapter =
                                ProductNameAdapter(
                                    requireActivity(),
                                    productNameList
                                )
                            fragmentWeightmentBinding.productNameACT.setAdapter(
                                productNameACTAdapter
                            )
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

    override fun onEditorAction(view: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
        when (view?.id) {
            R.id.grosswtET -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && !fragmentWeightmentBinding.grosswtET.text.isNullOrEmpty()) {
                    showContinueDialog()
                    return true
                }

            }

        }
        return false
    }

    private fun showContinueDialog() {

        val dialog = Dialog(requireActivity())
        val window = dialog.window
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_continue)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        window?.setGravity(Gravity.CENTER);
        val yesTv = dialog.findViewById(R.id.yesTv) as TextView
        val continueTv = dialog.findViewById(R.id.continueTv) as TextView
        val noTv = dialog.findViewById(R.id.noTv) as TextView


        continueTv.setOnClickListener {
            dialog.dismiss()

            dialog.dismiss()


            weighment_detail_LL.visibility = View.VISIBLE
            weighStatus = "Continue"
            setAllDetailsAdapter()
//            fragmentWeightmentBinding.saveBT.isEnabled = true
//            fragmentWeightmentBinding.saveBT.setBackgroundColor(
//                fragmentWeightmentBinding.saveBT.getContext().getResources()
//                    .getColor(R.color.colorPrimaryDark)
//            );


            // saveInwardWeighmentDetailData("Continue")


        }

        yesTv.setOnClickListener {
            dialog.dismiss()

            weighment_detail_LL.visibility = View.VISIBLE
            weighStatus = "yes"
            setAllDetailsAdapter()
            //  saveInwardWeighmentDetailData("Yes")


        }


        noTv.setOnClickListener {
            dialog.dismiss()


            weighment_detail_LL.visibility = View.VISIBLE
            weighStatus = "No"
            setAllDetailsAdapter()
            // saveInwardWeighmentDetailData("No")
        }


        if (dialog.isShowing) {
            dialog.dismiss()
            dialog.show()
        } else {
            dialog.show()
        }
    }


    private fun savelocaldataInAdapter(): MutableList<GetAllInwardWeighingProductsResponse>? {
        val response =
            GetAllInwardWeighingProductsResponse(
                fragmentWeightmentBinding.slNoET.text.toString().toLongOrNull(),
                0,
                customerItemsNo.toString(),
                fragmentWeightmentBinding.qunatityET.text.toString().toIntOrNull(),
                fragmentWeightmentBinding.containerET.text.toString().toIntOrNull(),
                fragmentWeightmentBinding.tareWeightContainerET.text.toString().toDoubleOrNull(),
                fragmentWeightmentBinding.grosswtET.text.toString().toDoubleOrNull(),
                fragmentWeightmentBinding.totalTareWtET.text.toString().toDoubleOrNull(),
                fragmentWeightmentBinding.netMaterialWeightET.text.toString().toDoubleOrNull(),
                fragmentWeightmentBinding.productNameACT.text.toString(),
                employeeID
            )

        newList?.clear()
        newList?.add(response)
        newList?.let { updatedata?.addAll(it) }

        return updatedata
    }

    private fun saveInwardWeighmentDetailData() {
        val list = ArrayList<SaveInwardWeighProductRequestModel>()

        for ((index, value) in updatedata?.withIndex()!!) {
            list.add(
                SaveInwardWeighProductRequestModel(
                    value.weighingSlNo.toString(),
                    value.customerItemsNo.toString(),
                    value.weightQuantity.toString(),
                    value.weightContainersNos.toString(),
                    value.tareWeightContainer.toString(),
                    value.grossWeightMaterial.toString(),
                    value.totalTareWeightContainers.toString(),
                    value.netWeightMaterial.toString(),
                )
            )
        }
        weightmentViewModel.saveInwardWeighingProduct(
            list
        )
    }

    fun setAllDetailsAdapter() {

        fragmentWeightmentBinding.saveBT.isEnabled = true
        fragmentWeightmentBinding.saveBT.setBackgroundColor(
            fragmentWeightmentBinding.saveBT.getContext().getResources()
                .getColor(R.color.colorPrimaryDark)
        );

        var allInwardWeightmentProductAdapter =
            AllInwardWeightmentProductAdapter(
                requireContext(),
                savelocaldataInAdapter()
            )
        weightment_detail_rv.setHasFixedSize(true)
        weightment_detail_rv.layoutManager =
            LinearLayoutManager(requireContext())
        weightment_detail_rv.addItemDecoration(
            DividerItemDecoration(
                weightment_detail_rv.context,
                DividerItemDecoration.VERTICAL
            )
        )
        weightment_detail_rv?.adapter = allInwardWeightmentProductAdapter
        fragmentWeightmentBinding.qunatityET.setText("")
        fragmentWeightmentBinding.containerET.setText("")
        fragmentWeightmentBinding.grosswtET.setText("")
        fragmentWeightmentBinding.tareWeightContainerET.setText("")
        fragmentWeightmentBinding.totalTareWtET.setText("")
        fragmentWeightmentBinding.netMaterialWeightET.setText("")
        ///  fragmentWeightmentBinding.remarkET.setText("")
        //fragmentWeightmentBinding.placeET.setText("")

    }


    private fun checkSaveInwardWeightResponse() {
        weightmentViewModel.saveInwardWeighMaster.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.let {
                            fragmentWeightmentBinding.saveBT.isEnabled = true
                            AppUtils.hideDialog()
                            saveInwardWeighmentDetailData()
                        }

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

    private fun checkValidation() {
        if (updatedata.orEmpty().isNotEmpty()) {
            weightmentViewModel.saveInwardWeighingMaster(
                SaveInwardWeighingMasterRequestModel(
                    fragmentWeightmentBinding.slNoET.text.toString(),
                    orgOfficeNo!!,
                    Prefs.getString(Constants.EMPLOYEE_ID),
                    dataEntryDateTime,
                    inwardGateEntryNo!!,
                    customerOrgID!!,
                    fragmentWeightmentBinding.remarkET.text.toString(),
                    weighStatus,
                    selectedRbText
                )
            )
            return
        }

        if (fragmentWeightmentBinding.qunatityET.text.isNullOrEmpty()) {
            fragmentWeightmentBinding.qunatityET.error = "filled not be empty"
        } else if (fragmentWeightmentBinding.containerET.text.isNullOrEmpty()) {
            fragmentWeightmentBinding.containerET.error = "filled not be empty"
        } else if (fragmentWeightmentBinding.tareWeightContainerET.text.isNullOrEmpty()) {
            fragmentWeightmentBinding.tareWeightContainerET.error = "filled not be empty"
        } else if (fragmentWeightmentBinding.grosswtET.text.isNullOrEmpty()) {
            fragmentWeightmentBinding.grosswtET.error = "filled not be empty"
        } else {

            weightmentViewModel.saveInwardWeighingMaster(
                SaveInwardWeighingMasterRequestModel(
                    fragmentWeightmentBinding.slNoET.text.toString(),
                    orgOfficeNo!!,
                    Prefs.getString(Constants.EMPLOYEE_ID),
                    dataEntryDateTime,
                    inwardGateEntryNo!!,
                    customerOrgID!!,
                    fragmentWeightmentBinding.remarkET.text.toString(),
                    weighStatus,
                    selectedRbText
                )
            )
        }

    }

    private fun checkSaveInwardWeightProductResponse() {
        weightmentViewModel.saveInwardWeighProduct.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.let {
                            Toast.makeText(
                                requireActivity(),
                                "Weighment Data Successfully Saved",
                                Toast.LENGTH_SHORT
                            ).show()
                            // emptyAllFields()
                            AppUtils.hideDialog()
                            //  weightmentViewModel.getAllInwardWeighProductDetails()  //bottom grid
                            fragmentWeightmentBinding.inwardPendingDetailLL.visibility =
                                View.VISIBLE
                        }

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

    private fun emptyAllFields() {
        fragmentWeightmentBinding.saveBT.isEnabled = false
        fragmentWeightmentBinding.saveBT.setBackgroundColor(
            fragmentWeightmentBinding.saveBT.getContext().getResources()
                .getColor(R.color.button_fade)
        );

        fragmentWeightmentBinding.newWeightBT.isEnabled = true
        fragmentWeightmentBinding.netMaterialWeightET.setText("")
        fragmentWeightmentBinding.totalTareWtET.setText("")
        fragmentWeightmentBinding.grosswtET.setText("")
        fragmentWeightmentBinding.tareWeightContainerET.setText("")
        fragmentWeightmentBinding.containerET.setText("")
        fragmentWeightmentBinding.qunatityET.setText("")
        fragmentWeightmentBinding.remarkET.setText("")
        fragmentWeightmentBinding.placeET.setText("")
        fragmentWeightmentBinding.slNoET.setText("")
        fragmentWeightmentBinding.igpNoET.setText("")
        fragmentWeightmentBinding.datetimeET.setText("")
        fragmentWeightmentBinding.enteredByET.setText("")


        fragmentWeightmentBinding.netMaterialWeightET.isEnabled = false
        fragmentWeightmentBinding.totalTareWtET.isEnabled = false
        fragmentWeightmentBinding.grosswtET.isEnabled = false
        fragmentWeightmentBinding.tareWeightContainerET.isEnabled = false
        fragmentWeightmentBinding.containerET.isEnabled = false
        fragmentWeightmentBinding.qunatityET.isEnabled = false
        fragmentWeightmentBinding.remarkET.isEnabled = false
        fragmentWeightmentBinding.placeET.isEnabled = false
        fragmentWeightmentBinding.slNoET.isEnabled = false
        fragmentWeightmentBinding.igpNoET.isEnabled = false
        fragmentWeightmentBinding.datetimeET.isEnabled = false
        fragmentWeightmentBinding.enteredByET.isEnabled = false

        fragmentWeightmentBinding.locationACT.isEnabled = false
        fragmentWeightmentBinding.productNameACT.isEnabled = false
        fragmentWeightmentBinding.customerNameACT.isEnabled = false
        fragmentWeightmentBinding.placeET.isEnabled = false
        fragmentWeightmentBinding.inwardPendingDetailRv.visibility = View.GONE
        radioClear = true
        fragmentWeightmentBinding.radioGrp.clearCheck()
        if (::materialLocation.isInitialized) {
            materialLocation.clear()
        }

        if (::productNameList.isInitialized) {
            productNameList.clear()
        }

        if (::placesList.isInitialized) {
            placesList.clear()
        }
        if (::customerList.isInitialized) {
            customerList.clear()
        }

        updatedata?.clear()
        newList?.clear()
        weightment_detail_rv?.adapter = null


        fragmentWeightmentBinding.locationACT.setText("")
        fragmentWeightmentBinding.productNameACT.setText("")
        fragmentWeightmentBinding.customerNameACT.setText("")

    }

    private fun checkSerialNoResponse() {
        weightmentViewModel._responseSerialNo.observe(
            requireActivity(),
            androidx.lifecycle.Observer { response ->
                when (response) {
                    is NetworkResult.Success -> {

                        response.data?.let {

                            fragmentWeightmentBinding.slNoET.setText((it).toString())
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

    fun checkDisableEnableButton() {


        fragmentWeightmentBinding.grosswtET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (fragmentWeightmentBinding.grosswtET.text.toString().isEmpty()) {


                    fragmentWeightmentBinding.newWeightBT.isEnabled = false
                    fragmentWeightmentBinding.newWeightBT.setBackgroundColor(
                        fragmentWeightmentBinding.saveBT.getContext().getResources()
                            .getColor(R.color.button_fade)
                    );

                    fragmentWeightmentBinding.continueWeightBT.isEnabled = false
                    fragmentWeightmentBinding.continueWeightBT.setBackgroundColor(
                        fragmentWeightmentBinding.saveBT.getContext().getResources()
                            .getColor(R.color.button_fade)
                    );

                    fragmentWeightmentBinding.modifyBT.isEnabled = false
                    fragmentWeightmentBinding.modifyBT.setBackgroundColor(
                        fragmentWeightmentBinding.saveBT.getContext().getResources()
                            .getColor(R.color.button_fade)
                    );

                }
            } else {

                fragmentWeightmentBinding.saveBT.isEnabled = false
                fragmentWeightmentBinding.saveBT.setBackgroundColor(
                    fragmentWeightmentBinding.saveBT.getContext().getResources()
                        .getColor(R.color.button_fade)
                );

                fragmentWeightmentBinding.newWeightBT.isEnabled = true
                fragmentWeightmentBinding.newWeightBT.setBackgroundColor(
                    fragmentWeightmentBinding.saveBT.getContext().getResources()
                        .getColor(R.color.colorPrimary)
                );

                fragmentWeightmentBinding.continueWeightBT.isEnabled = true
                fragmentWeightmentBinding.continueWeightBT.setBackgroundColor(
                    fragmentWeightmentBinding.saveBT.getContext().getResources()
                        .getColor(R.color.colorPrimary)
                );

                fragmentWeightmentBinding.modifyBT.isEnabled = true
                fragmentWeightmentBinding.modifyBT.setBackgroundColor(
                    fragmentWeightmentBinding.saveBT.getContext().getResources()
                        .getColor(R.color.colorPrimary)
                );

            }
        }
    }

    private fun turnonBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        requestBluetoothPermission()
        if (mBluetoothAdapter != null && !mBluetoothAdapter?.isEnabled!!) {
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                requestMultiplePermissions.launch(
//                    arrayOf(
//                        Manifest.permission.BLUETOOTH_SCAN,
//                        Manifest.permission.BLUETOOTH_CONNECT
//                    )
//
//                )
//            } else {


            /* val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
             requestBluetooth.launch(enableBtIntent)*/
            // mArrayAdapter?.notifyDataSetChanged()
            //  }

        }


    }

    private fun requestBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        } else {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestBluetooth.launch(enableBtIntent)
        }
    }

    /*
        * Starts listening for serial data from BT!
        */
    private fun getBleData() {


        scaleBleSerial?.setmOnDataReceivedListener { data ->
            // E/weightFragment: data=+00004.5
            Log.e("weightFragment", "data=$data")
            // Toast.makeText(requireActivity(), data, LENGTH_SHORT).show()
            val wtData = removeLeadingZeroes(data.substring(1))
//
//            // E/weightFragment: data=+00004.5
//            Log.e("weightFragment", "data=$wtData")
            //  val gson = Gson()
//            //   activity!!.runOnUiThread {
//            var scaleData = ScaleData(0F, "")
//            try {
//                scaleData = gson.fromJson(wtData, ScaleData::class.java)
//            } catch (e: Exception) {
//                Log.w(
//                    TAG,
//                    "Error while deserializing JSON: $e"
//                )
//            }
//            Log.e("weightFragment", "weight=" + scaleData.weight)
            //  val grossValue=wtData.toString()
            //7.80
            // if(!wtData.contains("\r"))
            if (wtData != null && wtData != "0") {
                grosswtET.setText(wtData)
            }
            //grosswtET.text=wtData
            //                        weightText.setText(String.format("%sg", String.format("%.1f", scaleData.weight)));
//            Toast.makeText(mActivity, "$scaleData", LENGTH_SHORT).show()
            // }

        }
        scaleBleSerial?.listen()
    }

    private fun removeLeadingZeroes(str: String): String {
        var str = str
        val strPattern = "^0+(?!$)"
        str = str.replace(strPattern.toRegex(), "")
        return str
    }


    class GetWeatherTask(
        scaleBleSerial: ScaleBluetoothSerial,
        mContext: WeightmentFragment
    ) : AsyncTask<Unit, Unit, String>() {
        val innerScaleBle: ScaleBluetoothSerial? = scaleBleSerial
        val context: WeightmentFragment? = mContext
        override fun doInBackground(vararg params: Unit?): String? {
            innerScaleBle?.connect()
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
//            if (innerScaleBle?.isSocketConnected!!) {
//                context?.getBleData()
//            }
        }
    }

}