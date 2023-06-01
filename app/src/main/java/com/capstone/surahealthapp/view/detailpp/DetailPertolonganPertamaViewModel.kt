package com.capstone.surahealthapp.view.detailpp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surahealthapp.data.model.PertolonganPertama
import com.capstone.surahealthapp.data.repository.SuraRepository
import com.capstone.surahealthapp.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailPertolonganPertamaViewModel(
    private val repository: SuraRepository
) : ViewModel(){
    private val _resultState: MutableStateFlow<ResultState<PertolonganPertama>> =
        MutableStateFlow(ResultState.Loading)
    val resultStateFlow: StateFlow<ResultState<PertolonganPertama>>
        get() = _resultState

    fun getPertolonganPertamaById(pertolonganId: Long){
        viewModelScope.launch{
            _resultState.value = ResultState.Loading
            try {
                val pertolongan = repository.getPertolonganPertamaById(pertolonganId)
                if (pertolongan != null){
                    _resultState.value = ResultState.Success(pertolongan)
                } else {
                    _resultState.value = ResultState.Error("Data not Found")
                }
            }catch (e : Exception){
                _resultState.value = ResultState.Error(e.message.toString())
            }
//            _resultState.value = ResultState.Loading
//            _resultState.value = ResultState.Success(repository.getPertolonganPertamaById(pertolonganId))
        }
    }
}