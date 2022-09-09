package com.diana.appcore.sources

import com.diana.lib.adapter.StateEventAdapterFactory
import com.diana.lib.adapter.StateEventResponse
import com.diana.appcore.data.response.UserResponses
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET(EndPoint.GET_USER)
    suspend fun getList(
        @Query("page") page: Int
    ): StateEventResponse<UserResponses>

    object EndPoint {
        const val GET_USER = "/api/users"
    }

    companion object {
        private const val BASE_URL = "https://reqres.in"

        private val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()

        private fun build(): WebServices {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(StateEventAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(WebServices::class.java)
        }

        fun inject() = module {
            single { build() }
        }
    }
}