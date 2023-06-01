package com.capstone.surahealthapp.view.pertolonganpertama

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surahealthapp.data.model.PertolonganPertama
import com.capstone.surahealthapp.data.repository.SuraRepository
import com.capstone.surahealthapp.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PertolonganPertamaViewModel(
    private val repository: SuraRepository
) : ViewModel() {
    private val _resultState: MutableStateFlow<ResultState<List<PertolonganPertama>>> =
        MutableStateFlow(ResultState.Loading)
    val resultStateFlow: StateFlow<ResultState<List<PertolonganPertama>>>
    get() = _resultState

    fun getAllPertolonganPertama(){
        viewModelScope.launch {
            repository.getAllPertolonganPertama()
                .catch {
                    _resultState.value = ResultState.Error(it.message.toString())
                }
                .collect{
                    _resultState.value = ResultState.Success(it)
                }
        }
    }
}