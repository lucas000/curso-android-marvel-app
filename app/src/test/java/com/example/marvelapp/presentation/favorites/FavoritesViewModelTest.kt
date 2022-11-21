package com.example.marvelapp.presentation.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.core.usecase.GetFavoritesUseCase
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Mock
    private lateinit var favoritesUiStateObserver: Observer<FavoritesViewModel.UiState>

    private val characters = listOf(
        CharacterFactory().create(CharacterFactory.Hero.ThreeDMan),
        CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    )

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setU() {
        viewModel = FavoritesViewModel(
            getFavoritesUseCase,
            mainCoroutineRule.testDispatcherProvider
        ).apply {
            state.observeForever(favoritesUiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with ShowFavorites from UiState when favorites ShowFavorites`() {
        runTest {
            // Arrange
            whenever(getFavoritesUseCase.invoke())
                .thenReturn(
                    flowOf(characters)
                )
            // Act

            viewModel.getAll()

            // Assert
            verify(favoritesUiStateObserver).onChanged(isA<FavoritesViewModel.UiState.ShowFavorites>())
        }
    }

    @Test
    fun `should notify uiState with ShowEmpty from UiState when get favorites returns empty list`() {
        runTest {
            // Arrange
            whenever(getFavoritesUseCase.invoke())
                .thenReturn(
                    flowOf(listOf())
                )
            // Act

            viewModel.getAll()

            // Assert
            verify(favoritesUiStateObserver).onChanged(isA<FavoritesViewModel.UiState.ShowEmpty>())
        }
    }
}