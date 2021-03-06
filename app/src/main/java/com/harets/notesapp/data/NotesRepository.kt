package com.harets.notesapp.data

import androidx.lifecycle.LiveData
import com.harets.notesapp.data.entity.Notes
import com.harets.notesapp.data.room.NotesDao

class NotesRepository(private val notesDao: NotesDao) {

    fun getAllData() : LiveData<List<Notes>> = notesDao.getAllData()

    // fungsi ini untuk inset/add data ke dalam database
    suspend fun insertNotes(notes: Notes){
        notesDao.insertNotes(notes)
    }

    // single Expression kotlin
    fun sortByHighPriority() : LiveData<List<Notes>> = notesDao.sortByHighPriority()
    fun sortByLowPriority() : LiveData<List<Notes>> = notesDao.sortByLowPriority()

    suspend fun deleteAllData() = notesDao.deleteAllData()

    fun searchByQuery(query: String) : LiveData<List<Notes>> {
        return notesDao.searchByQuery(query)
    }

    suspend fun deleteNote(notes: Notes) = notesDao.deleteNote(notes)

    suspend fun updateNotes(notes: Notes) = notesDao.updateNote(notes)
}