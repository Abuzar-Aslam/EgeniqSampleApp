package com.example.egeniqsampleapp.data.model

/**
 * Data class representing the response model for an image.
 *
 * This class holds the properties that describe an image, including its ID, width, height, and URL.
 * The class is used to map the response from the data layer to a more structured model.
 *
 * @param id The unique identifier of the image.
 * @param width The width of the image.
 * @param height The height of the image.
 * @param url The URL of the image.
 */
data class ImageResponse(
    val id: String?,
    val width: Int?,
    val height: Int?,
    val url: String?
)
