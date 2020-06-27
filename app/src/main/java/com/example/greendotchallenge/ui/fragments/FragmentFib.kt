package com.example.greendotchallenge.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.greendotchallenge.models.FibModel
import com.example.greendotchallenge.R
import com.example.greendotchallenge.adapters.FibNumberRecyclerAdapter
import com.example.greendotchallenge.viewmodels.FibListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fib.*
import kotlinx.android.synthetic.main.viewgroup_cardview_number_input.*
import kotlinx.android.synthetic.main.viewgroup_cardview_number_input.view.*
import kotlinx.android.synthetic.main.viewgroup_fib_item.view.*

class FragmentFib : Fragment() {

    lateinit var progressBar: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_fib, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //title elements for the chart
        include_chart_title.textview_number.text = "  N"
        include_chart_title.textview_fibnumber.text = "F(N)"

        //set up for the recycler view for the fibonacci numbers that are going to be generated here
        recyclerview_numbers.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        val adapter = FibNumberRecyclerAdapter(requireActivity())
        recyclerview_numbers.adapter = adapter

        //handles the summary button, for transitioning to the summary fragment
        include_input_number.button_summary.setOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, FragmentSummary()).addToBackStack("FragmentFib").commit()
        }

        //when user presses enter this will process the user's input, checking to make sure it is not blank or null and pass it to the view model to calculate number
        include_input_number.editText_number_input.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val input = editText_number_input.text!!.toString().toInt()
                if (editText_number_input.text.isNullOrBlank() || editText_number_input.text.toString() != "") {
                    if (input >= 37) {//above 38 and my emulator started to take forever to calculate fib, this can be changed
                        Toast.makeText(requireContext(), "Please keep input <38", Toast.LENGTH_LONG).show()
                    } else {
                        progressBar = ProgressDialog.show(requireActivity().supportFragmentManager).apply {//progress indicator so it doesn't seem to the user that nothing is happening behind the scenes
                            FibListViewModel.setFibData(input) // calculate fib numbers
                        }
                    }
                }
                return@OnKeyListener true
            }
            false
        })


        //Observes for changes in the live data, and updates the recycler adapter accordingly
        FibListViewModel.getFibList().observe(requireActivity(), Observer<MutableList<FibModel>> {
            if (!it.isNullOrEmpty()) {
                progressBar.dismiss() //dismisses loading dialog
                adapter.updateData(it.last().fibNumbers)//updates recycler view
                try {
                    //This displays the computation time. I Decided to go with the snack bar because it felt more aesthetically pleasing, I could have made a viewgroup element below the recyclerView, but it just didn't feel right
                    Snackbar.make(view, "This process took " + it.last().processTime.toString() + " milliseconds", Snackbar.LENGTH_LONG).show()
                } catch (e: IllegalArgumentException) {
                    "IllegalArgumentException: this happens when you move between fragments while view is visible and it doesn't have a valid context"
                }
            }
        })
    }
}
