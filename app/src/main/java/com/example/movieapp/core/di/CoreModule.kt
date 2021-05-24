package com.example.movieapp.core.di

import androidx.room.Room
import com.example.movieapp.BuildConfig
import com.example.movieapp.core.data.Repository
import com.example.movieapp.core.data.source.local.AppDatabase
import com.example.movieapp.core.data.source.local.LocalDataSource
import com.example.movieapp.core.data.source.remote.ApiService
import com.example.movieapp.core.data.source.remote.RemoteDataSource
import com.example.movieapp.core.domain.repository.IMovieRepository
import com.example.movieapp.core.utils.AppExecutors
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<AppDatabase>().movieDao() }
    factory { get<AppDatabase>().tvShowDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movie.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

@DelicateCoroutinesApi
val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> { Repository(get(), get(), get()) }
}