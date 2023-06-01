package com.capstone.surahealthapp.di

import com.capstone.surahealthapp.data.repository.SuraRepository

object Injection {
    fun provideRepository(): SuraRepository{
        return SuraRepository.getInstance()
    }
}