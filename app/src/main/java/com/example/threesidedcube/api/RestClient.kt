package com.example.threesidedcube.api

import com.example.threesidedcube.utils.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    companion object {

        /**
         * implemented logging interceptor inorder to check logs of api calls by okhttp filter
         */
        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()


        /**
         * retrun the retrofit instance for calling webservices
         */
        fun getPokeMonWebService(): PokemonWebService {
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.getBaseHost())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(PokemonWebService::class.java)
        }
    }
}
