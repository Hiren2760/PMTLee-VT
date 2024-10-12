package com.pg.crm.model

data class GetMaterialResponse(
    val orgOfficeNo:Int?=0,
    val employeeID:Int?=0,
    val dataEntryDateTime:String?=null,
    val inwardGateEntryNo:Int?=0,
    val igeCustomerName:String?=null,
    val igePlace:String?=null,
    val igeDCDate:String?=null,
    val igeDCNo:String?=null,
    val igeVehicleNo:String?=null,
    val igeDriverName:String?=null,
    val igeMaterialDetails:String?=null,
    val igeRemarks:String?=null

)