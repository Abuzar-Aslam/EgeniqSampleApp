package com.example.egeniqsampleapp.domain.model

/**
 * Represents a breed result in the domain layer of the application.
 *
 * @property id The ID of the breed.
 * @property name The name of the breed.
 * @property temperament The temperament of the breed.
 * @property image The image associated with the breed.
 */
data class BreedResult(
    val id: String,
    val name: String,
    val temperament: String,
    val image: ImageResult?
)
