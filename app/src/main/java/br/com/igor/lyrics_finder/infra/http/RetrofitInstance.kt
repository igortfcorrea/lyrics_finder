package br.com.igor.lyrics_finder.infra.http

import br.com.igor.lyrics_finder.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitInstance {

    var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(getOkHttpClient())
        .connectTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private fun getOkHttpClient(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        return interceptor
    }
}