package com.example.greendotchallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.greendotchallenge.models.FibModel
import com.example.greendotchallenge.R
import com.example.greendotchallenge.adapters.SummaryRecyclerAdapter
import com.example.greendotchallenge.viewmodels.FibListViewModel
import kotlinx.android.synthetic.main.fragment_summary.*
import kotlinx.android.synthetic.main.viewgroup_fib_item.view.*

class FragmentSummary: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_summary, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Since this application does not have a supportActionbar (which was on purpose), this is the tool bar that allows for navigation back through the fragment stack
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24px)
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack();
        }
        toolbar.title = "Summary"

        //title elements for the chart
        include_chart_title_summary.textview_number.text = "Input"
        include_chart_title_summary.textview_fibnumber.text = "Time"

        //set up for the recycler view for the summary that are going to be generated here
        recyclerview_summary.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        val adapter = SummaryRecyclerAdapter(requireActivity())
        recyclerview_summary.adapter = adapter

        //Observes for changes in the live data, and updates the recycler adapter accordingly
        FibListViewModel.getFibList().observe(requireActivity(), Observer<MutableList<FibModel>> {
            if (!it.isNullOrEmpty()) {
                adapter.updateData(it)
            }
        })

    }
}