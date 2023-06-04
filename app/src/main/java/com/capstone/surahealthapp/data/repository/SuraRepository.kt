package com.capstone.surahealthapp.data.repository

import androidx.lifecycle.LiveData
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaDao
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaEntity
import kotlinx.coroutines.flow.Flow

class SuraRepository private constructor(private val dao: PertolonganPertamaDao) {

    fun getPertolonganPertamaById(itemId: Long): LiveData<PertolonganPertamaEntity> {
        return dao.getPertolonganPertamaById(itemId)
    }
    fun getAllPertolonganPertama(): LiveData<List<PertolonganPertamaEntity>> {
        return dao.getAllPertolonganPertama()
    }

    fun searchPertolonganPertama(query: String): Flow<List<PertolonganPertamaEntity>> {
        return dao.searchPertolonganPertama(query)
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