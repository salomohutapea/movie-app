package com.example.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.example.movieapp.data.Repository
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.TvShow
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.data.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FragmentMovieTvViewModelTest {

    private lateinit var viewModel: FragmentMovieTvViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var movieObserver: Observer<Resource<PagedList<Movie>>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<PagedList<TvShow>>>

    @Mock
    private lateinit var movieFavoriteObserver: Observer<PagedList<Movie>>

    @Mock
    private lateinit var tvFavoriteObserver: Observer<PagedList<TvShow>>

    @Before
    fun setUp() {
        viewModel = FragmentMovieTvViewModel(repository)
    }

    @Test
    fun `a_getMovies should be success`() {
        val movies =
            PagedTestDataSources.snapshot(DataDummy.generateDummyMoviesAndTv().first.movies as List<Movie>)
        val expected = MutableLiveData<Resource<PagedList<Movie>>>()
        expected.value = Resource.success(movies)

        `when`(repository.getAllMovies()).thenReturn(expected)

        viewModel.getAllMovies().observeForever(movieObserver)
        verify(movieObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getAllMovies().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun `b_getMovies should be success but data is empty`() {
        val movies = PagedTestDataSources.snapshot<Movie>()
        val expected = MutableLiveData<Resource<PagedList<Movie>>>()
        expected.value = Resource.success(movies)

        `when`(repository.getAllMovies()).thenReturn(expected)

        viewModel.getAllMovies().observeForever(movieObserver)
        verify(movieObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getAllMovies().value?.data?.size
        assertTrue(
            "size of data should be 0, actual is $actualValueDataSize",
            actualValueDataSize == 0
        )
    }

    @Test
    fun `c_getMovies should be error`() {

        val expectedMessage = "Something happen dude!"
        val expected = MutableLiveData<Resource<PagedList<Movie>>>()
        expected.value = Resource.error(expectedMessage, null)

        `when`(repository.getAllMovies()).thenReturn(expected)

        viewModel.getAllMovies().observeForever(movieObserver)
        verify(movieObserver).onChanged(expected.value)

        val actualMessage = viewModel.getAllMovies().value?.message
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun `d_getTvShows should be success`() {
        val tvShows =
            PagedTestDataSources.snapshot(DataDummy.generateDummyMoviesAndTv().second.tvShow as List<TvShow>)
        val expected = MutableLiveData<Resource<PagedList<TvShow>>>()
        expected.value = Resource.success(tvShows)

        `when`(repository.getAllTvShows()).thenReturn(expected)

        viewModel.getAllTvShows().observeForever(tvObserver)
        verify(tvObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getAllTvShows().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun `e_getTvShows should be success but data is empty`() {
        val tvShow = PagedTestDataSources.snapshot<TvShow>()
        val expected = MutableLiveData<Resource<PagedList<TvShow>>>()
        expected.value = Resource.success(tvShow)

        `when`(repository.getAllTvShows()).thenReturn(expected)

        viewModel.getAllTvShows().observeForever(tvObserver)
        verify(tvObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getAllTvShows().value?.data?.size
        assertTrue(
            "size of data should be 0, actual is $actualValueDataSize",
            actualValueDataSize == 0
        )
    }

    @Test
    fun `f_getTvShows should be error`() {
        val expectedMessage = "Something happen dude!"
        val expected = MutableLiveData<Resource<PagedList<TvShow>>>()
        expected.value = Resource.error(expectedMessage, null)

        `when`(repository.getAllTvShows()).thenReturn(expected)

        viewModel.getAllTvShows().observeForever(tvObserver)
        verify(tvObserver).onChanged(expected.value)

        val actualMessage = viewModel.getAllTvShows().value?.message
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun `g_getFavoriteMovies should be success`() {
        val expected = MutableLiveData<PagedList<Movie>>()
        expected.value = PagedTestDataSources.snapshot(DataDummy.generateDummyMoviesAndTv().first.movies as List<Movie>)

        `when`(repository.getFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(movieFavoriteObserver)
        verify(movieFavoriteObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteMovies().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `h_getFavoriteMovies should be success but data is empty`() {
        val expected = MutableLiveData<PagedList<Movie>>()
        expected.value = PagedTestDataSources.snapshot()

        `when`(repository.getFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(movieFavoriteObserver)
        verify(movieFavoriteObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteMovies().value?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    @Test
    fun `i_getFavoriteTvShows should be success`() {
        val expected = MutableLiveData<PagedList<TvShow>>()
        expected.value = PagedTestDataSources.snapshot(DataDummy.generateDummyMoviesAndTv().second.tvShow as List<TvShow>)

        `when`(repository.getFavoriteTvShows()).thenReturn(expected)

        viewModel.getFavoriteTvShows().observeForever(tvFavoriteObserver)
        verify(tvFavoriteObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteTvShows().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `j_getFavoriteTvShows should be success but data is empty`() {
        val expected = MutableLiveData<PagedList<TvShow>>()
        expected.value = PagedTestDataSources.snapshot()

        `when`(repository.getFavoriteTvShows()).thenReturn(expected)

        viewModel.getFavoriteTvShows().observeForever(tvFavoriteObserver)
        verify(tvFavoriteObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteTvShows().value?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    class PagedTestDataSources<T> private constructor(private val items: List<T>) :
        PositionalDataSource<T>() {
        companion object {
            fun <T> snapshot(items: List<T> = listOf()): PagedList<T> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}