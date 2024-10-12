package com.pg.crm.model

data class MaterialInwardLocationResponse(
    val IsSucceed:Boolean?=null,
    val ErrorMessages:String?=null,
    val Exception:String?=null,
    var Data:ArrayList<MaterialLocationData>

)

data class MaterialLocationData(
    val orgOfficeNo:Int?=null,
    val orgCode:Int?=null,
    val orgOfficeName:String?=null
)