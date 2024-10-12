package com.pg.crm.ui.materialInward

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.pg.crm.R
import com.pg.crm.model.CustomerInformationModel
import com.pg.crm.model.ProductNameModel
import kotlinx.android.synthetic.main.spinner_item.view.*

class MaterialTypeAdapter (ctx: Context, fontList: ArrayList<String>) : ArrayAdapter<String>(ctx, 0, fontList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val productData = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item,
            parent,
            false
        )

        productData?.let {
            view.itemTV.text =productData.toString()
        }


        return view
    }
}