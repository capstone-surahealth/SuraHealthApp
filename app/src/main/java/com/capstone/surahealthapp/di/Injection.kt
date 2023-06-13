package com.capstone.surahealthapp.di

import android.content.Context
import com.capstone.surahealthapp.data.lokal.PertolonganDatabase
import com.capstone.surahealthapp.data.remote.ApiConfig
import com.capstone.surahealthapp.data.repository.PertolonganPertamaRepository
import com.capstone.surahealthapp.data.repository.RumahSakitRepository

object Injection {
    fun provideRepository(context: Context): PertolonganPertamaRepository {
        val dao = PertolonganDatabase.getInstance(context)!!.dao
        return PertolonganPertamaRepository.getInstance(dao)
    }

    fun provideRumahSakitRepository(): RumahSakitRepository {
        val apiService = ApiConfig.getApiService()
        return RumahSakitRepository.getInstance(apiService)
    }
}