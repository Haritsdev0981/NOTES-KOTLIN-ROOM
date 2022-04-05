package com.harets.notesapp.utils

import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.card.MaterialCardView
import com.harets.notesapp.R
import com.harets.notesapp.data.entity.Notes
import com.harets.notesapp.data.entity.Priority
import com.harets.notesapp.ui.home.HomeFragmentDirections

object BindingAdapters {

    // ini akan memanggil didalam xml
    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColors(cardView: MaterialCardView, priority: Priority){
        when(priority){
            Priority.HIGH ->{cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink))}
            Priority.MEDIUM ->{cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))}
            Priority.LOW ->{cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))}
        }
    }

    @BindingAdapter("android:sendDataToDetail")
    @JvmStatic
    fun sendDataToDetail(view: ConstraintLayout, currentItem: Notes){
        view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(view: Spinner, priority: Priority) {
        when (priority) {
            Priority.LOW -> view.setSelection(2)
            Priority.MEDIUM -> view.setSelection(1)
            Priority.HIGH -> view.setSelection(0)
        }
    }
}