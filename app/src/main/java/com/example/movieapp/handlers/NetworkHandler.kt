package com.example.movieapp.handlers

import android.content.Context
import com.example.movieapp.R
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkHandler(private val context: Context) {

    // set interceptor
    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                var request = chain.request()
                val url = request.url.newBuilder().addQueryParameter(
                    "api_key", context.getString(
                        R.string.tmdb_api_key
                    )
                ).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ServiceApiCall = getRetrofit().create(ServiceApiCall::class.java)
}

interface ServiceApiCall {
    @GET("movie/now_playing")
    fun getAllMovies(): Call<MovieEntity>

    @GET("tv/on_the_air")
    fun getAllTvShows(): Call<TvShowEntity>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<GenreEntity>

    @GET("genre/tv/list")
    suspend fun getTvGenres(): Response<GenreEntity>

}