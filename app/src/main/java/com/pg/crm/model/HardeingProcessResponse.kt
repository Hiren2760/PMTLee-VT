package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class HardeingProcessResponse(
    @SerializedName("inwardWeighingMasterDetails") var inwardWeighingMasterDetails: InwardWeighingMasterDetails,
    @SerializedName("customerInformationDetails") var customerInformationDetails: CustomerInformationDetails,
    @SerializedName("inwardWeighingProductsDetails") var inwardWeighingProductsDetails: InwardWeighingProductsDetails,
    @SerializedName("customerItemsMasterDetails") var customerItemsMasterDetails: CustomerItemsMasterDetails,
    @SerializedName("itemwiseEffectiveProcessStages") var itemwiseEffectiveProcessStages: ItemwiseEffectiveProcessStages,
    @SerializedName("itemPreferredProgrammeDetails") var itemPreferredProgrammeDetails: ItemPreferredProgrammeDetails,
    @SerializedName("processGroupDetails") var processGroupDetails: ProcessGroupDetails,
    @SerializedName("processNameDetails") var processNameDetails: ProcessNameDetails,
    @SerializedName("programmeMasterDetails") var processMasterDetails: ProcessMasterDetails,
    @SerializedName("HTMaterialInwardDetails") var htmMaterialInwardDetails: HTMaterialInwardDetails,
    @SerializedName("ProcessMachinesDetailsEntity") var processMachinesDetailsEntity: ProcessMachinesDetailsEntity,
    val InwardGateEntryNo: String? = "",
    val Process_Machine_No: String? = "",
    val Data_Entry_Date_Time: String? = "",
var isSelected:Boolean=false

)
//
//data class InwardWeighingMasterDetails(
//
//    val Weighing_Sl_No: String? = "",
//    val Inward_Gate_Entry_No: Int? = 0,
//    val Customer_Org_ID: String? = "",
//    val Weighing_Continue_Status: String? = "",
//    val Data_Entry_Date_Time: String? = ""
//)
//
//data class CustomerInformationDetails(
//    val CustomerOrganisationName: String? = ""
//)
//
//data class InwardWeighingProductsDetails(
//    val Customer_Items_No: String? = "",
//    val Weighing_Prod_Sl_No: String? = "",
//    val Net_Weight_Material: Double? = 0.0
//)
//
//data class CustomerItemsMasterDetails(
//    val Customer_Item_Name: String? = "",
//    val Customer_Item_Code: String? = "",
//    val Customer_Part_No: String? = ""
//)
//
//data class ItemwiseEffectiveProcessStages(
//    val Effective_Date: String? = "",
//    val CP_Process_Stage_No: String? = "",
//    val Process_Stage_No: String? = "",
//    val Process_Group_Code: String? = "",
//    val Process_Name_Code: String? = "",
//    val Customer_Items_No: String? = ""
//)
//
//data class ItemPreferredProgrammeDetails(
//    val Programme_Sugg_No: String? = "",
//    val Programme_Ammendment_No: String? = ""
//)
//
//data class ProcessGroupDetails(
//    val Process_Group_Name: String? = ""
//)
//
//data class ProcessNameDetails(
//    val Process_Name_Code: String? = "",
//    val Process_Name: String? = ""
//)
//
//data class ProcessMasterDetails(
//    val ProgrammeNo: String? = ""
//)
//
//data class HTMaterialInwardDetails(
//    val ProgrammeNo: String? = ""
//)
//
//data class ProcessMachinesDetailsEntity(
//    val Machine_Tank_No: String? = "",
//    val Process_Machine_No: Double? = 0.0,
//    val Machine_Capacity: String? = "",
//    val MC_Capacity_UOM_Code: String? = ""
//)

