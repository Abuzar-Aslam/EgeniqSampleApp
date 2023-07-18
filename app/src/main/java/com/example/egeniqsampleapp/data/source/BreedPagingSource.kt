package com.example.egeniqsampleapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.egeniqsampleapp.data.model.BreedResponse
import com.example.egeniqsampleapp.utils.Constants.DEFAULT_PAGE
import com.example.egeniqsampleapp.utils.Constants.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Paging source for retrieving breeds.
 *
 * This class implements the `PagingSource` interface provided by the Paging 3 library. It is responsible for loading
 * paginated data from the data source and providing it to the Paging library.
 *
 * @param dataSource The data source for retrieving breeds.
 */
class BreedPagingSource @Inject constructor(private val dataSource: DataSource) :
    PagingSource<Int, BreedResponse>() {

    /**
     * Gets the refresh key for subsequent refresh calls to `PagingSource.load` after the initial load.
     *
     * @param state The current paging state.
     * @return The refresh key to be used for loading data.
     */
    override fun getRefreshKey(state: PagingState<Int, BreedResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * Loads a page of data from the data source.
     *
     * @param params The parameters for loading the data, including the key and the load size.
     * @return A `LoadResult` object containing the loaded data and the next/previous keys.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreedResponse> {
        val page = params.key ?: DEFAULT_PAGE

        return try {
            val response = dataSource.getBreeds(page, params.loadSize)

            val nextKey = if (response.isEmpty()) {
                null
            } else {
                page + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            val prevKey = if (page == DEFAULT_PAGE) null else page - 1

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
