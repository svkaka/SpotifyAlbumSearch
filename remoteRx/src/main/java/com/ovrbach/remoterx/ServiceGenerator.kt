package com.ovrbach.remoterx

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit



const val ABSOLUTELY_NOT_CLIENT = "f8fb471dff7c4a7a95f361395b848fde"
//todo PASTE_YOUR_SECRET_HERE
const val DEFINITELY_NOT_SECRET = "PASTE_YOUR_SECRET_HERE"
object ServiceGenerator {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())

    private lateinit var retrofit: Retrofit


    fun <S> createService(
        serviceClass: Class<S>
    ): S {
        return hookInterceptor(serviceClass)
    }

    private fun <S> hookInterceptor(
        serviceClass: Class<S>
    ): S {
        val interceptor = AuthenticationInterceptor()

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor)

            builder.client(httpClient.build())
        }

        retrofit = builder.build()
        return retrofit.create(serviceClass)
    }
}
