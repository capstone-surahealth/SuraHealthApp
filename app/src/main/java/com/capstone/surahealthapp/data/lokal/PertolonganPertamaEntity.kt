package com.capstone.surahealthapp.data.lokal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pertolongan_table")
data class PertolonganPertamaEntity(
    val pertolongan_pertama : String,
    val photoUrl : String,
    val deskripsi : String,
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
)