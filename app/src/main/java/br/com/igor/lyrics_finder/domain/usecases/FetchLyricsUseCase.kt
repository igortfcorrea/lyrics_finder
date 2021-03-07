package br.com.igor.lyrics_finder.domain.usecases

import br.com.igor.lyrics_finder.data.repositories.LyricsRepository
import br.com.igor.lyrics_finder.domain.entities.toUIModel
import br.com.igor.lyrics_finder.presentation.models.LyricsUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchLyricsUseCase(private val lyricsRepository: LyricsRepository) : FetchLyrics {

    override suspend fun fetchLyrics(artist: String, song: String): Flow<LyricsUIModel> {
        return lyricsRepository.fetchLyrics(artist, song)
                    .map {
                        if (it.lyrics?.isNotEmpty() == true) {
                            it.title = "$song - $artist"
                            it.toUIModel()
                        } else {
                            throw Exception("Song not founded")
                        }
                    }
    }

}