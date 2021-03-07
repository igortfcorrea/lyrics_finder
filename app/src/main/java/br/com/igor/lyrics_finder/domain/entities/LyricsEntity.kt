package br.com.igor.lyrics_finder.domain.entities

import br.com.igor.lyrics_finder.presentation.models.LyricsUIModel
import java.io.Serializable

class LyricsEntity(
    var title: String?,
    var lyrics: String?
) : Serializable

fun LyricsEntity.toUIModel() : LyricsUIModel {
    return LyricsUIModel(title, lyrics)
}