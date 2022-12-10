package com.example.asystent

import androidx.room.*

@Dao
interface OcenyDao {

    @Query("SELECT * FROM oceny_table")
    fun getAll(): MutableList<Oceny>

    @Insert
    suspend fun insert(oceny: Oceny)

    @Delete
    suspend fun delete(oceny: Oceny)

    @Query("DELETE FROM oceny_table")
    suspend fun deleteAll()
}