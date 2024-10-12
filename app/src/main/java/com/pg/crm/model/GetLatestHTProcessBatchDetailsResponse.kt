package com.pg.crm.model

data class GetLatestHTProcessBatchDetailsResponse(
	val getLatestHTProcessBatchDetailsResponse: List<GetLatestHTProcessBatchDetailsResponseItem?>? = null
)

data class GetLatestHTProcessBatchDetailsResponseItem(
	val htProductionMasterDetails: HtProductionMasterDetails? = null
)

data class HtProductionMasterDetails(
	val timeIn: String? = null,
	val processMachineNo: Int? = null,
	val hTProductionONID: Int? = null,
	val startTime: String? = null,
	val cPProcessStageNo: Int? = null,
	val employeeID: Int? = null,
	val oilTemp: Int? = null,
	val dataEntryDateTime: String? = null,
	val batchSchNo: Int? = null
)

