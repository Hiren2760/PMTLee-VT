package com.pg.crm.api

import com.pg.crm.model.BatchScheduleGridResponseItem
import com.pg.crm.model.BatchScheduleMasterDetailsModel1
import com.pg.crm.model.CustomerInformationModel
import com.pg.crm.model.CustomerUOMDetailsModel
import com.pg.crm.model.GetAllInwardWeighingProductsResponse
import com.pg.crm.model.GetBatchResponse

import com.pg.crm.model.GetLatestHTProcessBatchDetailsResponse
import com.pg.crm.model.GetMaterialResponse
import com.pg.crm.model.HardeningProcessVacuumPartialRequest
import com.pg.crm.model.HtVacuumBatchDetailsResponse
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
import com.pg.crm.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body loginBean: LoginBean): Response<LoginResult>

    @GET(Constants.GET_ALL_MATERIAL)
    suspend fun getMaterial(): Response<List<GetMaterialResponse>>

    @GET(Constants.GET_IGP_NO)
    suspend fun getIgpno(): Response<String>

    @GET(Constants.LOCATION_API)
    suspend fun materialInwardLocation(): Response<MaterialInwardLocationResponse>

    @GET(Constants.GET_PRODUCT_NAME)
    suspend fun getProductName(@Path("id") id: Int): Response<ArrayList<ProductNameModel>>

    @GET(Constants.GET_CUSTOMER_INFO)
    suspend fun getCustomerInfo(): Response<ArrayList<CustomerInformationModel>>

    @GET(Constants.SERIAL_NUMBER)
    suspend fun getSerialNo(@Query("org_office_no") org_office_no: String): Response<String>

    @POST(Constants.INWARD_INSERT_API)
    suspend fun saveMaterial(@Body saveMaterialInwardModel: SaveMaterialInwardModel): Response<SaveMaterialInwardModel>

    @GET(Constants.PLACES_API)
    suspend fun getPlaces(): Response<ArrayList<PlacesModel>>

    @POST(Constants.WEIGHMENT_SAVE_MASTER_DETAILS)
    suspend fun saveInwardWeighingMaster(@Body saveInwardWeighingMasterRequestModel: SaveInwardWeighingMasterRequestModel): Response<SaveInwardWeighingMasterResponse>

    @POST(Constants.WEIGHMENT_SAVE_PRODUCT_DETAILS)
    suspend fun saveInwardWeighProduct(@Body saveInwardWeighProductRequestModel: List<SaveInwardWeighProductRequestModel>): Response<List<SaveInwardWeighProductResponse>>

    @GET(Constants.WEIGHMENT_DETAIL_API)
    suspend fun getAllInwardWeighProductDetails(): Response<ArrayList<GetAllInwardWeighingProductsResponse>>

    @GET(Constants.GET_BATCH_DETAILS)
    suspend fun getBatchDetail(
        @Query("org_office_no") org_office_no: String,
        @Query("effectiveDate") effectiveDate: String
    ): Response<ArrayList<GetBatchResponse>>

    @GET(Constants.GET_BATCH_NUMBER)
    suspend fun getBatchNumber(@Query("org_office_no") org_office_no: String): Response<String>


    @GET(Constants.GetScheduledBatchesDetails)
    suspend fun getScheduledBatchesDetails(@Query("tank_no") tank_no: String): Response<ArrayList<BatchScheduleGridResponseItem>>

    @GET(Constants.GetHTProcessBatchDetails)
    suspend fun getHtProcessBatchDetailsByLocation(@Query("org_office_no") org_office_no: String): Response<String>


    @GET(Constants.GetLatestHTProcessBatchDetails)
    suspend fun getLatestHtProcessBatchDetails(): Response<GetLatestHTProcessBatchDetailsResponse>


    @POST(Constants.SaveHTProcessBatchDetails)
    suspend fun SaveHTProcessBatchDetails(@Body saveHTProcessBatchDetailsRequest: SaveHTProcessBatchDetailsRequest): Response<SaveBatchSchResponse>


    @POST(Constants.SAVE_BATCH_SCH)
    suspend fun saveBatchSch(@Body saveBatchSchRequest: BatchScheduleMasterDetailsModel1): Response<SaveBatchSchResponse>

    @GET(Constants.GetHTProcessVacuumeBatchDetails)
    suspend fun getHtProcessVacuumBatchDetails(@Query("org_office_no") org_office_no: String): Response<ArrayList<HtVacuumBatchDetailsResponseItem>>


    @POST(Constants.SaveHTProcessVacuumeBatchDetails)
    suspend fun SaveHTProcessVacuumeBatchDetails(@Body saveInitialRequest: SaveInitialRequest): Response<Int>


    @POST(Constants.SaveHTHardeningProductionProcessVacuumeDetails)
    suspend fun SaveHTHardeningProductionProcessVacuumeDetails(@Body hardeningProcessVacuumPartialRequest: HardeningProcessVacuumPartialRequest): Response<SaveBatchSchResponse>

    @GET(Constants.GET_CUSTOMER_UOM_DETAILS)
    suspend fun getCustomerUOMDetails(@Path("id") id: Int): Response<ArrayList<CustomerUOMDetailsModel>>

}