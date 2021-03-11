package br.com.igor.lyrics_finder.presentation.view

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import br.com.igor.lyrics_finder.R
import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
open class LyricsActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<LyricsActivity> = ActivityTestRule(LyricsActivity::class.java)

    @Test
    fun test_isActivityInView() {
        val activityScenario = ActivityScenario.launch(LyricsActivity::class.java)

        onView(withId(R.id.activityLyrics)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isBackButtonVisible() {
        mActivityRule.launchActivity(getLyricsEntityIntent())

        onView(withId(R.id.backLyricsImageView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isGettingExtrasCorrectly() {
        mActivityRule.launchActivity(getLyricsEntityIntent())

        onView(withId(R.id.songTitleTextView)).check(matches(withText("any_title")))
        onView(withId(R.id.lyricsTextView)).check(matches(withText("any_lyrics")))
    }

    private fun getLyricsEntityIntent() : Intent {
        val i = Intent()
        i.putExtra("lyrics", LyricsEntity("any_title", "any_lyrics"))
        return i
    }
}