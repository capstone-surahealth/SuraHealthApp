package com.capstone.surahealthapp.data.repository

import androidx.lifecycle.LiveData
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaDao
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaEntity
import kotlinx.coroutines.flow.Flow

class PertolonganPertamaRepository private constructor(private val dao: PertolonganPertamaDao) {

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
        private var instance: PertolonganPertamaRepository? = null

        fun getInstance(dao: PertolonganPertamaDao): PertolonganPertamaRepository =
            instance ?: synchronized(this) {
                instance ?: PertolonganPertamaRepository(dao).also { instance = it }
            }
    }
}