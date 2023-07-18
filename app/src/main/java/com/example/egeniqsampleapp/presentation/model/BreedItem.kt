package com.example.egeniqsampleapp.presentation.model

/**
 * Data class representing a breed item.
 *
 * @property id The ID of the breed.
 * @property name The name of the breed.
 * @property temperament The temperament of the breed.
 * @property image The [ImageItem] associated with the breed.
 */
data class BreedItem(
    val id: String,
    val name: String,
    val temperament: String,
    val image: ImageItem?
)
