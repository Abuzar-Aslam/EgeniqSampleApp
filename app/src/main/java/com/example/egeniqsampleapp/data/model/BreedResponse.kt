package com.example.egeniqsampleapp.data.model

/**
 * Data class representing the response model for a breed.
 *
 * This class holds the properties that describe a breed, including its ID, name, temperament, and image Object.
 * The class is used to map the response from the data layer to a more structured model in the domain layer.
 *
 * @param id The unique identifier of the breed.
 * @param name The name of the breed.
 * @param temperament The temperament of the breed.
 * @param image The image URL of the breed.
 */
data class BreedResponse(
    val id: String?,
    val name: String?,
    val temperament: String?,
    val image: ImageResponse?
)
