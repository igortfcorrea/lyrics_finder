package br.com.igor.lyrics_finder.domain.usecases

import br.com.igor.lyrics_finder.presentation.models.LyricsUIModel
import kotlinx.coroutines.flow.Flow

interface FetchLyrics {
    suspend fun fetchLyrics(artist: String, song: String) : Flow<LyricsUIModel>
}