package com.example.asystent

import androidx.room.*

@Entity(tableName = "zajecia_table")
data class Zajecia(
    @PrimaryKey(autoGenerate = true) var _id: Int?,
    @ColumnInfo (name = "nazwa_zajec") var nazwaZajec : String,
    @ColumnInfo (name = "dzien") var dzien : String,
    @ColumnInfo (name = "godzina") var godzina : String
    ) {
    @Ignore
    constructor(nazwaZajec: String, dzien: String, godzina: String) : this (null, nazwaZajec, dzien, godzina)
}
