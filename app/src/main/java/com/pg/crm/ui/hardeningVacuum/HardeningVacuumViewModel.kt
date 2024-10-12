package com.pg.crm.ui.hardeningVacuum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pg.crm.api.ApiRepository
import com.pg.crm.base.BaseViewModel
import com.pg.crm.model.HardeningProcessVacuumPartialRequest
import com.pg.crm.model.HtVacuumBatchDetailsResponseItem
import com.pg.crm.model.MaterialInwardLocationResponse
import com.pg.crm.model.SaveInitialRequest
import com.pg.crm.ui.batchSchedule.SaveBatchSchResponse
import com.pg.crm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HardeningVacuumViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModel() {

    private val _response: MutableLiveData<NetworkResult<MaterialInwardLocationResponse>> =
        MutableLiveData()
    val response: LiveData<NetworkResult<MaterialInwardLocationResponse>> = _response
    val statusValues = arrayOf("Pending", "On Going", "Completed")
    val cycleRunningInValues = arrayOf("Programmer", "SCADA")
    val gridDataResponse: MutableLiveData<NetworkResult<ArrayList<HtVacuumBatchDetailsResponseItem>>> =
        MutableLiveData()

    val saveInitialReqResponse: MutableLiveData<NetworkResult<Int>> =
        MutableLiveData()

    val savePartialReqResponse: MutableLiveData<NetworkResult<SaveBatchSchResponse>> =
        MutableLiveData()


    fun getMaterialLocations() = viewModelScope.launch {
        _response.value = NetworkResult.Loading()
        apiRepository.getMaterialInwardLocations().collect {
            _response.value = it
        }
    }

    fun getHtProcessVacuumBatchDetails(orgOfficeNo: String) = viewModelScope.launch {
        gridDataResponse.value = NetworkResult.Loading()
        apiRepository.getHtProcessVacuumBatchDetails(orgOfficeNo).collect {
            gridDataResponse.value = it

        }
    }


    fun saveHTProcessVacuumBatchDetails(initialRequest: SaveInitialRequest) =
        viewModelScope.launch {
            saveInitialReqResponse.value = NetworkResult.Loading()
            apiRepository.SaveHTProcessVacuumeBatchDetails(initialRequest).collect {
                saveInitialReqResponse.value = it

            }
        }

    fun SaveHTHardeningProductionProcessVacuumeDetails(hardeningProcessVacuumPartialRequest: HardeningProcessVacuumPartialRequest) =
        viewModelScope.launch {
            savePartialReqResponse.value = NetworkResult.Loading()
            apiRepository.SaveHTHardeningProductionProcessVacuumeDetails(hardeningProcessVacuumPartialRequest).collect {
                savePartialReqResponse.value = it

            }
        }

}