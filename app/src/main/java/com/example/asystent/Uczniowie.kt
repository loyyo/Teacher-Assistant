package com.example.asystent

import androidx.room.*

@Entity(tableName = "uczniowie_table")
data class Uczniowie(
    @PrimaryKey(autoGenerate = true) var _id: Int?,
    @ColumnInfo(name = "imie_nazwisko") var imieNazwisko : String,
    @ColumnInfo(name = "nr_albumu") var nrAlbumu : Int
    ) {
    @Ignore
    constructor(imieNazwisko: String, nrAlbumu: Int) : this (null, imieNazwisko, nrAlbumu)
}