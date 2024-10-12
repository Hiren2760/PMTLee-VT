package com.pg.crm.ui.batchSchedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pg.crm.api.ApiRepository
import com.pg.crm.base.BaseViewModel
import com.pg.crm.model.BatchScheduleCustomerProcessDetailsList
import com.pg.crm.model.BatchScheduleGridResponseItem
import com.pg.crm.model.BatchScheduleMasterDetailsModel
import com.pg.crm.model.BatchScheduleMasterDetailsModel1
import com.pg.crm.model.GetBatchResponse
import com.pg.crm.model.MaterialInwardLocationResponse
import com.pg.crm.ui.login.data.LoginBean
import com.pg.crm.ui.login.data.LoginResult
import com.pg.crm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatchSchViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModel() {

    private val _response: MutableLiveData<NetworkResult<MaterialInwardLocationResponse>> =
        MutableLiveData()
    val response: LiveData<NetworkResult<MaterialInwardLocationResponse>> = _response

    val batchResponse: MutableLiveData<NetworkResult<ArrayList<GetBatchResponse>>> =
        MutableLiveData()
    val scheduleList: MutableLiveData<NetworkResult<ArrayList<BatchScheduleGridResponseItem>>> =
        MutableLiveData()
    val batchNo: MutableLiveData<NetworkResult<String>> = MutableLiveData()
    val saveBatchResponse: MutableLiveData<NetworkResult<SaveBatchSchResponse>> = MutableLiveData()


    fun getMaterialLocations() = viewModelScope.launch {
        _response.value = NetworkResult.Loading()
        apiRepository.getMaterialInwardLocations().collect {
            _response.value = it
        }
    }


    fun getBatchDetails(org_no: String, date: String) = viewModelScope.launch {
        batchResponse.value = NetworkResult.Loading()
        apiRepository.getBatchDetail(org_no, date).collect {
            batchResponse.value = it
        }
    }

    fun getBatchNo(org_no: String) = viewModelScope.launch {
        batchNo.value = NetworkResult.Loading()
        apiRepository.getBatchNumber(org_no).collect {
            batchNo.value = it
        }
    }

    fun getScheduledBatchesDetails(tankNo: String) = viewModelScope.launch {
        scheduleList.value = NetworkResult.Loading()
        apiRepository.getScheduledBatchesDetails(tankNo).collect {
            scheduleList.value = it
        }
    }

    fun saveBatchSch(
        saveBatchSchRequest: BatchScheduleMasterDetailsModel1
    ) = viewModelScope.launch {
        saveBatchResponse.value = NetworkResult.Loading()
        apiRepository.saveBatchSch(saveBatchSchRequest).collect {
            saveBatchResponse.value = it
        }
    }

}