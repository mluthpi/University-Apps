package com.example.myuniversity.di

import android.content.Context
import com.example.myuniversity.UnivRepository
import com.example.myuniversity.local.room.UnivDatabase
import com.example.myuniversity.retrofit.ApiConfig
import com.example.myuniversity.utils.AppExecutors

object Injection {
    fun providerRepository(context: Context): UnivRepository {
        val apiService = ApiConfig.getApiService()
        val database = UnivDatabase.getInstance(context)
        val dao = database.univDao()
        val appExecutors = AppExecutors()
        return UnivRepository.getInstance(apiService, database, dao, appExecutors)
    }
}