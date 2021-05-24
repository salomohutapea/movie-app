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
import com.example.movieapp.core.utils.DataDummy
import com.example.movieapp.core.utils.EspressoIdlingResource
import com.example.movieapp.main.MainActivity
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    fun a_loadMoviesData() {
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(dummyMovieTv.first.movies?.size?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                it
            )
        })
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape())
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(dummyMovieTv.first.movies?.size?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                it
            )
        })
    }

    @Test
    fun b_loadDetailMovie() {
        onView(withId(R.id.rv_movietv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.textview_detail_movie_title)).check(
            ViewAssertions.matches(
                isDisplayed()
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

        onView(withId(R.id.action_favorite))
            .perform(ViewActions.click())

        onView(isRoot())
            .perform(ViewActions.pressBack())
    }

    @Test
    fun c_loadTvShowsData() {
        onView(withText("TV SHOWS")).perform(ViewActions.click())
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(dummyMovieTv.second.tvShow?.size?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                it
            )
        })
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape())
        onView(withId(R.id.rv_movietv)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movietv)).perform(dummyMovieTv.second.tvShow?.size?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                it
            )
        })
    }

    @Test
    fun d_loadDetailTvShow() {
        onView(withText("TV SHOWS")).perform(ViewActions.click())
        onView(withId(R.id.rv_movietv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.textview_detail_tv_title)).check(
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

        onView(withId(R.id.action_favorite))
            .perform(ViewActions.click())

        onView(isRoot())
            .perform(ViewActions.pressBack())

    }

    @Test
    fun e_loadFavoriteMovies() {
        onView(withId(R.id.switchFavorite))
            .perform(ViewActions.click())
        onView(withId(R.id.switchFavorite))
            .perform(ViewActions.click())
    }

    @Test
    fun f_loadFavoriteTvShows() {
        onView(withId(R.id.switchFavorite))
            .perform(ViewActions.click())
        onView(withText("TV SHOWS")).perform(ViewActions.click())
        onView(withId(R.id.switchFavorite))
            .perform(ViewActions.click())
    }
}