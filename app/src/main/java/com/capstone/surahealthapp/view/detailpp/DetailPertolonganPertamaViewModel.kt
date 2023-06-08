package com.capstone.surahealthapp.view.detailpp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaEntity
import com.capstone.surahealthapp.data.repository.PertolonganPertamaRepository
import com.capstone.surahealthapp.utils.ResultState

class DetailPertolonganPertamaViewModel(
    private val repository: PertolonganPertamaRepository
) : ViewModel(){

    private val _resultState: MutableLiveData<ResultState<PertolonganPertamaEntity>> =
        MutableLiveData(ResultState.Loading)
    val resultStateLiveData: LiveData<ResultState<PertolonganPertamaEntity>>
        get() = _resultState

    fun getPertolonganPertamaById(pertolonganId: Long, owner: LifecycleOwner) {
        repository.getPertolonganPertamaById(pertolonganId).observe(owner) { pertolongan ->
            if (pertolongan != null) {
                _resultState.value = ResultState.Success(pertolongan)
            } else {
                _resultState.value = ResultState.Error("Data not Found")
            }
        }
    }
}