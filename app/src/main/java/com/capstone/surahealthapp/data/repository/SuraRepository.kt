package com.capstone.surahealthapp.data.repository

import com.capstone.surahealthapp.data.model.PertolonganDummy
import com.capstone.surahealthapp.data.model.PertolonganPertama
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SuraRepository {
    private val pertolongan = mutableListOf<PertolonganPertama>()

    init {
        if (pertolongan.isEmpty()){
            PertolonganDummy.dataDummy.forEach{
                pertolongan.add(it)
            }
        }
    }

    fun getAllPertolonganPertama(): Flow<List<PertolonganPertama>> {
        return flowOf(pertolongan)
    }

    fun getPertolonganPertamaById(pertolonganId : Long): PertolonganPertama? {
        return pertolongan.find{
            it.id == pertolonganId
        }
    }

    companion object {
        @Volatile
        private var instance: SuraRepository? = null

        fun getInstance(): SuraRepository =
            instance ?: synchronized(this) {
                SuraRepository().apply {
                    instance = this
                }
            }
    }
}