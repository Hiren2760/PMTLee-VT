package com.pg.crm.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pg.crm.R


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var userLiveData: MutableLiveData<ArrayList<HomeData>?>? = null
    var userArrayList: ArrayList<HomeData>? = null


    init {
        populateList()
        userLiveData = MutableLiveData()
        userLiveData!!.value = userArrayList
    }

    private fun populateList() {

        userArrayList = ArrayList()
        userArrayList!!.add(
            HomeData(
                R.drawable.material_in_icon,
                getApplication<Application>().resources.getString(R.string.material_inward)
            )
        )
        userArrayList!!.add(
            HomeData(
                R.drawable.ic_material_indent,
                getApplication<Application>().resources.getString(R.string.weighment)
            )
        )
        userArrayList!!.add(
            HomeData(
                R.drawable.batch_schedule_icon,
                getApplication<Application>().resources.getString(R.string.batch_schedule)
            )
        )
        userArrayList!!.add(
            HomeData(
                R.drawable.hardening_process_icon,
                getApplication<Application>().resources.getString(R.string.hardening_process)
            )
        )

        userArrayList!!.add(
            HomeData(
                R.drawable.hardening_process_icon,
                getApplication<Application>().resources.getString(R.string.hardening_process_vaccum)
            )
        )
        userArrayList!!.add(
            HomeData(
                R.drawable.batch_schedule_icon,
                getApplication<Application>().resources.getString(R.string.tp_batch_sch)
            )
        )

        userArrayList!!.add(
            HomeData(
                R.drawable.hardening_process_icon,
                getApplication<Application>().resources.getString(R.string.tampering_process)
            )
        )



//        userArrayList!!.add(
//            HomeData(
//                R.drawable.annealing_icon,
//                getApplication<Application>().resources.getString(R.string.annealing_process)
//            )
//        )
//        userArrayList!!.add(
//            HomeData(
//                R.drawable.shot_icon,
//                getApplication<Application>().resources.getString(R.string.short_blasting)
//            )
//        )
//        userArrayList!!.add(
//            HomeData(
//                R.drawable.band_saw_icon,
//                getApplication<Application>().resources.getString(R.string.band_sawing)
//            )
//        )
//        userArrayList!!.add(
//            HomeData(
//                R.drawable.cnc_icon,
//                getApplication<Application>().resources.getString(R.string.cnc_process)
//            )
//        )
//        userArrayList!!.add(
//            HomeData(
//                R.drawable.annealing_icon,
//                getApplication<Application>().resources.getString(R.string.induction_process)
//            )
//        )
//        userArrayList!!.add(
//            HomeData(
//                R.drawable.stock_icoon,
//                getApplication<Application>().resources.getString(R.string.stocking_despatch)
//            )
//        )
        userArrayList!!.add(
            HomeData(
                R.drawable.material_out_icon,
                getApplication<Application>().resources.getString(R.string.material_outward)
            )
        )
        userArrayList!!.add(
            HomeData(
                R.drawable.ic_details,
                getApplication<Application>().resources.getString(R.string.customer_wise_item_rate_details)
            )
        )
    }


}