package com.pg.crm.ui.weighment

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pg.crm.R
import com.pg.crm.interfaces.OnItemClickListener
import com.pg.crm.model.GetMaterialResponse
import kotlinx.android.synthetic.main.weightment_pending_row.view.customerNameTV
import kotlinx.android.synthetic.main.weightment_pending_row.view.dateTimeTV
import kotlinx.android.synthetic.main.weightment_pending_row.view.igpNoTV
import kotlinx.android.synthetic.main.weightment_pending_row.view.materialDetailsTV
import kotlinx.android.synthetic.main.weightment_pending_row.view.selectedCB
import kotlinx.android.synthetic.main.weightment_pending_row.view.serialNumberTV
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class WeighmentPendingInwardAdapter(
    var context: Context?,
    var materialList: List<GetMaterialResponse>,
    var onItemClickListener: OnItemClickListener<GetMaterialResponse>
) : RecyclerView.Adapter<WeighmentPendingInwardAdapter.RecyclerViewHolder>() {


    private var serialNum = 0
    private var checkedPosition = -1

    var lastItemSelectedPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.weightment_pending_row, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return materialList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//        serialNum=0
        Log.d("check", materialList.size.toString() + "..." + position)
        holder.bindPerformersChild(materialList.get(position), position)
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPerformersChild(getMaterialResponse: GetMaterialResponse, position: Int) {
            try {
                serialNum += 1
                itemView.serialNumberTV.text = "${position + 1}"
                if (checkedPosition == -1) {
                    itemView.selectedCB.isChecked=false
                } else {
                    itemView.selectedCB.isChecked = checkedPosition == adapterPosition
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val parsedDate = LocalDateTime.parse(
                        getMaterialResponse.dataEntryDateTime,
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                    val formattedDate =
                        parsedDate.format(DateTimeFormatter.ofPattern("MMM dd,yyyy HH:mm"))
                    itemView.dateTimeTV.text = formattedDate

                } else {
//                    2023-08-04T09:31:00
                    val date = formatDateStr(getMaterialResponse.dataEntryDateTime);
                    println(date)
                    itemView.dateTimeTV.text = date.toString()
                }

                itemView.igpNoTV.text = getMaterialResponse.inwardGateEntryNo.toString()
                itemView.customerNameTV.text = getMaterialResponse.igeCustomerName
                itemView.materialDetailsTV.text = getMaterialResponse.igeMaterialDetails

                itemView.selectedCB.setOnCheckedChangeListener { _, b ->

                    if(b){
                        itemView.selectedCB.isChecked=true
                        if (checkedPosition != adapterPosition) {
                            notifyItemChanged(checkedPosition);
                            checkedPosition = adapterPosition;
                        }

                        onItemClickListener.onItemClick(getMaterialResponse,materialList.get(materialList.size-1).inwardGateEntryNo!!,position)
                    }


                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    @Throws(ParseException::class)
    fun formatDateStr(strDate: String?): String? {
        val inputFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        inputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"))
        val outputFormatter: DateFormat = SimpleDateFormat("MMM d, yyyy HH:mm" , Locale.ENGLISH)
        return inputFormatter.parse(strDate)?.let { outputFormatter.format(it) }
    }

}
