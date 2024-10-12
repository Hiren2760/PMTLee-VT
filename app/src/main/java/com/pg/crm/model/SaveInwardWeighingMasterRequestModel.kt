package com.pg.crm.model

data class SaveInwardWeighingMasterRequestModel(
    val weighingSlNo:String,
    val orgOfficeNo:Int,
    val employeeId:String,
    val dataEntryDateTime:String,
    val inwardGateEntryNo:Int,
    val customerOrgID:Int,
    val inwardWeighingRemarks:String,
    val weighingContinueStatus:String, // "YES","No","CONTINUE"
    val weightMode:String // "YES","No","CONTINUE"
)


