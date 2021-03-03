package br.com.igor.lyrics_finder.domain.usecases

import br.com.igor.lyrics_finder.data.repositories.LyricsRepository
import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class FetchLyricsUseCaseTest {

    @Mock
    lateinit var lyricsRepository: LyricsRepository

    lateinit var sut: FetchLyrics

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = FetchLyricsUseCase(lyricsRepository)
    }

    @Test
    fun shouldMapLyricsFromRepository() {
        runBlockingTest {
            val fakeLyricsEntity = LyricsEntity("any_title", "any_lyrics")
            val fakeFlow = flow {
                emit(fakeLyricsEntity)
            }

            Mockito.`when`(lyricsRepository.fetchLyrics(Mockito.anyString(), Mockito.anyString())).thenReturn(fakeFlow)

            val flow = sut.fetchLyrics("any_artist", "any_song")

            flow.collect {
                assertEquals(it.title, "any_song - any_artist")
                assertEquals(it.lyrics, fakeLyricsEntity.lyrics)
            }
        }
    }
}