package com.capstone.surahealthapp.view.pertolonganpertama

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaEntity
import com.capstone.surahealthapp.data.repository.PertolonganPertamaRepository

class PertolonganPertamaViewModel(
    private val repository: PertolonganPertamaRepository
) : ViewModel() {

//    private val _searchResultState: MutableLiveData<ResultState<List<PertolonganPertamaEntity>>> =
//        MutableLiveData()
//    val searchResultState: LiveData<ResultState<List<PertolonganPertamaEntity>>>
//        get() = _searchResultState

    fun getAllPertolonganPertama(): LiveData<List<PertolonganPertamaEntity>> {
        return repository.getAllPertolonganPertama()
    }

    fun searchPertolonganPertama(query: String): LiveData<List<PertolonganPertamaEntity>> {
        return repository.searchPertolonganPertama(query).asLiveData()
    }
}