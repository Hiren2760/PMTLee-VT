package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class SaveInwardWeighProductResponse(
    @SerializedName("weighingSlNo") var weighingSlNo: Int? = null,
    @SerializedName("weighingProdSlNo") var weighingProdSlNo: Int? = null,
    @SerializedName("customerItemsNo") var customerItemsNo: Int? = null,
    @SerializedName("weightQuantity") var weightQuantity: Int? = null,
    @SerializedName("weightContainersNos") var weightContainersNos: Int? = null,
    @SerializedName("tareWeightContainer") var tareWeightContainer: String? = null,
    @SerializedName("grossWeightMaterial") var grossWeightMaterial: String? = null,
    @SerializedName("totalTareWeightContainers") var totalTareWeightContainers: String? = null,
    @SerializedName("netWeightMaterial") var netWeightMaterial: String? = null


)
