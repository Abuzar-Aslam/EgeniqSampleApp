package com.example.egeniqsampleapp

import com.example.egeniqsampleapp.presentation.model.BreedItem
import com.example.egeniqsampleapp.presentation.model.ImageItem

val mockResult = listOf(
    BreedItem(
        id = "1",
        name = "Breed 1",
        temperament = "Cute, Fluffy",
        image = ImageItem(id = "11", width = 50, height = 50, url = "google.com/image")
    ),
    BreedItem(
        id = "2",
        name = "Breed 2",
        temperament = "Sweet, loyal, Quite",
        image = ImageItem(id = "12", width = 50, height = 50, url = "google.com/image")
    )
)