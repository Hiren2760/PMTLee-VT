package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class SaveInitialRequest(

	@field:SerializedName("Time_In")
	val timeIn: String? = null,

	@field:SerializedName("Process_Machine_No")
	val processMachineNo: String? = null,

	@field:SerializedName("HT_Production_ON_ID")
	val hTProductionONID: String? = null,

	@field:SerializedName("Start_Time")
	val startTime: String? = null,

	@field:SerializedName("Org_office_No")
	val orgOfficeNo: String? = null,

	@field:SerializedName("CP_Process_Stage_No")
	val cPProcessStageNo: String? = null,

	@field:SerializedName("Employee_ID")
	val employeeID: String? = null,

	@field:SerializedName("Oil_Temp")
	val oilTemp: String? = null,

	@field:SerializedName("Data_Entry_Date_Time")
	val dataEntryDateTime: String? = null,

	@field:SerializedName("Batch_Sch_No")
	val batchSchNo: String? = null
)
