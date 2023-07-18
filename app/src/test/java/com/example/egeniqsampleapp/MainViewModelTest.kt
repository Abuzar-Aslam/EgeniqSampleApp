package com.example.egeniqsampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.DifferCallback
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import com.example.egeniqsampleapp.data.repository.BreedRepository
import com.example.egeniqsampleapp.data.repository.BreedRepositoryImpl
import com.example.egeniqsampleapp.data.source.BreedPagingSource
import com.example.egeniqsampleapp.domain.model.BreedResult
import com.example.egeniqsampleapp.domain.model.ImageResult
import com.example.egeniqsampleapp.domain.usecase.BreedUseCase
import com.example.egeniqsampleapp.presentation.MainViewModel
import com.example.egeniqsampleapp.presentation.model.BreedItem
import com.example.egeniqsampleapp.presentation.model.ImageItem
import com.example.egeniqsampleapp.presentation.state.UiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainViewModelTest {

//    private lateinit var viewModel: MainViewModel
//    private lateinit var breedUseCase: BreedUseCase
//
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Before
//    fun setUp() {
//        breedUseCase = mockk()
//        Dispatchers.setMain(testDispatcher)
//        viewModel = MainViewModel(breedUseCase)
//    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var breedPagingSource: BreedPagingSource

    lateinit var breedRepository: BreedRepository

    lateinit var BreedUseCase: BreedUseCase


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    //@Mock
    private lateinit var breedUseCase: BreedUseCase

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
//        breedPagingSource = BreedPagingSource(FakeDataSource())
//        breedRepository = BreedRepositoryImpl(breedPagingSource)
//        breedUseCase = BreedUseCase(breedRepository)
        breedUseCase = mockk()
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(breedUseCase)
    }

    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `init should fetch breeds and update pagingData when successful`() = runBlockingTest {
        val breedListResult = listOf(
            BreedItem(
                id = "1",
                name = "Breed 1",
                temperament = "",
                image = ImageItem(id = "", width = 0, height = 0, url = "")
            ),
            BreedItem(
                id = "2",
                name = "Breed 2",
                temperament = "",
                image = ImageItem(id = "", width = 0, height = 0, url = "")
            )
        )
        val pagingData = PagingData.from(breedListResult)

        //coEvery { breedUseCase.getBreeds() } returns flow { emit(pagingData) }

        viewModel.fetchBreeds()


        val emittedPagingData = viewModel.pagingData.take(1).toList().first()

        val result = emittedPagingData.collectDataForTest()
        val result2 = pagingData.collectDataForTest()
        assertEquals(result2, result)
    }

    @Test
    fun myRepositoryTest() = runTest {
        // Given a repository that combines values from two data sources:
        //val repository = MyRepository(fakeSource1, fakeSource2)

        // When the repository emits a value
        val firstItem = breedUseCase.getBreeds().first() // Returns the first item in the flow

        // Then check it's the expected item
        assertEquals("ITEM_1", firstItem)
    }


    suspend fun <T> Flow<T>.collectData(): List<T> {
        val list = mutableListOf<T>()
        collect { value ->
            list.add(value)
        }
        return list
    }


}

private suspend fun <T : Any> PagingData<T>.collectDataForTest(): List<T> {
    val dcb = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }
    val items = mutableListOf<T>()
    val dif = object : PagingDataDiffer<T>(dcb, TestCoroutineDispatcher()) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit
        ): Int? {
            for (idx in 0 until newList.size)
                items.add(newList.getFromStorage(idx))
            onListPresentable()
            return null
        }
    }
    dif.collectFrom(this)
    return items
}