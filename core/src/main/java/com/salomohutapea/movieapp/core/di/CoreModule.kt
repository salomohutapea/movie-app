package com.salomohutapea.movieapp.core.di

import androidx.room.Room
import com.salomohutapea.movieapp.core.BuildConfig
import com.salomohutapea.movieapp.core.data.Repository
import com.salomohutapea.movieapp.core.data.source.local.AppDatabase
import com.salomohutapea.movieapp.core.data.source.local.LocalDataSource
import com.salomohutapea.movieapp.core.data.source.remote.ApiService
import com.salomohutapea.movieapp.core.data.source.remote.RemoteDataSource
import com.salomohutapea.movieapp.core.domain.repository.IMovieRepository
import com.salomohutapea.movieapp.core.utils.AppExecutors
import kotlinx.coroutines.DelicateCoroutinesApi
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movie.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .build()
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
            .certificatePinner(certificatePinner)
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
    single<IMovieRepository> {
        Repository(get(),
            get(),
            get())
    }
}