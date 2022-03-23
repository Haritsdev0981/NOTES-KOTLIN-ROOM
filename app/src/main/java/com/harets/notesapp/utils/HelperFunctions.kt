package com.harets.notesapp.utils

import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.harets.notesapp.R

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
}