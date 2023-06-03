package com.capstone.surahealthapp.view.pertolonganpertama

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surahealthapp.data.lokal.PertolonganDatabase
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaEntity
import kotlinx.coroutines.launch

class PertolonganPertamaViewModel(
    private val ppDatabase: PertolonganDatabase
) : ViewModel() {
    val pps = ppDatabase.dao.getAllPertolonganPertama()

    fun insertPertolonganPertama(ppEntity: PertolonganPertamaEntity){
        viewModelScope.launch {
            ppDatabase.dao.insertPertolongan(ppEntity)
        }
    }
}