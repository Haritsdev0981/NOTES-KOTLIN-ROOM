package com.harets.notesapp.data.room

import androidx.room.TypeConverter
import com.harets.notesapp.data.entity.Priority

class Converter {

    // untuk konvert dari priority enum class ke string
    // fungsi ini dipanggil ketika get sebuah database
    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    // ini untuk konvert sebuah string kedalam emun class priority
    // fungsi ini dipanggil ketika add dan update sebuah kedalam database
    @TypeConverter
    fun toPriority(priority: String): Priority{
        return Priority.valueOf(priority)
    }
}