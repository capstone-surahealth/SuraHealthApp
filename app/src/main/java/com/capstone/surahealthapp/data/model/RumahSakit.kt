package com.capstone.surahealthapp.data.model

data class RumahSakit(
    val id: Long,
    val nama_rm: String,
    val rm_photoUrl: String,
    val kamar_kosong: Int,
    val kelas: String,
    val lat: Double,
    val lng: Double,
    val phone: String
)
