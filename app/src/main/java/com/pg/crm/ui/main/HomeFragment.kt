package com.pg.crm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.pg.crm.R
import com.pg.crm.databinding.FragmentHomeBinding
import com.pg.crm.utils.ItemOffsetDecoration

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var homeRecyclerAdapter: HomeRecyclerAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        var navController = findNavController()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        activity?.run {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.updateActionBarTitle(getString(R.string.dashBoard))
            viewModel.checkIsVisible(true)
        } ?: throw Throwable("invalid activity")

        val recyclerView = _binding?.homeRV

        recyclerView!!.layoutManager = GridLayoutManager(activity, 3)
        recyclerView!!.addItemDecoration(ItemOffsetDecoration(requireActivity(), R.dimen._5sdp))
        recyclerView!!.setHasFixedSize(true)

        val listener = object : HomeRecyclerAdapter.HomeViewHolderListener {

            override fun onCustomItemClicked(homeData: HomeData) {
                when (homeData.title) {
                    getString(R.string.material_inward) -> {
                        var action = HomeFragmentDirections.actionNavHomeToMaterialInwardFragment()
                        navController.navigate(action)
                    }

                    getString(R.string.weighment) -> {
                        var action = HomeFragmentDirections.actionNavHomeToWeightmentFragment()
                        navController.navigate(action)
                    }

                    getString(R.string.batch_schedule) -> {
                        var action = HomeFragmentDirections.actionNavHomeToBatchScheduleFragment()
                        navController.navigate(action)
                    }

                    getString(R.string.hardening_process) -> {
                        var action =
                            HomeFragmentDirections.actionNavHomeToHardeningProcessFragment()
                        navController.navigate(action)
                    }

                    getString(R.string.hardening_process_vaccum) -> {
                        var action =
                            HomeFragmentDirections.actionNavHomeToHardeningProcessVacuumFragment()
                        navController.navigate(action)
                    }

                    getString(R.string.tp_batch_sch) -> {
                        var action =
                            HomeFragmentDirections.actionNavHomeToTpBatchSchFragment()
                        navController.navigate(action)
                    }

                    getString(R.string.tampering_process) -> {
                        var action =
                            HomeFragmentDirections.actionNavHomeToTemperingProcessFragment()
                        navController.navigate(action)
                    }
                    getString(R.string.customer_wise_item_rate_details) -> {
                        var action =
                            HomeFragmentDirections.actionNavHomeToCustomerwiseitemratedetailsFragment()
                        navController.navigate(action)
                    }



                }

            }

        }

        homeRecyclerAdapter = HomeRecyclerAdapter(context, listener)
        //set the CustomAdapter
        recyclerView.adapter = homeRecyclerAdapter
        getAllDev()
        return binding.root

    }

    private fun getAllDev() {
        ///get the list of dev from api response
        homeViewModel!!.userLiveData!!.observe(viewLifecycleOwner, Observer { it ->
            homeRecyclerAdapter?.setDeveloperList(it as ArrayList<HomeData>)
        })
    }


}

