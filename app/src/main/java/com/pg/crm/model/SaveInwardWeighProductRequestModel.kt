package com.pg.crm.model

data class SaveInwardWeighProductRequestMain(val data: List<SaveInwardWeighProductRequestModel>) {

}

data class SaveInwardWeighProductRequestModel(
    val weighingSlNo: String? = "",
    val customerItemsNo: String? = "",
    val weightQuantity: String? = "",
    val weightContainersNos: String? = "",
    val tareWeightContainer: String? = "",
    val grossWeightMaterial: String? = "",
    val totalTareWeightContainers: String? = "",
    val netWeightMaterial: String? = ""
)

