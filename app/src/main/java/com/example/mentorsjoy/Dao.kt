package com.example.mentorsjoy

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {
    @Insert
    fun insert(pdf: PdfData)
    @Query("SELECT * FROM PDfs")
    fun getAll(): Flow<List<PdfData>>
    @Query("Select * FROM PDFs WHERE :s = projectName")
    fun get(s: String?) : List<PdfData>
    @Delete
    fun delete(pdf: PdfData)
}