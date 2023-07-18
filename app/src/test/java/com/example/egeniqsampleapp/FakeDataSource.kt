package com.example.egeniqsampleapp

import com.example.egeniqsampleapp.data.model.BreedResponse
import com.example.egeniqsampleapp.data.model.ImageResponse
import com.example.egeniqsampleapp.data.source.DataSource

class FakeDataSource : DataSource {
    override suspend fun getBreeds(page: Int, limit: Int): List<BreedResponse> {
        return listOf(
            BreedResponse(
                id = "1",
                name = "Breed 1",
                temperament = "Cute, Fluffy",
                image = ImageResponse(id = "11", width = 50, height = 50, url = "google.com/image")
            ),
            BreedResponse(
                id = "2",
                name = "Breed 2",
                temperament = "Sweet, loyal, Quite",
                image = ImageResponse(id = "12", width = 50, height = 50, url = "google.com/image")
            )
        )
    }
}