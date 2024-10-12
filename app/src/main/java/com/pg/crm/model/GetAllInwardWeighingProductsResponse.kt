package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class GetAllInwardWeighingProductsResponse(

    @SerializedName("weighingSlNo") var weighingSlNo: Long? = null,
    @SerializedName("weighingProdSlNo") var weighingProdSlNo: Long? = null,
    @SerializedName("customerItemsNo") var customerItemsNo: String? = null,
    @SerializedName("weightQuantity") var weightQuantity: Int? = null,
    @SerializedName("weightContainersNos") var weightContainersNos: Int? = null,
    @SerializedName("tareWeightContainer") var tareWeightContainer: Double? = null,
    @SerializedName("grossWeightMaterial") var grossWeightMaterial: Double? = null,
    @SerializedName("totalTareWeightContainers") var totalTareWeightContainers: Double? = null,
    @SerializedName("netWeightMaterial") var netWeightMaterial: Double? = null,
    @SerializedName("productName") var  productName:String?=null,
   var employeeId:String?=null

)
