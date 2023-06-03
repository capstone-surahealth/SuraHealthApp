package com.capstone.surahealthapp.view.pertolonganpertama

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.surahealthapp.data.lokal.PertolonganDatabase

class PPViewModelFactory constructor(private val ppDatabase: PertolonganDatabase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PertolonganPertamaViewModel::class.java)) {
            PertolonganPertamaViewModel(ppDatabase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}