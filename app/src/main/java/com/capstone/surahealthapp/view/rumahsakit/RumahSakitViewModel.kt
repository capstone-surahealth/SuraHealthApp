package com.capstone.surahealthapp.view.rumahsakit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surahealthapp.data.model.RumahSakit
import com.capstone.surahealthapp.data.repository.RumahSakitRepository
import com.capstone.surahealthapp.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RumahSakitViewModel(
    private val rumahSakitRepository: RumahSakitRepository
): ViewModel() {
    private val _rmstate: MutableStateFlow<ResultState<List<RumahSakit>>> =
        MutableStateFlow(ResultState.Loading)
    val rmState: StateFlow<ResultState<List<RumahSakit>>>
        get() = _rmstate
    fun getAllRumahSakit() {
        viewModelScope.launch {
            rumahSakitRepository.getAllRumahSakit()
                .catch {
                    _rmstate.value = ResultState.Error(it.message.toString())
                }
                .collect { rs ->
                    _rmstate.value = ResultState.Success(rs)
                }
        }
    }
}