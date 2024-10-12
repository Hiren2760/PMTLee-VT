package com.pg.crm.ui.weighment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pg.crm.R
import com.pg.crm.model.GetAllInwardWeighingProductsResponse
import kotlinx.android.synthetic.main.weightment_pending_row.view.serialNumberTV
import kotlinx.android.synthetic.main.weightment_product_row.view.*
import java.text.DecimalFormat

class AllInwardWeightmentProductAdapter(
    var context: Context?,
    var materialList: MutableList<GetAllInwardWeighingProductsResponse>?

) : RecyclerView.Adapter<AllInwardWeightmentProductAdapter.RecyclerViewHolder>() {

    private var serialNum = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.weightment_product_row, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return materialList?.size ?:0
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//        serialNum=0
        Log.d("check", materialList?.size.toString() + "..." + position)
        materialList?.let { holder.bindPerformersChild(it.get(position), position) }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPerformersChild(
            getProductResponse: GetAllInwardWeighingProductsResponse,
            position: Int) {
            try {


                if((getProductResponse.productName)?.contains(",")==true){
                    serialNum += 1
                    itemView.serialNumberTV.text = "${position + 1}"
                    val productName = getProductResponse.productName?.substringBefore(",")
                    itemView.productCodeTV.text=productName
                    itemView.grosswtTV.text  = String.format("%.2f", getProductResponse.grossWeightMaterial).toDouble().toString()
                    itemView.totalWtTV.text   = String.format("%.2f", getProductResponse.totalTareWeightContainers).toDouble().toString()
                    itemView.netwtTV.text = String.format("%.2f", getProductResponse.netWeightMaterial).toDouble().toString()
                }else{
                    itemView.serialNumberTV.text = getProductResponse.weighingProdSlNo.toString()
                    itemView.productCodeTV.text = getProductResponse.productName
                    itemView.grosswtTV.text = getProductResponse.grossWeightMaterial.toString()
                    itemView.totalWtTV.text = getProductResponse.totalTareWeightContainers.toString()
                    itemView.netwtTV.text = getProductResponse.netWeightMaterial.toString()

                }
                itemView.qty_containerTV.text=getProductResponse.weightQuantity.toString()+"/"+getProductResponse.weightContainersNos



            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


}