package com.pg.crm.ui.weighment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pg.crm.api.ApiRepository
import com.pg.crm.base.BaseViewModel
import com.pg.crm.model.*
import com.pg.crm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightmentViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModel() {

    private val _response: MutableLiveData<NetworkResult<MaterialInwardLocationResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<MaterialInwardLocationResponse>> = _response
    val materialResponse: MutableLiveData<NetworkResult<List<GetMaterialResponse>>> = MutableLiveData()
    val customerResponse: MutableLiveData<NetworkResult<ArrayList<CustomerInformationModel>>> = MutableLiveData()
    val placesResponse: MutableLiveData<NetworkResult<ArrayList<PlacesModel>>> = MutableLiveData()
    val productNameResponse: MutableLiveData<NetworkResult<ArrayList<ProductNameModel>>> = MutableLiveData()
    val saveInwardWeighMaster: MutableLiveData<NetworkResult<SaveInwardWeighingMasterResponse>> = MutableLiveData()
    val saveInwardWeighProduct: MutableLiveData<NetworkResult<List<SaveInwardWeighProductResponse>>> = MutableLiveData()
    val _responseSerialNo: MutableLiveData<NetworkResult<String>> = MutableLiveData()
    val allInwardWeighingProductResponse: MutableLiveData<NetworkResult<ArrayList<GetAllInwardWeighingProductsResponse>>> = MutableLiveData()
    fun getMaterialLocations() = viewModelScope.launch {
        _response.value= NetworkResult.Loading()
        apiRepository.getMaterialInwardLocations().collect {
            _response.value = it
        }
    }

    fun getMaterialDetails()=viewModelScope.launch {
        materialResponse.value= NetworkResult.Loading()
        apiRepository.getMaterial().collect {
            materialResponse.value = it
        }
    }

    fun getCustomerInfo()=viewModelScope.launch {
        customerResponse.value= NetworkResult.Loading()
        apiRepository.getCustomerList().collect {
            customerResponse.value = it
        }
    }

    fun getSerialNo(org_officeno:String) = viewModelScope.launch {
        _responseSerialNo.value= NetworkResult.Loading()
        apiRepository.getSerialNo(org_officeno).collect {
            _responseSerialNo.value = it
        }
    }

    fun getPlaces()=viewModelScope.launch {
        placesResponse.value= NetworkResult.Loading()
        apiRepository.getPlaces().collect {
            placesResponse.value = it
        }
    }

    fun getProductDetails(id:Int)=viewModelScope.launch {
        productNameResponse.value= NetworkResult.Loading()
        apiRepository.getProductDetails(id).collect {
            productNameResponse.value = it
        }
    }


    fun saveInwardWeighingMaster(saveInwardWeighingMasterRequestModel: SaveInwardWeighingMasterRequestModel)=viewModelScope.launch {
        saveInwardWeighMaster.value= NetworkResult.Loading()
        apiRepository.saveInwardWeighingMaster(saveInwardWeighingMasterRequestModel).collect {
            saveInwardWeighMaster.value = it
        }
    }

    fun saveInwardWeighingProduct(saveInwardWeighProductRequestModel:  List<SaveInwardWeighProductRequestModel>)=viewModelScope.launch {
        saveInwardWeighProduct.value= NetworkResult.Loading()
        apiRepository.saveInwardWeighingProduct(saveInwardWeighProductRequestModel).collect {
            saveInwardWeighProduct.value = it
        }
    }


    fun getAllInwardWeighProductDetails()=viewModelScope.launch {
        allInwardWeighingProductResponse.value= NetworkResult.Loading()
        apiRepository.getAllInwardWeighProductDetails().collect {
            allInwardWeighingProductResponse.value = it
        }
    }
}