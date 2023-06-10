package com.capstone.surahealthapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surahealthapp.data.model.RumahSakit
import com.capstone.surahealthapp.data.repository.RumahSakitRepository
import com.capstone.surahealthapp.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapsViewModel(private val rumahSakitRepository: RumahSakitRepository): ViewModel() {
    private val _rmstate: MutableStateFlow<ResultState<RumahSakit>> =
        MutableStateFlow(ResultState.Loading)
    val rmState: StateFlow<ResultState<RumahSakit>>
        get() = _rmstate
    fun getRumahSakitId(id : Long) {
        viewModelScope.launch {
            _rmstate.value = ResultState.Loading
            _rmstate.value = ResultState.Success(rumahSakitRepository.getRumahSakitById(id))
        }
    }
}