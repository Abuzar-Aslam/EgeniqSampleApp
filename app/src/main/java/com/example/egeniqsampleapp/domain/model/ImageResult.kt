package com.example.egeniqsampleapp.domain.model

/**
 * Represents an image result in the domain layer of the application.
 *
 * @property id The ID of the image.
 * @property width The width of the image.
 * @property height The height of the image.
 * @property url The URL of the image.
 */
data class ImageResult(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
)
