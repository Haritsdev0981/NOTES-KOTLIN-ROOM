package com.harets.notesapp.utils

import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.harets.notesapp.R
import com.harets.notesapp.data.entity.Priority

object HelperFunctions {

    fun spinnerListener(context : Context?, priorityIndicator : CardView): AdapterView.OnItemSelectedListener = object :AdapterView.OnItemSelectedListener{
        override fun onItemSelected(paret: AdapterView<*>?, view: View?, position: Int, id: Long) {
            context?.let {
                when(position){
                    0 -> {
                        // cara memanggil warna di object
                        val pink = ContextCompat.getColor(it, R.color.pink)
                        priorityIndicator.setCardBackgroundColor(pink)
                    }
                    1 -> {
                        val yellow = ContextCompat.getColor(it, R.color.yellow)
                        priorityIndicator.setCardBackgroundColor(yellow)
                    }
                    2 -> {
                        val green = ContextCompat.getColor(it, R.color.green)
                        priorityIndicator.setCardBackgroundColor(green)
                    }
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }
    fun parseToPriority(priority: String, context: Context?): Priority {
        val expectedPriority = context?.resources?.getStringArray(R.array.priorities)
        return when (priority) {
            expectedPriority?.get(0) -> Priority.HIGH
            expectedPriority?.get(1) -> Priority.MEDIUM
            expectedPriority?.get(2) -> Priority.LOW
            else -> Priority.HIGH
        }
    }
}