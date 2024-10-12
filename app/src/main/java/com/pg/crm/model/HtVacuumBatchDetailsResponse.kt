package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class HtVacuumBatchDetailsResponse(

	@field:SerializedName("HtVacuumBatchDetailsResponse")
	val htVacuumBatchDetailsResponse: List<HtVacuumBatchDetailsResponseItem?>? = null
)

data class ProcessMachineDetails(

	@field:SerializedName("Machine_Capacity")
	val machineCapacity: Int? = null,

	@field:SerializedName("Machine_Tank_No")
	val machineTankNo: String? = null,

	@field:SerializedName("MC_Capacity_UOM_Code")
	val mCCapacityUOMCode: Int? = null
)

data class BatchScheduleCustomerProcessDetails(

	@field:SerializedName("Net_Weight_Material")
	val netWeightMaterial: String? = null,

	@field:SerializedName("Weighing_Sl_No")
	val weighingSlNo: Int? = null,

	@field:SerializedName("Weighing_Prod_Sl_No")
	val weighingProdSlNo: Int? = null,

	@field:SerializedName("Batch_Cust_Process_No")
	val batchCustProcessNo: Int? = null,

	@field:SerializedName("Customer_Items_No")
	val customerItemsNo: Int? = null
)

data class ProcessNameDetailsV(

	@field:SerializedName("Process_Name")
	val processName: String? = null
)

data class HtVacuumBatchDetailsResponseItem(

	@field:SerializedName("processNameDetails")
	val processNameDetails: ProcessNameDetailsV? = null,

	@field:SerializedName("customerInformationDetails")
	val customerInformationDetails: CustomerInformationDetailsV? = null,

	@field:SerializedName("processMachineDetails")
	val processMachineDetails: ProcessMachineDetails? = null,

	@field:SerializedName("batchScheduleMasterDetails")
	val batchScheduleMasterDetails: BatchScheduleMasterDetails? = null,

	@field:SerializedName("batchScheduleCustomerProcessDetails")
	val batchScheduleCustomerProcessDetails: BatchScheduleCustomerProcessDetails? = null,

	@field:SerializedName("inwardWeighingMasterDetails")
	val inwardWeighingMasterDetails: InwardWeighingMasterDetailsV? = null,

	var isSelected: Boolean =false
)

data class CustomerInformationDetailsV(

	@field:SerializedName("Customer_Organisation_Name")
	val customerOrganisationName: String? = null
)

data class InwardWeighingMasterDetailsV(

	@field:SerializedName("Customer_Org_ID")
	val customerOrgID: Int? = null
)

data class BatchScheduleMasterDetails(

	@field:SerializedName("Process_Machine_No")
	val processMachineNo: Int? = null,

	@field:SerializedName("Total_Batch_Quantity")
	val totalBatchQuantity: Double? = null,

	@field:SerializedName("Prop_Batch_Shchedule_Time")
	val propBatchShcheduleTime: String? = null,

	@field:SerializedName("Process_Name_Code")
	val processNameCode: Int? = null,

	@field:SerializedName("CP_Process_Stage_No")
	val cPProcessStageNo: Int? = null,

	@field:SerializedName("Shift_Schedule")
	val shiftSchedule: String? = null,

	@field:SerializedName("Data_Entry_Date_Time")
	val dataEntryDateTime: String? = null,

	@field:SerializedName("Batch_Remarks")
	val batchRemarks: String? = null,

	@field:SerializedName("Prop_Batch_Shchedule_Date")
	val propBatchShcheduleDate: String? = null,

	@field:SerializedName("Batch_Sch_No")
	val batchSchNo: Int? = null
)
