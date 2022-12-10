package com.example.asystent

import androidx.room.*

@Entity(tableName = "zajecia_ucznia_table")
data class ZajeciaUcznia(
    @PrimaryKey(autoGenerate = true) var _id: Int?,
    @ColumnInfo(name = "nazwa_zajec") var nazwaZajec : String,
    @ColumnInfo(name = "nr_albumu") var nrAlbumu : Int,
) {
    @Ignore
    constructor(nazwaZajec: String, nrAlbumu: Int) :
            this (null, nazwaZajec, nrAlbumu)
}
