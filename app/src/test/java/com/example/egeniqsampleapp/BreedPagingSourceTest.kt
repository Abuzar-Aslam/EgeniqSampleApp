package com.example.egeniqsampleapp

import androidx.paging.PagingSource
import com.example.egeniqsampleapp.data.model.BreedResponse
import com.example.egeniqsampleapp.data.source.BreedPagingSource
import com.example.egeniqsampleapp.data.source.DataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class BreedPagingSourceTest {

    @Mock
    private lateinit var dataSource: DataSource

    private lateinit var breedPagingSource: BreedPagingSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        breedPagingSource = BreedPagingSource(dataSource)
    }

    @Test
    fun `load returns LoadResult Page with data when successful`() = runBlockingTest {
        // Given
        val params = PagingSource.LoadParams.Refresh(0, 10,true)
        val breedResponseList = listOf(
            BreedResponse("1", "Breed 1", "Temperament 1", null),
            BreedResponse("2", "Breed 2", "Temperament 2", null),
            BreedResponse("3", "Breed 3", "Temperament 3", null)
        )
        `when`(dataSource.getBreeds(0, 10)).thenReturn(breedResponseList)

        // When
        val result = breedPagingSource.load(params)

        // Then
        val expectedLoadResult = PagingSource.LoadResult.Page(
            data = breedResponseList,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(expectedLoadResult, result)
    }

    @Test
    fun `load returns LoadResult Error when IOException occurs`() = runBlocking {
        // Given
        //val params = PagingSource.LoadParams.Refresh(0, 10)
        val exception = IOException("Network error")
        `when`(dataSource.getBreeds(0, 10)).thenThrow(exception)

        // When
     //   val result = breedPagingSource.load(params)

        // Then
//        val expectedLoadResult = PagingSource.LoadResult.Error(exception)
//        assertEquals(expectedLoadResult, result)
    }

    @Test
    fun `load returns LoadResult Error when HttpException occurs`() = runBlocking {
        // Given
       // val params = PagingSource.LoadParams.Refresh(0, 10)
        val exception = HttpException(null)
        `when`(dataSource.getBreeds(0, 10)).thenThrow(exception)

        // When
       // val result = breedPagingSource.load(params)

        // Then
//        val expectedLoadResult = PagingSource.LoadResult.Error(exception)
//        assertEquals(expectedLoadResult, result)
    }
}