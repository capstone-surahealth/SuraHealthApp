package com.capstone.surahealthapp.data.repository

import androidx.lifecycle.LiveData
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaDao
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaEntity

class SuraRepository private constructor(private val dao: PertolonganPertamaDao) {

    fun getPertolonganPertamaById(itemId: Long): LiveData<PertolonganPertamaEntity> {
        return dao.getPertolonganPertamaById(itemId)
    }

    companion object {
        @Volatile
        private var instance: SuraRepository? = null

        fun getInstance(dao: PertolonganPertamaDao): SuraRepository =
            instance ?: synchronized(this) {
                instance ?: SuraRepository(dao).also { instance = it }
            }
    }
}