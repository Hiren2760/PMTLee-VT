package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class BatchScheduleGridResponse(

	@field:SerializedName("BatchScheduleGridResponse")
	val batchScheduleGridResponse: List<BatchScheduleGridResponseItem?>? = null
)

data class BatchScheduleMasterDetailsa(

	@field:SerializedName("Process_Machine_No")
	val processMachineNo: Int? = null,

	@field:SerializedName("Total_Batch_Quantity")
	val totalBatchQuantity: Any? = null,

	@field:SerializedName("Prop_Batch_Shchedule_Time")
	val propBatchShcheduleTime: String? = null,

	@field:SerializedName("Process_Name_Code")
	val processNameCode: Int? = null,

	@field:SerializedName("CP_Process_Stage_No")
	val cPProcessStageNo: Int? = null,

	@field:SerializedName("Data_Entry_Date_Time")
	val dataEntryDateTime: String? = null,

	@field:SerializedName("Prop_Batch_Shchedule_Date")
	val propBatchShcheduleDate: String? = null,

	@field:SerializedName("Batch_Sch_No")
	val batchSchNo: Int? = null
)

data class BatchScheduleGridResponseItem(

	@field:SerializedName("Current_Status")
	val currentStatus: String? = null,

	@field:SerializedName("batchScheduleMasterDetails")
	val batchScheduleMasterDetails: BatchScheduleMasterDetailsa? = null
)
