package com.example.asystent

import androidx.room.*

@Entity(tableName = "oceny_table")
data class Oceny(
    @PrimaryKey(autoGenerate = true) var _id: Int?,
    @ColumnInfo (name = "nazwa_zajec") var nazwaZajec : String,
    @ColumnInfo(name = "nr_albumu") var nrAlbumu : Int,
    @ColumnInfo (name = "nazwa_oceny") var nazwaOceny : String,
    @ColumnInfo (name = "wartosc_oceny") var wartoscOceny : String
) {
    @Ignore
    constructor(nazwaZajec: String, nrAlbumu: Int, nazwaOceny: String, wartoscOceny: String) :
            this (null, nazwaZajec, nrAlbumu, nazwaOceny, wartoscOceny)
}
