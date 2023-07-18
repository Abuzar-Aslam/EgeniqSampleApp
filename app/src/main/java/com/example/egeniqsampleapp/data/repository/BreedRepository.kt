package com.example.egeniqsampleapp.data.repository

import androidx.paging.PagingData
import com.example.egeniqsampleapp.domain.model.BreedResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for retrieving breeds.
 */
interface BreedRepository {
    fun getBreeds(): Flow<PagingData<BreedResult>>
}
