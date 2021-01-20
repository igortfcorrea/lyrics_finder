package br.com.igor.lyrics_finder.domain.usecases

import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import br.com.igor.lyrics_finder.domain.repositories.LyricsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchLyricsUseCase(private val lyricsRepository: LyricsRepository) : FetchLyrics {

    override suspend fun fetchLyrics(artist: String, song: String): Flow<LyricsEntity> {
        return lyricsRepository.fetchLyrics(artist, song).map {
                    if (it.lyrics?.isNotEmpty() == true)
                        LyricsEntity("$song - $artist", it.lyrics)
                    else
                        throw Throwable("Song not founded")
                }
    }

}