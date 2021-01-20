package br.com.igor.lyrics_finder.di

import br.com.igor.lyrics_finder.BuildConfig
import br.com.igor.lyrics_finder.infra.http.ApiRoutes
import br.com.igor.lyrics_finder.infra.http.RetrofitInstance
import br.com.igor.lyrics_finder.domain.usecases.FetchLyricsUseCase
import br.com.igor.lyrics_finder.domain.usecases.FetchLyrics
import br.com.igor.lyrics_finder.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single { Retrofit.Builder()
            .baseUrl(BuildConfig.LYRICS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitInstance().client)
            .build()
            .create(ApiRoutes::class.java) as ApiRoutes
    }

    single<br.com.igor.lyrics_finder.domain.repositories.LyricsRepository> { br.com.igor.lyrics_finder.data.repositories.LyricsRepository(get()) }

    single<FetchLyrics> { FetchLyricsUseCase(get()) }

    viewModel { MainViewModel(get()) }
}