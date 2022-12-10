package com.example.asystent

import androidx.room.*

@Dao
interface ZajeciaDao {

    @Query("SELECT * FROM zajecia_table")
    fun getAll(): MutableList<Zajecia>

    @Insert
    suspend fun insert(zajecia: Zajecia)

    @Delete
    suspend fun delete(zajecia: Zajecia)

    @Query("DELETE FROM zajecia_table")
    suspend fun deleteAll()
}