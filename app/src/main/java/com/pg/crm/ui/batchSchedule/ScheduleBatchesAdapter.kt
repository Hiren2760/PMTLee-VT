package com.pg.crm.ui.batchSchedule

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pg.crm.R
import com.pg.crm.model.BatchScheduleGridResponseItem

import kotlinx.android.synthetic.main.weightment_product_row.view.grosswtTV
import kotlinx.android.synthetic.main.weightment_product_row.view.llMain
import kotlinx.android.synthetic.main.weightment_product_row.view.netwtTV
import kotlinx.android.synthetic.main.weightment_product_row.view.productCodeTV
import kotlinx.android.synthetic.main.weightment_product_row.view.qty_containerTV
import kotlinx.android.synthetic.main.weightment_product_row.view.serialNumberTV
import kotlinx.android.synthetic.main.weightment_product_row.view.totalWtTV

import java.text.SimpleDateFormat

import java.util.Locale

class ScheduleBatchesAdapter(
    var context: Context?, var batchList: List<BatchScheduleGridResponseItem>
) : RecyclerView.Adapter<ScheduleBatchesAdapter.RecyclerViewHolder>() {


    private var serialNum = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.weightment_product_row, parent, false)
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
            getBatchResponse: BatchScheduleGridResponseItem, position: Int
        ) {
            try {
                serialNum += 1
                itemView.llMain.setBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.button_fade
                    )
                )
                itemView.serialNumberTV.text = "${position + 1}"
                itemView.productCodeTV.text =
                    getBatchResponse.batchScheduleMasterDetails?.processMachineNo.toString()

                val batchScheduleDate =
                    getBatchResponse.batchScheduleMasterDetails?.propBatchShcheduleDate
                val batchScheduleTime =
                    getBatchResponse.batchScheduleMasterDetails?.propBatchShcheduleTime

                val formattedDate = batchScheduleDate?.let { formatDate(it) }
                val formattedTime = batchScheduleTime?.let { formatTime(it) }
                val batchNo = getBatchResponse.batchScheduleMasterDetails?.batchSchNo

                itemView.qty_containerTV.text = "$formattedDate & $formattedTime/$batchNo"
                itemView.grosswtTV.text =
                    "${getBatchResponse.batchScheduleMasterDetails?.processNameCode} "
                itemView.totalWtTV.text =
                    getBatchResponse.batchScheduleMasterDetails?.totalBatchQuantity.toString()
                itemView.netwtTV.text = getBatchResponse.currentStatus

            } catch (e: Exception) {
                e.printStackTrace()

            }

        }
    }

    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }

    fun formatTime(timeString: String): String {
        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val time = inputFormat.parse(timeString)
        return outputFormat.format(time!!)
    }
}
