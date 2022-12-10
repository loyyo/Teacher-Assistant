package com.example.asystent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Uczniowie :: class, Zajecia :: class, ZajeciaUcznia :: class, Oceny :: class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun uczniowieDao() : UczniowieDao
    abstract fun zajeciaDao() : ZajeciaDao
    abstract fun zajeciaUczniaDao() : ZajeciaUczniaDao
    abstract fun ocenyDao() : OcenyDao

    companion object {

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context : Context): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}