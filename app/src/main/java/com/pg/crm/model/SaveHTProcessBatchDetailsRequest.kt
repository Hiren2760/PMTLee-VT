package com.pg.crm.model

data class SaveHTProcessBatchDetailsRequest(
	val timeIn: String? = null,
	val processMachineNo: String? = null,
	val hTProductionONID: String? = null,
	val startTime: String? = null,
	val orgOfficeNo: String? = null,
	val cPProcessStageNo: String? = null,
	val employeeID: String? = null,
	val oilTemp: String? = null,
	val dataEntryDateTime: String? = null,
	val batchSchNo: String? = null
)

