package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.marvelapp.factory.response.DataWrapperResponseFactory
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.nhaarman.mockitokotlin2.any
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersPageSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val characterFactory = CharacterFactory()

    private lateinit var charactersPageSource: CharactersPageSource

    @Before
    fun setUp() {
        charactersPageSource = CharactersPageSource(remoteDataSource, "")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return a success load result when load is called`() {
        runTest {
            // Arrange
            whenever(remoteDataSource.fetchCharacters(any()))
                .thenReturn(dataWrapperResponseFactory.create())

            // Act
            val result = charactersPageSource.load(
                PagingSource.LoadParams.Refresh(
                    null,
                    loadSize = 2,
                    false
                )
            )

            // Assert
            val expected = listOf(
                characterFactory.create(CharacterFactory.Hero.ThreeDMan),
                characterFactory.create(CharacterFactory.Hero.ABomb)
            )

            assertEquals(
                PagingSource.LoadResult.Page(
                    data = expected,
                    prevKey = null,
                    20
                ), result
            )
        }
    }

    @Test
    fun `should return an error load result when load is called`() {
        runTest {

            // Arrange
            val exception = RuntimeException()
            whenever(remoteDataSource.fetchCharacters(any()))
                .thenThrow(exception)

            // Act
            val result = charactersPageSource.load(
                PagingSource.LoadParams.Refresh(
                    null,
                    20,
                    placeholdersEnabled = false
                )
            )

            // Assert
            assertEquals(
                PagingSource.LoadResult.Error<Int, Character>(exception),
                result
            )
        }
    }
}