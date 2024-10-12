package com.pg.crm.ui.main


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pg.crm.R
import kotlinx.android.synthetic.main.home_list_item.view.*
import kotlin.coroutines.coroutineContext

class HomeRecyclerAdapter(var context: Context?,private val listener: HomeViewHolderListener) :
    RecyclerView.Adapter<HomeRecyclerAdapter.RecyclerViewHolder>() {
    var homeArrayList: ArrayList<HomeData>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return homeArrayList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//        serialNum=0
        holder.bindPerformersChild(homeArrayList!!.get(position), position)
    }

    fun setDeveloperList(mhomeArrayList: ArrayList<HomeData>) {
        homeArrayList = mhomeArrayList
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("ResourceAsColor")
        fun bindPerformersChild(homeData: HomeData, position: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                itemView.img_pic.colorFilter =
                    BlendModeColorFilter(R.color.black, BlendMode.SRC_ATOP)
            } else {
                itemView.img_pic.setColorFilter(R.color.black, PorterDuff.Mode.SRC_ATOP)
            }
             itemView.img_pic.setColorFilter(ContextCompat.getColor((context as MainActivity),R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            itemView.img_pic.setImageResource(homeData.image)
            itemView.tv_usernme.text = (homeData.title)
            itemView.homeCardView.setOnClickListener {
                listener.onCustomItemClicked(homeData)
            }

        }
    }

    interface HomeViewHolderListener {
        fun onCustomItemClicked(x: HomeData)
    }
}






