package com.harets.notesapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.harets.notesapp.R
import com.harets.notesapp.data.entity.Notes
import com.harets.notesapp.data.entity.Priority
import com.harets.notesapp.databinding.RowItemNotesBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){

    val listNotes = ArrayList<Notes>()
    inner class MyViewHolder(val binding: RowItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    // langsung menginisialisai class viewHolder nya
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listNotes[position]
        holder.binding.apply {

            mNotes = data
            executePendingBindings()

//            tvTitle.text = data.title
//            tvDescription.text = data.description
//            tvDate.text = data.date

//            val pink = ContextCompat.getColor(priorityIndicator.context, R.color.pink)
//            val yellow = ContextCompat.getColor(priorityIndicator.context, R.color.yellow)
//            val green = ContextCompat.getColor(priorityIndicator.context, R.color.green)
//            when(data.priority){
//                Priority.HIGH -> priorityIndicator.setBackgroundColor(pink)
//                Priority.MEDIUM -> priorityIndicator.setBackgroundColor(yellow)
//                Priority.LOW -> priorityIndicator.setBackgroundColor(green)
//            }
//            when(data.priority){
//                Priority.HIGH -> {
//                    // cara memanggil warna di object
//                    val pink = ContextCompat.getColor(priorityIndicator.context, R.color.pink)
//                    priorityIndicator.setCardBackgroundColor(pink)
//                }
//                Priority.MEDIUM -> {
//                    val yellow = ContextCompat.getColor(priorityIndicator.context, R.color.yellow)
//                    priorityIndicator.setCardBackgroundColor(yellow)
//                }
//                Priority.LOW -> {
//                    val green = ContextCompat.getColor(priorityIndicator.context, R.color.green)
//                    priorityIndicator.setCardBackgroundColor(green)
//                }
//            }
        }
    }

    override fun getItemCount() = listNotes.size

    fun setData(data: List<Notes>?){
        if (data == null) return
        val diffCalback = DiffCallback(listNotes, data)
        val diffCallbackResult =  DiffUtil.calculateDiff(diffCalback)
        listNotes.clear()
        listNotes.addAll(data)
        diffCallbackResult.dispatchUpdatesTo(this)
    }
}