package br.com.igor.lyrics_finder.presentation.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import br.com.igor.lyrics_finder.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun test_isActivityInView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isLayoutVisibilities() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.songEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.searchTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.backImageView)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.progressMainActivity)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.searchImageButton)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun test_displaySearchButtonOnlyWhenArtistAndSongIsInserted() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.searchImageButton)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.artistEditText)).perform(typeText("Some Artist"))
        onView(withId(R.id.searchImageButton)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.artistEditText)).perform(clearText())
        onView(withId(R.id.songEditText)).perform(typeText("Some Song"))
        onView(withId(R.id.searchImageButton)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.artistEditText)).perform(typeText("Some Artist"))
        onView(withId(R.id.searchImageButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_showLoadingWhenSearchButtonClicked() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.progressMainActivity)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.songEditText)).perform(typeText("Some Song"))
        onView(withId(R.id.artistEditText)).perform(typeText("Some Artist"))
        onView(withId(R.id.searchImageButton)).perform(click())

        onView(withId(R.id.progressMainActivity)).check(matches(isDisplayed()))
    }

}