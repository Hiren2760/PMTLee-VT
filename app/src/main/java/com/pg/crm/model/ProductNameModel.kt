package com.pg.crm.model

data class ProductNameModel(
    val Customer_Org_ID:Int,
    val Customer_Items_No:Int,
    val Customer_Item_Name:String,
    val Customer_Item_Code:String,
    val Customer_Part_No:String,
    val Item_Weight_UOM_Code:Int,
    val Item_Weight:String,
    val Item_UOM_Code:String
)