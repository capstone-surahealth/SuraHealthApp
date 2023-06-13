package com.capstone.surahealthapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RumahSakitResponse (
    @SerializedName("Kode Rumah Sakit")
    val kodeRumahSakit: String?,
    @SerializedName("Nama Rumah Sakit")
    val namaRumahSakit: String?,
    @SerializedName("Tipe")
    val tipe: String?,
    @SerializedName("Alamat")
    val alamat: String?,
    @SerializedName("Telepon")
    val telepon: String?,
    @SerializedName("Kelas")
    val kelas: String?,
    @SerializedName("Jumlah Bed")
    val jumlahBed: Int?,
    @SerializedName("BPJS")
    val bpjs: Int?,
    @SerializedName("No WhatsApp")
    val wa: String?,
    @SerializedName("Foto Rumah Sakit")
    val foto: String?,
    @SerializedName("Longitude")
    val longitude: Double?,
    @SerializedName("Latitude")
    val latitude: Double?
) : Parcelable