package com.example.egeniqsampleapp.presentation.model

/**
 * Data class representing an image item.
 *
 * @property id The ID of the image.
 * @property width The width of the image.
 * @property height The height of the image.
 * @property url The URL of the image.
 */
data class ImageItem(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
)
