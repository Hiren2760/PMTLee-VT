package com.pg.crm.model

data class SaveMaterialInwardModel(
    val orgOfficeNo: Int,
    val employeeID: String,
    val inwardGateEntryNo: String,
    val dataEntryDateTime: String?,
    val igeCustomerName: String,
    val igePlace: String,
    val igeDCDate: String?,
    val igeDCNo: String,
    val igeVehicleNo: String,
    val igeDriverName: String,
    val inwardMaterialType: String,
    val igeMaterialDetails: String,
    val igeRemarks: String


)