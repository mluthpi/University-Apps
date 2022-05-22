package com.example.myuniversity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.viewbinding.BuildConfig.BUILD_TYPE
import com.example.myuniversity.local.room.UnivDao
import com.example.myuniversity.local.room.UnivDatabase
import com.example.myuniversity.local.room.entity.UnivEntity
import com.example.myuniversity.retrofit.ApiService
import com.example.myuniversity.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnivRepository private constructor(
    private val apiService: ApiService,
    private val univDao: UnivDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<UnivEntity>>>()


    fun getHeadlineUniv():LiveData<Result<List<UnivEntity>>> {
        result.value = Result.Loading
        val client = apiService.getUniversity()
        client.enqueue(object: Callback<UniversityResponse> {
            override fun onResponse(
                call: Call<UniversityResponse>,
                response: Response<UniversityResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.universityResponse
                    val univList = ArrayList<UnivEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            val isBookmarked = article?.name?.let { univDao.isUnivBookmarked(it) }
                            val univ = article?.name?.let {
                                UnivEntity(
//                                    it,
//                                    article?.domains,
//                                    article?.webPages?.toString(),
//                                    article?.stateProvince?.toString(),
//                                    isBookmarked = tr
                                title = it,
                                    urlToImage = article.webPages.toString(),
                                    url = article.domains.toString(),
                                    isBookmarked = false
                                )
                            }
                            if (univ != null) {
                                univList.add(univ)
                            }
                        }
                    }
                    univDao.deleteAll()
                    univDao.insertUniv(univList)
                }
            }
            override fun onFailure(call: Call<UniversityResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localeData = univDao.getUniv()
        result.addSource(localeData) { univData: List<UnivEntity> ->
            result.value = Result.Success(univData)
        }
        return result
    }
    fun getBookmarkedUniv(): LiveData<List<UnivEntity>> {
        return univDao.getBookmarkedUniv()
    }

    fun setBookmarkedUniv(univ: UnivEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
                univ.isBookmarked = bookmarkState
            univDao.updateUniv(univ)
        }
    }

    companion object {
        @Volatile
        private var instance: UnivRepository? = null
        fun getInstance(
            apiService: ApiService,
            database: UnivDatabase,
            univDao: UnivDao,
            appExecutors: AppExecutors
        ):UnivRepository =
            instance ?: synchronized(this) {
                instance ?: UnivRepository(apiService, univDao, appExecutors)
            }.also { instance = it }

    }
}