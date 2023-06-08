package com.capstone.surahealthapp.data.repository

import com.capstone.surahealthapp.data.model.RumahSakit
import com.capstone.surahealthapp.data.model.RumahSakitData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RumahSakitRepository {
    private val rm = mutableListOf<RumahSakit>()

    init {
        if (rm.isEmpty()) {
            RumahSakitData.rumahSakit.forEach {
                rm.add(it)
            }
        }
    }

    fun getAllRumahSakit(): Flow<List<RumahSakit>> {
        return flowOf(rm)
    }

    fun getRumahSakitById(rumahSakitId: Long): RumahSakit {
        return rm.first{
            it.id == rumahSakitId
        }
    }

    companion object {
        @Volatile
        private var instance: RumahSakitRepository? = null

        fun getInstance(): RumahSakitRepository =
            instance ?: synchronized(this) {
                RumahSakitRepository().apply {
                    instance = this
                }
            }
    }
}