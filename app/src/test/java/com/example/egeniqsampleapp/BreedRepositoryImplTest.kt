package com.example.egeniqsampleapp

import androidx.paging.PagingData
import com.example.egeniqsampleapp.data.model.BreedResponse
import com.example.egeniqsampleapp.data.repository.BreedRepositoryImpl
import com.example.egeniqsampleapp.data.source.BreedPagingSource
import com.example.egeniqsampleapp.domain.model.BreedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class BreedRepositoryImplTest {

    @Mock
    private lateinit var breedPagingSource: BreedPagingSource

    private lateinit var breedRepositoryImpl: BreedRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        breedRepositoryImpl = BreedRepositoryImpl(breedPagingSource)
    }

    @Test
    fun `getBreeds returns Flow of PagingData of BreedResult`() = runBlocking {
        // Given
        val breedResponseList = listOf(
            BreedResponse("1", "Breed 1", "Temperament 1", null),
            BreedResponse("2", "Breed 2", "Temperament 2", null),
            BreedResponse("3", "Breed 3", "Temperament 3", null)
        )
//        val expectedBreedResultList = breedResponseList.map { it.toBreedResult() }
//        `when`(breedPagingSource.load(0, 10)).thenReturn(breedResponseList)
//
//        // When
//        val breedsFlow: Flow<PagingData<BreedResult>> = breedRepositoryImpl.getBreeds()
//        val breedsList = breedsFlow.toList().flatten()
//
//        // Then
//        assertEquals(expectedBreedResultList, breedsList)
    }
}

