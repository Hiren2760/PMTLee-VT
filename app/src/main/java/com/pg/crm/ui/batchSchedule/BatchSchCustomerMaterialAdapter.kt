package com.pg.crm.ui.batchSchedule


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.pg.crm.R
import com.pg.crm.model.GetBatchResponse
import kotlinx.android.synthetic.main.row_batch_schedule_ht.view.customerName
import kotlinx.android.synthetic.main.row_batch_schedule_ht.view.dateTimeTV
import kotlinx.android.synthetic.main.row_batch_schedule_ht.view.etChargedQty
import kotlinx.android.synthetic.main.row_batch_schedule_ht.view.materialDetailsTV
import kotlinx.android.synthetic.main.row_batch_schedule_ht.view.process_name
import kotlinx.android.synthetic.main.row_batch_schedule_ht.view.selectedCB
import kotlinx.android.synthetic.main.row_batch_schedule_ht.view.tv_quantity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BatchSchCustomerMaterialAdapter(
    var context: Context?,
    var batchList: List<GetBatchResponse>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BatchSchCustomerMaterialAdapter.RecyclerViewHolder>() {


    private var serialNum = 0
    private var checkedPosition = -1
    var checklist = ArrayList<GetBatchResponse>()
    var lastItemSelectedPos = -1
    var firstSelected: GetBatchResponse? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.row_batch_schedule_ht, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return batchList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//        serialNum=0
        Log.d("check", batchList.size.toString() + "..." + position)
        holder.bindPerformersChild(batchList.get(position), position)
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindPerformersChild(getBatchResponse: GetBatchResponse, position: Int) {
            try {
                serialNum += 1
                itemView.selectedCB.text = "${position + 1}"

                getBatchResponse.inwardWeighingProductsDetails.Net_Weight_Material_Charged =
                    getBatchResponse.inwardWeighingProductsDetails.Net_Weight_Material

                itemView.selectedCB.isChecked = batchList.get(position).isSelected

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val parsedDate = LocalDateTime.parse(
                        getBatchResponse.Data_Entry_Date_Time,
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                    val formattedDate =
                        parsedDate.format(DateTimeFormatter.ofPattern("MMM dd,yyyy HH:mm"))
                    itemView.dateTimeTV.text =
                        formattedDate + "/" + batchList.get(position).InwardGateEntryNo

                } else {
                    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    val formatter = SimpleDateFormat("MMM dd,yyyy")
                    val formattedDate =
                        formatter.format(parser.parse(batchList.get(position).Data_Entry_Date_Time))
                    itemView.dateTimeTV.text = formattedDate
                }

                itemView.customerName.text =
                    batchList.get(position).customerInformationDetails.CustomerOrganisationName.toString()
                itemView.tv_quantity.text = String.format(
                    "%.2f",
                    batchList.get(position).inwardWeighingProductsDetails.Net_Weight_Material
                ).toDouble().toString()


                itemView.etChargedQty.doOnTextChanged { text, start, before, count ->
                    val newChargedQty = text.toString().toDoubleOrNull()

                    if (newChargedQty != null) {
                        val netWeightMaterial =
                            getBatchResponse.inwardWeighingProductsDetails.Net_Weight_Material
                        if (netWeightMaterial != null && netWeightMaterial >= newChargedQty) {
                            getBatchResponse.inwardWeighingProductsDetails.Net_Weight_Material_Charged =
                                newChargedQty
                            onItemClickListener.onQuantityModified(getBatchResponse, position)
                        } else {
                            getBatchResponse.inwardWeighingProductsDetails.Net_Weight_Material_Charged =
                                getBatchResponse.inwardWeighingProductsDetails.Net_Weight_Material
                            itemView.etChargedQty.error = "Maximum quantity exceed"
                        }

                    }

                }

                itemView.etChargedQty.setText(
                    String.format(
                        "%.2f",
                        batchList[position].inwardWeighingProductsDetails.Net_Weight_Material_Charged
                    ).toDouble().toString()
                )


                itemView.materialDetailsTV.text =
                    batchList.get(position).customerItemsMasterDetails.Customer_Item_Name + "/" +
                            batchList.get(position).customerItemsMasterDetails.Customer_Item_Code + "/" +
                            batchList.get(position).customerItemsMasterDetails.Customer_Part_No

                itemView.process_name.text =
                    batchList.get(position).processNameDetails.Process_Name + "/" +
                            batchList.get(position).processMasterDetails.ProgrammeNo

                val userSelectedObject = batchList.get(position)

                itemView.selectedCB.setOnCheckedChangeListener { _, b ->
                    userSelectedObject.isSelected = !userSelectedObject.isSelected
                    if (b) {
                        itemView.etChargedQty.isEnabled = true
                    } else {
                        itemView.etChargedQty.isEnabled = false
                    }
                    onItemClickListener.onItemClick(
                        getBatchResponse,
                        position
                    )
                }
                /*   itemView.selectedCB.setOnClickListener {

                       userSelectedObject.isSelected = !userSelectedObject.isSelected

                       if (userSelectedObject.isSelected) {
                           itemView.etChargedQty.isEnabled = true
                       } else {
                           itemView.etChargedQty.isEnabled = false

                       }

                       if (checklist.size == 0) {

                           firstSelected = userSelectedObject
                           if (userSelectedObject.isSelected) {
                               checklist.add(userSelectedObject)

                           }
                           notifyItemChanged(position)
                       } else if (firstSelected != null && userSelectedObject.itemPreferredProgrammeDetails.Programme_Ammendment_No.toString() == (firstSelected?.itemPreferredProgrammeDetails?.Programme_Ammendment_No
                               ?: "")
                       ) {
                           userSelectedObject.isSelected = !userSelectedObject.isSelected
                           if (userSelectedObject.isSelected) {
                               checklist.add(userSelectedObject)
                           } else {
                               if (checklist.size == 1) {
                                   firstSelected = null
                               }
                               checklist.remove(userSelectedObject)

                           }
                           notifyItemChanged(position)
                       } else if (userSelectedObject.itemPreferredProgrammeDetails.Programme_Ammendment_No.toString() != (firstSelected?.itemPreferredProgrammeDetails?.Programme_Ammendment_No
                               ?: "")
                       ) {
                           Toast.makeText(
                               context,
                               "Select Same Programming Process/Programmer No.",
                               Toast.LENGTH_LONG
                           ).show()
                       }



                       onItemClickListener.onItemClick(
                           getBatchResponse,
                           position
                       )
   //
                   }*/
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(getBatchResponse: GetBatchResponse, position: Int)
        fun onQuantityModified(getBatchResponse: GetBatchResponse, position: Int)
    }

}
