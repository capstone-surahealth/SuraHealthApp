package com.capstone.surahealthapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.surahealthapp.data.remote.ApiService
import com.capstone.surahealthapp.data.response.RumahSakitResponse
import com.capstone.surahealthapp.utils.ResultState

class RumahSakitRepository(private val apiService: ApiService) {
    fun getDataRumahSakit(longitude: Double, latitude: Double): LiveData<ResultState<List<RumahSakitResponse>>> = liveData{
        emit(ResultState.Loading)
        try {
            val rsLoc = apiService.getDataRumahSakitTerdekat(longitude, latitude)
            emit(ResultState.Success(rsLoc))
        } catch (e: Exception) {
            Log.d(TAG, "getRumahSakitTerdekat: ${e.message.toString()}")
            emit(ResultState.Error(e.message.toString()))
        }
    }


    companion object {

        private val TAG = RumahSakitRepository::class.java.simpleName


        @Volatile
        private var instance: RumahSakitRepository? = null

        fun getInstance(apiService: ApiService): RumahSakitRepository =
            instance ?: synchronized(this) {
                instance ?: RumahSakitRepository(apiService)
            }.also { instance = it }

    }
}