package com.example.asystent

import androidx.room.*

@Dao
interface ZajeciaUczniaDao {

    @Query("SELECT * FROM zajecia_ucznia_table")
    fun getAll(): MutableList<ZajeciaUcznia>

    @Insert
    suspend fun insert(zajecia_ucznia: ZajeciaUcznia)

    @Delete
    suspend fun delete(zajecia_ucznia: ZajeciaUcznia)

    @Query("DELETE FROM zajecia_ucznia_table")
    suspend fun deleteAll()
}