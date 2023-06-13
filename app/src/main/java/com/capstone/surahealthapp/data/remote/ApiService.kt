package com.capstone.surahealthapp.data.remote

import com.capstone.surahealthapp.data.response.RumahSakitResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/v1/predict/{longitude}/{latitude}")
    suspend fun getDataRumahSakitTerdekat(
        @Path("longitude") longitude: Double,
        @Path("latitude") latitude: Double
    ): List<RumahSakitResponse>
}