package com.example.myuniversity.retrofit

import com.example.myuniversity.UniversityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search?country=Indonesia")
    fun getUniversity(): Call<UniversityResponse>

}