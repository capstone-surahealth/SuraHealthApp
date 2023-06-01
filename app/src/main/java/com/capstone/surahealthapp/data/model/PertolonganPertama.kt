package com.capstone.surahealthapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PertolonganPertama(
    val id : Long,
    val pertolongan_pertama : String,
    val photoUrl : String,
    val deskripsi : String
): Parcelable
