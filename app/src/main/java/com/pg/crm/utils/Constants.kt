package com.pg.crm.utils

class Constants {


    companion object {
        const val BASE_URL = "http://etechprodapi.pg-erp.com/"
        const val AUTH_TOKEN = "auth_token"
        const val LOGIN_URL = "api/V1/Login/UserLogin"
        const val GET_ALL_MATERIAL = "/HTMaterialInwardDetails/GetAllMaterialDetails"
        const val GET_IGP_NO = "GetNextIGPNo?org_office_no=1"
        const val LOCATION_API = "api/V1/FinishedSFOpeningStock/GetOrganisationOfficeUnits"
        const val GET_PRODUCT_NAME = "InwardWeighingProductsDetails/GetProductNamesLookup/{id}"
        const val GET_CUSTOMER_INFO = "CustomerInformationDetails/GetAllCustomerInformationDetails"
        const val SERIAL_NUMBER = "GetNextSINo"
        const val WEIGHMENT_DETAIL_API =
            "InwardWeighingProductsDetails/GetAllInwardWeighingProductsDetails"
        const val WEIGHMENT_SAVE_MASTER_DETAILS =
            "InwardWeighingMasterDetails/SaveInwardWeighingMasterDetails"
        const val WEIGHMENT_SAVE_PRODUCT_DETAILS =
            "InwardWeighingProductsDetails/SaveInwardWeighingProductsDetails"

        const val PLACES_API = "GetPlaces"
        const val INWARD_INSERT_API = "HTMaterialInwardDetails/insert"
        const val GET_BATCH_DETAILS = "GetBatchScheduleDetails"
        const val GET_BATCH_NUMBER = "GetNextBatchSchNo"
        const val GetScheduledBatchesDetails = "GetScheduledBatchesDetails"
        const val SAVE_BATCH_SCH = "SaveBatchScheduleDetails"
        const val GetHTProcessBatchDetails = "GetHTProcessBatchDetails"
        const val GetLatestHTProcessBatchDetails = "GetLatestHTProcessBatchDetails"
        const val SaveHTProcessBatchDetails = "SaveHTProcessBatchDetails"
        const val GetLatestHTHardeningProcessBatchDetails =
            "GetLatestHTHardeningProcessBatchDetails"
        const val GetHTProcessVacuumeBatchDetails = "GetHTProcessVacuumeBatchDetails"
        const val SaveHTProcessVacuumeBatchDetails = "SaveHTProcessVacuumeBatchDetails"
        const val SaveHTHardeningProductionProcessVacuumeDetails = "SaveHTHardeningProductionProcessVacuumeDetails"
        const val GET_CUSTOMER_UOM_DETAILS = "GetUomDetailsByCustomerOrgId?customerOrgId={id}"

        const val USER_ID = "user_id"
        const val EMPLOYEE_ID = "employee_id"
        const val ORG_OFFICE_CODE = "org_office_code"
        const val SUCCESS = "Success"
        const val AUTHORIZATION = "Authorization"
        const val CONTENT_TYPE = "Content-Type"
        const val USER_NAME = "user_name"

    }
}