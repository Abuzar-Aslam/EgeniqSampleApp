package com.example.egeniqsampleapp

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.PagingData
import com.example.egeniqsampleapp.domain.usecase.BreedUseCase
import com.example.egeniqsampleapp.presentation.MainViewModel
import com.example.egeniqsampleapp.presentation.model.BreedItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import kotlin.math.ceil
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class SampleViewModelTest {

    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var breedUseCase: BreedUseCase
    private lateinit var viewModel: MainViewModel
    private lateinit var testCoroutineScope: TestCoroutineScope

    @Before
    fun setUp() {
        val testCoroutineDispatcher = TestCoroutineDispatcher()
        testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
        breedUseCase = mockk()
        // Set the Main dispatcher to the test dispatcher
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `should return network_error for IOException`() {
        val pagingData = PagingData.from(mockResult)

        // Specify the behavior of the mocked breedUseCase.getBreeds() method
        coEvery { breedUseCase.getBreeds() } returns flow { emit(pagingData) }
        viewModel = MainViewModel(breedUseCase)
        val exception = IOException("Network error")
        val errorMessageResId = viewModel.handleException(exception)
        assertEquals(R.string.network_error, errorMessageResId)
    }

    @Test
    fun `should return http_error for HttpException`() {
        val pagingData = PagingData.from(mockResult)

        // Specify the behavior of the mocked breedUseCase.getBreeds() method
        coEvery { breedUseCase.getBreeds() } returns flow { emit(pagingData) }
        viewModel = MainViewModel(breedUseCase)
        val exception =
            HttpException(Response.error<Any>(404, ResponseBody.create(null, "Not Found")))
        val errorMessageResId = viewModel.handleException(exception)
        assertEquals(R.string.http_error, errorMessageResId)
    }

    @Test
    fun `should return unknown_error for any other exception`() {
        val pagingData = PagingData.from(mockResult)

        // Specify the behavior of the mocked breedUseCase.getBreeds() method
        coEvery { breedUseCase.getBreeds() } returns flow { emit(pagingData) }
        viewModel = MainViewModel(breedUseCase)
        val exception = IllegalArgumentException("Some unknown error")
        val errorMessageResId = viewModel.handleException(exception)
        assertEquals(R.string.unknown_error, errorMessageResId)
    }

    @Test
    fun `should load pages in batches`() = testCoroutineScope.runBlockingTest {
        // Given
        val pagingData = PagingData.from(mockResult)

        // Specify the behavior of the mocked breedUseCase.getBreeds() method
        coEvery { breedUseCase.getBreeds() } returns flow { emit(pagingData) }
        viewModel = MainViewModel(breedUseCase)

        viewModel.fetchBreeds()


        // When
        viewModel.pagingData.testPages {
            // Assert or perform any other test actions
            awaitPages().assertOrdered()
            viewModel.fetchBreeds()
            awaitPages().assertOrdered()
        }
    }

}

private fun Pagination<BreedItem>.assertOrdered() {
    // ceil because a page may contain les items than the page's size
    val pages = ceil(mockResult.size.toDouble() / PAGE_SIZE).toInt()

    repeat(pages) {
        assertEquals(mockResult.take(PAGE_SIZE * it), this.loadedAt(point = it))
    }
}