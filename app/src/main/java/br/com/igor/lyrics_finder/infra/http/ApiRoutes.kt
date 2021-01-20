package br.com.igor.lyrics_finder.infra.http

import br.com.igor.lyrics_finder.data.models.RemoteLyricsModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRoutes {
    @GET("{artist}/{title}")
    suspend fun fetchLyrics(@Path("artist") artist: String?, @Path("title") title: String?): RemoteLyricsModel?
}