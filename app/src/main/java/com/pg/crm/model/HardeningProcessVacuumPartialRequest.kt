package com.pg.crm.model

data class HardeningProcessVacuumPartialRequest(
	val hTVacuumeProductionCompletedDetailsModel: HTVacuumeProductionCompletedDetailsModel? = null,
	val hTHardeningVacuumeProductionDetailsModel: HTHardeningVacuumeProductionDetailsModel? = null
)

data class HTVacuumeProductionCompletedDetailsModel(
	val End_Date_Time: String? = null,
	val Duration_Duration: String? = null,
	val hTProductionONID: String? = null,
	val hTProductionENDID: String? = null,
	val batchSchNo: String? = null
)

data class HTHardeningVacuumeProductionDetailsModel(
	val serialNo: String? = null,
	val ln2FinalMb1: String? = null,
	val ln2FinalReadingBar: String? = null,
	val ln2FinalMb2: String? = null,
	val ln2InitialMb2: String? = null,
	val ln2InitialMb1: String? = null,
	val rootPumpOnTime: String? = null,
	val htProductionOnId: String? = null,
	val ln2InitialReadingBar: String? = null,
	val thyristorOnTime: String? = null,
	val pistonPumpOnMBar: String? = null,
	val holdingPumpOnMBar: String? = null,
	val htProductionTrasactionId: String? = null,
	val pistonPumpOnTime: String? = null,
	val diffusionPumpOnTime: String? = null,
	val rootPumpOnMBar: String? = null,
	val batchSchNo: String? = null,
	val diffusionPumpOnMBar: String? = null,
	val thyristorOnMBar: String? = null,
	val holdingPumpOnTime: String? = null
)

