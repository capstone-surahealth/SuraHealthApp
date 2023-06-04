package com.capstone.surahealthapp.di

import android.content.Context
import com.capstone.surahealthapp.data.lokal.PertolonganDatabase
import com.capstone.surahealthapp.data.repository.SuraRepository

object Injection {
    fun provideRepository(context: Context): SuraRepository {
        val dao = PertolonganDatabase.getInstance(context)!!.dao
        return SuraRepository.getInstance(dao)
    }
}