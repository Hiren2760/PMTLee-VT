package com.pg.crm.interfaces

import android.view.View

interface OnItemClickListener<T> {

    fun  onItemClick(data: T, sl:Int,position: Int)

}