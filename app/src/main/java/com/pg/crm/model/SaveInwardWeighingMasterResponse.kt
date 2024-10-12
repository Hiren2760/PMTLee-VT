package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class SaveInwardWeighingMasterResponse(
    @SerializedName("orgOfficeNo") var orgOfficeNo: Int? = null,
    @SerializedName("employeeID") var employeeID: String? = null,
    @SerializedName("dataEntryDateTime") var dataEntryDateTime: String? = null,
    @SerializedName("inwardGateEntryNo") var inwardGateEntryNo: Int? = null,
    @SerializedName("weighingSlNo") var weighingSlNo: Int? = null,
    @SerializedName("customerOrgID") var customerOrgID: Int? = null,
    @SerializedName("inwardWeighingRemarks") var inwardWeighingRemarks: String? = null,
    @SerializedName("weighingContinueStatus") var weighingContinueStatus: String? = null
)
