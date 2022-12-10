package com.example.asystent

import androidx.room.*

@Dao
interface UczniowieDao {

    @Query("SELECT * FROM uczniowie_table")
    fun getAll(): MutableList<Uczniowie>

//    @Query("SELECT * FROM uczniowie_table WHERE nr_albumu LIKE :nrAlbumu LIMIT 1")
//    suspend fun findByNrAlbumu(nrAlbumu: Int): Uczniowie

    @Insert
    suspend fun insert(uczniowie: Uczniowie)

    @Delete
    suspend fun delete(uczniowie: Uczniowie)

    @Query("DELETE FROM uczniowie_table")
    suspend fun deleteAll()
}