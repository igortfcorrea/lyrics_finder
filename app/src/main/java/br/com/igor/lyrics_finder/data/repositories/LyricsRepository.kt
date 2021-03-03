package br.com.igor.lyrics_finder.data.repositories

import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import kotlinx.coroutines.flow.Flow

interface LyricsRepository {
    suspend fun fetchLyrics(artist: String, song: String) : Flow<LyricsEntity>
}