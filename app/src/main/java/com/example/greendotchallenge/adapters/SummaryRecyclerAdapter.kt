package com.example.greendotchallenge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.greendotchallenge.models.FibModel
import com.example.greendotchallenge.R
import kotlinx.android.synthetic.main.viewgroup_fib_item.view.*

class SummaryRecyclerAdapter (private val context: FragmentActivity)  : RecyclerView.Adapter<SummaryRecyclerAdapter.ViewHolder>() {

     var fibResultList: MutableList<FibModel> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.viewgroup_fib_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = fibResultList.size



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            number.text = fibResultList[position].inputNumber.toString()
            time.text = fibResultList[position].processTime.toString() + "ms"
        }
    }

    fun updateData(data:MutableList<FibModel> ) {
        fibResultList = data
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = view.textview_number
        val time = view.textview_fibnumber
    }
}
