package com.example.greendotchallenge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.greendotchallenge.R
import kotlinx.android.synthetic.main.viewgroup_fib_item.view.*

class FibNumberRecyclerAdapter (private val context: FragmentActivity)  : RecyclerView.Adapter<FibNumberRecyclerAdapter.ViewHolder>() {

    private var fibResultMap: Map<Int,Int> = emptyMap()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.viewgroup_fib_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = fibResultMap.size



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            number.text = position.toString()//did not need to actually print the map values from the stored values
            fibNumber.text = fibResultMap.get(position).toString()
        }
    }

    //updates the live data from the observer
    fun updateData(data: Map<Int,Int>) {
        fibResultMap = data
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = view.textview_number
        val fibNumber = view.textview_fibnumber
    }
}
