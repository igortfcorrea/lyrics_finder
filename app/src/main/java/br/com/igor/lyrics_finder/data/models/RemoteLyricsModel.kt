package br.com.igor.lyrics_finder.data.models

import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RemoteLyricsModel (
    @SerializedName("title")
    @Expose var title: String?,
    @SerializedName("lyrics")
    @Expose var lyrics: String?
)

fun RemoteLyricsModel.toEntity() : LyricsEntity {
    return LyricsEntity(title, lyrics)
}