package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.example.core.usecase.GetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    private val charactersFactory = CharacterFactory()

    private val pagingDataCharacter = PagingData.from(
        listOf(
            charactersFactory.create(CharacterFactory.Hero.ThreeDMan),
            charactersFactory.create(CharacterFactory.Hero.ABomb)
        )
    )

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() = runTest {
        whenever(
            getCharactersUseCase.invoke(any())
        ).thenReturn(
            flowOf(
                pagingDataCharacter
            )
        )

        val result = charactersViewModel.charactersPagingData("")

        assertEquals(1, result.count())
    }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case return an exception`() {
        runTest {
            whenever(getCharactersUseCase.invoke(any()))
                .thenThrow(RuntimeException())

            charactersViewModel.charactersPagingData("")
        }
    }
}