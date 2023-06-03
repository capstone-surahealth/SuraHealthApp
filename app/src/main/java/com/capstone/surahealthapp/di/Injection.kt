package com.capstone.surahealthapp.di

import com.capstone.surahealthapp.data.lokal.PertolonganPertamaDao
import com.capstone.surahealthapp.data.repository.SuraRepository

object Injection {
    fun provideRepository(dao: PertolonganPertamaDao): SuraRepository{
        return SuraRepository.getInstance(dao)
    }
}