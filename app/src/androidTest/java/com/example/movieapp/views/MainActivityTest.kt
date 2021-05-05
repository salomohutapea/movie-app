package com.example.movieapp.views

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.movieapp.R
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private val dummyMovieTv = DataDummy.generateDummyMoviesAndTv()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadMoviesData() {
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(dummyMovieTv.first.movies?.size?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                it
            )
        })
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movietv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.detail_movie_lang_textview)).check(
            ViewAssertions.matches(
                withText(
                    dummyMovieTv.first.movies?.get(0)?.original_language?.capitalize()
                )
            )
        )
        onView(withId(R.id.textview_detail_movie_title)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.detail_movie_vote_average_textview)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.textview_detail_movie_date_content))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun loadTvShowsData() {
        onView(withText("TV SHOWS")).perform(ViewActions.click())
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(dummyMovieTv.second.onAir?.size?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                it
            )
        })
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("TV SHOWS")).perform(ViewActions.click())
        onView(withId(R.id.rv_movietv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.detail_tv_lang_textview)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.textview_detail_tv_title)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.detail_tv_vote_average_textview)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.textview_detail_tv_date_content))
            .check(ViewAssertions.matches(isDisplayed()))
    }
}