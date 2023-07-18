package com.example.egeniqsampleapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.egeniqsampleapp.data.repository.BreedRepository
import com.example.egeniqsampleapp.domain.model.BreedResult
import com.example.egeniqsampleapp.domain.model.ImageResult
import com.example.egeniqsampleapp.presentation.model.BreedItem
import com.example.egeniqsampleapp.presentation.model.ImageItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case class for retrieving the list of breeds.
 *
 * @property breedRepository The repository for accessing the breeds data.
 */
class BreedUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {

    /**
     * Retrieves the list of breeds from the repository.
     *
     * @return A Flow of PagingData<BreedItem> representing the list of breeds.
     */
    fun getBreeds(): Flow<PagingData<BreedItem>> {
        return breedRepository.getBreeds()
            .map { pagingData ->
                pagingData.map { breedResult ->
                    breedResult.toBreedItem()
                }
            }
    }


    /**
     * Maps the [BreedResult] to [BreedItem].
     *
     * @return The [BreedItem] mapped from the [BreedResult].
     */
    private fun BreedResult.toBreedItem(): BreedItem {
        return BreedItem(
            id = id,
            name = name,
            temperament = temperament,
            image = image?.toImageItem() ?: ImageItem("", 0, 0, "")
        )
    }

    /**
     * Maps the [ImageResult] to [ImageItem].
     *
     * @return The [ImageItem] mapped from the [ImageResult].
     */
    private fun ImageResult.toImageItem(): ImageItem {
        return ImageItem(
            id = id,
            width = width,
            height = height,
            url = url
        )
    }
}
