package com.pg.crm.ui.hardeningVacuum

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pg.crm.R
import com.pg.crm.interfaces.OnItemClickListener
import com.pg.crm.model.GetBatchResponse
import com.pg.crm.model.HtVacuumBatchDetailsResponseItem
import kotlinx.android.synthetic.main.batch_sch_customrer_material.view.customerName
import kotlinx.android.synthetic.main.batch_sch_customrer_material.view.dateTimeTV
import kotlinx.android.synthetic.main.batch_sch_customrer_material.view.materialDetailsTV
import kotlinx.android.synthetic.main.batch_sch_customrer_material.view.process_name
import kotlinx.android.synthetic.main.batch_sch_customrer_material.view.selectedCB
import kotlinx.android.synthetic.main.batch_sch_customrer_material.view.serialNumberTV
import kotlinx.android.synthetic.main.batch_sch_customrer_material.view.tv_quantity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PendingBatchDetailsAdapter(
    var context: Context?,
    var batchList: List<HtVacuumBatchDetailsResponseItem>,
    var onItemClickListener: OnItemClickListener<HtVacuumBatchDetailsResponseItem>
) : RecyclerView.Adapter<PendingBatchDetailsAdapter.RecyclerViewHolder>() {


    private var serialNum = 0
    private var checkedPosition = -1
    var checklist = ArrayList<GetBatchResponse>()
    var lastItemSelectedPos = -1
    var firstSelected: GetBatchResponse? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.batch_sch_customrer_material, parent, false)
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
        fun bindPerformersChild(
            getBatchResponse: HtVacuumBatchDetailsResponseItem,
            position: Int
        ) {
            try {
                serialNum += 1
                itemView.serialNumberTV.text = "${position + 1}"
          /*      if (checkedPosition == -1) {
                    itemView.selectedCB.isChecked=false
                } else {
                    itemView.selectedCB.isChecked = checkedPosition == adapterPosition
                }*/

                itemView.selectedCB.isChecked= batchList.get(position).isSelected


                itemView.process_name.text =
                    "Pending for Schedule"



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val parsedDate = LocalDateTime.parse(
                        batchList.get(position).batchScheduleMasterDetails?.dataEntryDateTime ?: "",
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                    val formattedDate =
                        parsedDate.format(DateTimeFormatter.ofPattern("MMM dd,yyyy HH:mm"))
                    itemView.dateTimeTV.text =
                        formattedDate + "/" + (batchList.get(position).batchScheduleMasterDetails?.batchSchNo
                            ?: "")

                }
                else {
                    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    val formatter = SimpleDateFormat("MMM dd,yyyy")
                    val formattedDate =
                        parser.parse(
                            batchList.get(position).batchScheduleMasterDetails?.dataEntryDateTime
                                ?: ""
                        )?.let {
                            formatter.format(
                                it
                            )
                        }
                    itemView.dateTimeTV.text = formattedDate
                }



                itemView.customerName.text =
                    batchList.get(position).customerInformationDetails?.customerOrganisationName.toString()


                itemView.tv_quantity.text =
                    batchList.get(position).batchScheduleMasterDetails?.let {
                        String.format(
                            "%.2f",
                            it.totalBatchQuantity
                        ).toDouble().toString()
                    }


                itemView.materialDetailsTV.text =
                    batchList.get(position).batchScheduleMasterDetails?.cPProcessStageNo.toString() + "/" +
                            batchList.get(position).batchScheduleMasterDetails?.processMachineNo



                itemView.selectedCB.setOnCheckedChangeListener { _, b ->

                    if (b) {
                        itemView.selectedCB.isChecked = true
                        if (checkedPosition != adapterPosition) {
                            notifyItemChanged(checkedPosition);
                            checkedPosition = adapterPosition;
                        }

                        onItemClickListener.onItemClick(batchList.get(position), 0, position)
                    }


                }


            } catch (e: Exception) {
                e.printStackTrace()

            }

        }
    }

}
