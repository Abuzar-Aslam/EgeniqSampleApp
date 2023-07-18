package com.example.egeniqsampleapp.data.source

import com.example.egeniqsampleapp.data.model.BreedResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Data source interface for retrieving items.
 */
interface DataSource {

    /**
     * Retrieves a list of breeds from server.
     *
     * @return A list of [BreedResponse] representing the items.
     */
    @GET("breeds")
    suspend fun getBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<BreedResponse>
}