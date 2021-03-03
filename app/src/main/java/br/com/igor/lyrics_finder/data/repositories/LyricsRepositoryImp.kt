package br.com.igor.lyrics_finder.data.repositories

import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import br.com.igor.lyrics_finder.infra.http.ApiRoutes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class LyricsRepositoryImp(private val apiRoutes: ApiRoutes) : LyricsRepository {

    override suspend fun fetchLyrics(artist: String, song: String) : Flow<LyricsEntity> {
        return flow {
            try {
                val response = apiRoutes.fetchLyrics(artist, song)
                response?.let { emit(it.toEntity()) }
            } catch (e: HttpException) {
                throw Exception(e.message())
            }
        }
    }
}