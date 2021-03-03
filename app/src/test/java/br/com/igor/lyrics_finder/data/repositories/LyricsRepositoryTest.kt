package br.com.igor.lyrics_finder.data.repositories

import br.com.igor.lyrics_finder.data.models.RemoteLyricsModel
import br.com.igor.lyrics_finder.infra.http.ApiRoutes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LyricsRepositoryTest {

    @Mock
    lateinit var apiRoutes: ApiRoutes

    private lateinit var sut: LyricsRepositoryImp

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = LyricsRepositoryImp(apiRoutes)
    }

    @Test
    fun shouldEmitCorrectValue() {
        runBlockingTest {
            val fakeResult = RemoteLyricsModel("any_title", "any_lyrics")
            `when`(apiRoutes.fetchLyrics(Mockito.anyString(), Mockito.anyString())).thenReturn(fakeResult)

            val flow = sut.fetchLyrics("any_artist", "any_title")

            flow.collect {
                assertEquals(it.title, "any_title")
                assertEquals(it.lyrics, "any_lyrics")
            }
        }
    }
}