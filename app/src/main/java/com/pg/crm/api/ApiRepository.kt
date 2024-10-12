package com.pg.crm.api

import com.pg.crm.base.BaseApiResponse
import com.pg.crm.model.BatchScheduleGridResponseItem
import com.pg.crm.model.BatchScheduleMasterDetailsModel1
import com.pg.crm.model.CustomerInformationModel
import com.pg.crm.model.CustomerUOMDetailsModel
import com.pg.crm.model.GetAllInwardWeighingProductsResponse
import com.pg.crm.model.GetBatchResponse
import com.pg.crm.model.GetMaterialResponse
import com.pg.crm.model.HardeningProcessVacuumPartialRequest
import com.pg.crm.model.HtVacuumBatchDetailsResponseItem
import com.pg.crm.model.MaterialInwardLocationResponse
import com.pg.crm.model.PlacesModel
import com.pg.crm.model.ProductNameModel
import com.pg.crm.model.SaveHTProcessBatchDetailsRequest
import com.pg.crm.model.SaveInitialRequest
import com.pg.crm.model.SaveInwardWeighProductRequestMain
import com.pg.crm.model.SaveInwardWeighProductRequestModel
import com.pg.crm.model.SaveInwardWeighProductResponse
import com.pg.crm.model.SaveInwardWeighingMasterRequestModel
import com.pg.crm.model.SaveInwardWeighingMasterResponse
import com.pg.crm.model.SaveMaterialInwardModel
import com.pg.crm.ui.batchSchedule.SaveBatchSchResponse
import com.pg.crm.ui.login.data.LoginBean
import com.pg.crm.ui.login.data.LoginResult
import com.pg.crm.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class ApiRepository @Inject constructor(
    private val apiService: ApiService
) : BaseApiResponse() {

    suspend fun login(loginBean: LoginBean): Flow<NetworkResult<LoginResult>> {
        return flow<NetworkResult<LoginResult>> {
            emit(safeApiCall { apiService.login(loginBean) })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getMaterialInwardLocations(): Flow<NetworkResult<MaterialInwardLocationResponse>> {
        return flow<NetworkResult<MaterialInwardLocationResponse>> {
            emit(safeApiCall { apiService.materialInwardLocation() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMaterial(): Flow<NetworkResult<List<GetMaterialResponse>>> {
        return flow<NetworkResult<List<GetMaterialResponse>>> {
            emit(safeApiCall { apiService.getMaterial() })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getIgpNo(): Flow<NetworkResult<String>> {
        return flow<NetworkResult<String>> {
            emit(safeApiCall { apiService.getIgpno() })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun saveMaterialInward(saveMaterialInwardModel: SaveMaterialInwardModel): Flow<NetworkResult<SaveMaterialInwardModel>> {
        return flow<NetworkResult<SaveMaterialInwardModel>> {
            emit(safeApiCall { apiService.saveMaterial(saveMaterialInwardModel) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCustomerList(): Flow<NetworkResult<ArrayList<CustomerInformationModel>>> {
        return flow<NetworkResult<ArrayList<CustomerInformationModel>>> {
            emit(safeApiCall { apiService.getCustomerInfo() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSerialNo(org_officeno: String): Flow<NetworkResult<String>> {
        return flow<NetworkResult<String>> {
            emit(safeApiCall { apiService.getSerialNo(org_officeno) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPlaces(): Flow<NetworkResult<ArrayList<PlacesModel>>> {
        return flow<NetworkResult<ArrayList<PlacesModel>>> {
            emit(safeApiCall { apiService.getPlaces() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProductDetails(id: Int): Flow<NetworkResult<ArrayList<ProductNameModel>>> {
        return flow<NetworkResult<ArrayList<ProductNameModel>>> {
            emit(safeApiCall { apiService.getProductName(id) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun saveInwardWeighingMaster(saveInwardWeighingMasterRequestModel: SaveInwardWeighingMasterRequestModel): Flow<NetworkResult<SaveInwardWeighingMasterResponse>> {
        return flow<NetworkResult<SaveInwardWeighingMasterResponse>> {
            emit(safeApiCall {
                apiService.saveInwardWeighingMaster(
                    saveInwardWeighingMasterRequestModel
                )
            })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun saveInwardWeighingProduct(saveInwardWeighProductRequestModel: List<SaveInwardWeighProductRequestModel>): Flow<NetworkResult<List<SaveInwardWeighProductResponse>>> {
        return flow<NetworkResult<List<SaveInwardWeighProductResponse>>> {
            emit(safeApiCall { apiService.saveInwardWeighProduct(saveInwardWeighProductRequestModel) })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getAllInwardWeighProductDetails(): Flow<NetworkResult<ArrayList<GetAllInwardWeighingProductsResponse>>> {
        return flow<NetworkResult<ArrayList<GetAllInwardWeighingProductsResponse>>> {
            emit(safeApiCall { apiService.getAllInwardWeighProductDetails() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getBatchDetail(
        officeno: String,
        date: String
    ): Flow<NetworkResult<ArrayList<GetBatchResponse>>> {
        return flow<NetworkResult<ArrayList<GetBatchResponse>>> {
            emit(safeApiCall { apiService.getBatchDetail(officeno, date) })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getBatchNumber(officeno: String): Flow<NetworkResult<String>> {
        return flow<NetworkResult<String>> {
            emit(safeApiCall { apiService.getBatchNumber(officeno) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun saveBatchSch(saveBatchSchrequest: BatchScheduleMasterDetailsModel1): Flow<NetworkResult<SaveBatchSchResponse>> {
        return flow<NetworkResult<SaveBatchSchResponse>> {
            emit(safeApiCall { apiService.saveBatchSch(saveBatchSchrequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun SaveHTProcessBatchDetails(saveHTProcessBatchDetailsRequest: SaveHTProcessBatchDetailsRequest): Flow<NetworkResult<SaveBatchSchResponse>> {
        return flow<NetworkResult<SaveBatchSchResponse>> {
            emit(safeApiCall { apiService.SaveHTProcessBatchDetails(saveHTProcessBatchDetailsRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getHtProcessVacuumBatchDetails(officeno: String): Flow<NetworkResult<ArrayList<HtVacuumBatchDetailsResponseItem>>> {
        return flow<NetworkResult<ArrayList<HtVacuumBatchDetailsResponseItem>>> {
            emit(safeApiCall { apiService.getHtProcessVacuumBatchDetails(officeno) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getScheduledBatchesDetails(tankNo: String): Flow<NetworkResult<ArrayList<BatchScheduleGridResponseItem>>> {
        return flow<NetworkResult<ArrayList<BatchScheduleGridResponseItem>>> {
            emit(safeApiCall { apiService.getScheduledBatchesDetails(tankNo) })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun SaveHTProcessVacuumeBatchDetails(initialRequest: SaveInitialRequest): Flow<NetworkResult<Int>> {
        return flow<NetworkResult<Int>> {
            emit(safeApiCall { apiService.SaveHTProcessVacuumeBatchDetails(initialRequest) })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun SaveHTHardeningProductionProcessVacuumeDetails(hardeningProcessVacuumPartialRequest: HardeningProcessVacuumPartialRequest): Flow<NetworkResult<SaveBatchSchResponse>> {
        return flow<NetworkResult<SaveBatchSchResponse>> {
            emit(safeApiCall {
                apiService.SaveHTHardeningProductionProcessVacuumeDetails(
                    hardeningProcessVacuumPartialRequest
                )
            })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getCustomerUOMDetails(id: Int): Flow<NetworkResult<ArrayList<CustomerUOMDetailsModel>>> {
        return flow<NetworkResult<ArrayList<CustomerUOMDetailsModel>>> {
            emit(safeApiCall { apiService.getCustomerUOMDetails(id) })
        }.flowOn(Dispatchers.IO)
    }
}