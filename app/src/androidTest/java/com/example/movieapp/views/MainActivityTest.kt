package com.example.movieapp.views

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.movieapp.R
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private val dummyMovieTitle = "Godzilla vs. Kong"
    private val dummyMovieDate = "2021-03-24"
    private val dummyTvShowTitle = "The Falcon and the Winter Soldier"
    private val dummyTvShowDate = "2021-03-19"
    private val dummySize = 20

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadMoviesData() {
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySize))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movietv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.detail_movie_lang_textview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.textview_detail_movie_title)).check(ViewAssertions.matches(withText(dummyMovieTitle)))
        onView(withId(R.id.detail_movie_vote_average_textview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.textview_detail_movie_date_content))
            .check(ViewAssertions.matches(withText(dummyMovieDate)))
    }

    @Test
    fun loadTvShowsData() {
        onView(withText("TV SHOWS")).perform(ViewActions.click())
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySize))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("TV SHOWS")).perform(ViewActions.click())
        onView(withId(R.id.rv_movietv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.detail_tv_lang_textview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.textview_detail_tv_title)).check(ViewAssertions.matches(withText(dummyTvShowTitle)))
        onView(withId(R.id.detail_tv_vote_average_textview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.textview_detail_tv_date_content))
            .check(ViewAssertions.matches(withText(dummyTvShowDate)))
    }
}