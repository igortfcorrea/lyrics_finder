package br.com.igor.lyrics_finder.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import br.com.igor.lyrics_finder.domain.usecases.FetchLyrics
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var fetchLyrics: FetchLyrics

    @Mock
    lateinit var loadingObserver: Observer<Boolean>
    @Mock
    lateinit var messageObserver: Observer<String>
    @Mock
    lateinit var lyricsObserver: Observer<LyricsEntity>

    private val coroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var sut: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = MainViewModel(coroutineDispatcher, fetchLyrics)
        sut.loading.observeForever(loadingObserver)
        sut.lyrics.observeForever(lyricsObserver)
        sut.message.observeForever(messageObserver)
    }

    @After
    fun tearDown() {
        sut.loading.removeObserver(loadingObserver)
        sut.lyrics.removeObserver(lyricsObserver)
        sut.message.removeObserver(messageObserver)
    }

    @Test
    fun shouldUpdateLyricsValue() {
        coroutineDispatcher.runBlockingTest {
            val fakeResult = LyricsEntity("any_title", "any_lyrics")
            val fakeFlow = flow {
                emit(fakeResult)
            }
            `when`(fetchLyrics.fetchLyrics("any_artist", "any_song")).thenReturn(fakeFlow)

            sut.fetchLyrics("any_artist", "any_song")

            Mockito.verify(loadingObserver).onChanged(true)
            Mockito.verify(lyricsObserver).onChanged(fakeResult)
            Mockito.verify(loadingObserver).onChanged(false)
        }
    }

    @Test
    fun shouldCatchTheException() {
        coroutineDispatcher.runBlockingTest {
            val fakeMessage = "fakeMessage"
            val fakeFlow: Flow<LyricsEntity> = flow {
                throw Exception(fakeMessage)
            }
            `when`(fetchLyrics.fetchLyrics("any_artist", "any_song")).thenReturn(fakeFlow)

            sut.fetchLyrics("any_artist", "any_song")

            Mockito.verify(loadingObserver).onChanged(true)
            Mockito.verify(messageObserver).onChanged(fakeMessage)
            Mockito.verify(loadingObserver).onChanged(false)
        }
    }
}