package com.capstone.surahealthapp.view.rumahsakit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.surahealthapp.data.repository.RumahSakitRepository
import com.capstone.surahealthapp.data.response.RumahSakitResponse
import com.capstone.surahealthapp.utils.ResultState

class RumahSakitViewModel(
    private val rumahSakitRepository2: RumahSakitRepository
): ViewModel() {
    fun getRumahSakitTerdekat(longitude: Double, latitude: Double): LiveData<ResultState<List<RumahSakitResponse>>> {
        return rumahSakitRepository2.getDataRumahSakit(longitude, latitude)
    }
}