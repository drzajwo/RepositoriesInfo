package com.test.denis.repositoriesinfo.helpers

import com.test.denis.repositoriesinfo.model.RepositoryResponse
import com.test.denis.repositoriesinfo.network.BASE_URL
import com.test.denis.repositoriesinfo.network.RepositoryApi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Helper class for JUnit tests
 */
class JUnitSpecHelper {

    fun makeAPICall(query: String, page: Int, perPage: Int): RepositoryResponse? {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        val api = retrofit.create(RepositoryApi::class.java)
        return api.getRepositories(query, page, perPage).blockingSingle()
    }
}
