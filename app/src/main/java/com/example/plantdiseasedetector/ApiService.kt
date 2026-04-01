package com.example.plantdiseasedetector

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// This model must match the JSON output of your FastAPI (Render)
data class PredictionResponse(
    val `class`: String,
    val confidence: Double
)

interface ApiService {
    @Multipart
    @POST("predict")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<PredictionResponse>
}