package com.example.movieapp.core.di

import com.example.movieapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoreModule {

    val networkModule = module {
        single {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    var request = chain.request()
                    val url = request.url.newBuilder().addQueryParameter(
                        "api_key", BuildConfig.MOVIE_TOKEN
                    ).build()
                    request = request.newBuilder().url(url).build()
                    chain.proceed(request)
                }
                .build()
        }
        single {
            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}