package com.pg.crm.ui.weighment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.pg.crm.R
import com.pg.crm.model.CustomerInformationModel
import com.pg.crm.model.MaterialLocationData
import kotlinx.android.synthetic.main.spinner_item.view.*

class CustomerInfoAdapter (ctx: Context, fontList: ArrayList<CustomerInformationModel>) : ArrayAdapter<CustomerInformationModel>(ctx, 0, fontList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val materialLocationData = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item,
            parent,
            false
        )

        materialLocationData?.let {
            view.itemTV.text =materialLocationData.customerManagementDesignation
        }


        return view
    }
}