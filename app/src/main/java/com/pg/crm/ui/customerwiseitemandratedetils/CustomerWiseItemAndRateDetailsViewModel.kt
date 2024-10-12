package com.pg.crm.ui.customerwiseitemandratedetils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pg.crm.api.ApiRepository
import com.pg.crm.base.BaseViewModel
import com.pg.crm.model.CustomerInformationModel
import com.pg.crm.model.CustomerUOMDetailsModel
import com.pg.crm.model.MaterialInwardLocationResponse
import com.pg.crm.model.ProductNameModel
import com.pg.crm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomerWiseItemAndRateDetailsViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModel() {

    private val _response: MutableLiveData<NetworkResult<MaterialInwardLocationResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<MaterialInwardLocationResponse>> = _response
    val customerResponse: MutableLiveData<NetworkResult<ArrayList<CustomerInformationModel>>> = MutableLiveData()
    val productNameResponse: MutableLiveData<NetworkResult<ArrayList<ProductNameModel>>> = MutableLiveData()
    val customerUOMDetailsResponse: MutableLiveData<NetworkResult<ArrayList<CustomerUOMDetailsModel>>> = MutableLiveData()


    fun getMaterialLocations() = viewModelScope.launch {
        _response.value = NetworkResult.Loading()
        apiRepository.getMaterialInwardLocations().collect {
            _response.value = it
        }
    }
    fun getCustomerInfo()=viewModelScope.launch {
        customerResponse.value= NetworkResult.Loading()
        apiRepository.getCustomerList().collect {
            customerResponse.value = it
        }
    }
    fun getProductDetails(id:Int)=viewModelScope.launch {
        productNameResponse.value= NetworkResult.Loading()
        apiRepository.getProductDetails(id).collect {
            productNameResponse.value = it
        }
    }
    fun getCustomerUOMDetails(id:Int)=viewModelScope.launch {
        customerUOMDetailsResponse.value= NetworkResult.Loading()
        apiRepository.getCustomerUOMDetails(id).collect {
            customerUOMDetailsResponse.value = it
        }
    }
}