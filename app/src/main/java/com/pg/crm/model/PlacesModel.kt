package com.pg.crm.model

data class PlacesModel(
    val PlaceCode: Int,
    val PlaceName: String,
    val StateCode: Int? = null,
    val CountryCode: Int? = null,
    val DistrictCode:Int?=null

)