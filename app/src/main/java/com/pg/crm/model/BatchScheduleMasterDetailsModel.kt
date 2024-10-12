package com.pg.crm.model

data class BatchScheduleMasterDetailsModel1(
    var batchScheduleMasterDetailsModel: BatchScheduleMasterDetailsModel,
    var batchScheduleCustomerProcessDetailsList: ArrayList<BatchScheduleCustomerProcessDetailsList>

)

data class BatchScheduleMasterDetailsModel(
    val batch_Sch_No: Int,
    val org_office_No: Int,
    val employee_ID: Int,
    val data_Entry_Date_Time: String,
    val prop_Batch_Shchedule_Date: String,
    val prop_Batch_Shchedule_Time: String,
    val shift_Schedule: String,
    val process_Name_Code: String,
    val cP_Process_Stage_No: String, // "YES","No","CONTINUE"
    val total_Batch_Quantity: Double, // "YES","No","CONTINUE"
    val process_Machine_No: String, // "YES","No","CONTINUE"
    val batch_Remarks: String // "YES","No","CONTINUE"
)

data class BatchScheduleCustomerProcessDetailsList(
    val customer_Items_No: String,
    val net_Weight_Material: Double,
    val weighing_Prod_Sl_No: String,
    val weighing_Sl_No: String,
    val Batch_Sch_No: String,
    val Batch_Cust_Process_No: String,
    val Issued_Quantity: String,


    )