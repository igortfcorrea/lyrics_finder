package br.com.igor.lyrics_finder.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import br.com.igor.lyrics_finder.domain.usecases.FetchLyrics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val fetchLyrics: FetchLyrics) : ViewModel() {

    val lyrics = MutableLiveData<LyricsEntity>()
    val loading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun fetchLyrics(artist: String, song: String) {
        viewModelScope.launch (coroutineDispatcher) {
            this@MainViewModel.loading.postValue(true)
            fetchLyrics.fetchLyrics(artist, song)
                .catch {
                    this@MainViewModel.message.postValue(it.message)
                }
                .collect {
                    this@MainViewModel.lyrics.postValue(it)
                }
                .also {
                    this@MainViewModel.loading.postValue(false)
                }
        }
    }
}
