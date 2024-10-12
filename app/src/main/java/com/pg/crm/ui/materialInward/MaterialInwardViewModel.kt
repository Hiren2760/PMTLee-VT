package com.pg.crm.ui.materialInward

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pg.crm.api.ApiRepository
import com.pg.crm.base.BaseViewModel
import com.pg.crm.model.GetMaterialResponse
import com.pg.crm.model.MaterialInwardLocationResponse
import com.pg.crm.model.SaveMaterialInwardModel
import com.pg.crm.ui.login.data.LoginBean
import com.pg.crm.ui.login.data.LoginResult
import com.pg.crm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialInwardViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModel() {

    private val _response: MutableLiveData<NetworkResult<MaterialInwardLocationResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<MaterialInwardLocationResponse>> = _response

     val materialResponse: MutableLiveData<NetworkResult<List<GetMaterialResponse>>> = MutableLiveData()
    val saveMaterialResponse: MutableLiveData<NetworkResult<SaveMaterialInwardModel>> = MutableLiveData()
    val igpno: MutableLiveData<NetworkResult<String>> = MutableLiveData()

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

    fun getIGPNo()=viewModelScope.launch {
        igpno.value= NetworkResult.Loading()
        apiRepository.getIgpNo().collect {
            igpno.value = it
        }
    }

    fun saveMaterialInward(saveMaterialInwardModel: SaveMaterialInwardModel) =viewModelScope.launch {
        saveMaterialResponse.value= NetworkResult.Loading()
        apiRepository.saveMaterialInward(saveMaterialInwardModel).collect {
            saveMaterialResponse.value = it
        }
    }

}