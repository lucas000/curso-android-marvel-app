package com.example.marvelapp.presentation.sort

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.usecase.GetCharactersSortingUseCase
import com.example.core.usecase.SaveCharactersSortingUseCase
import com.example.core.usecase.base.AppCoroutinesDispatchers
import javax.inject.Inject

class SortViewModel @Inject constructor(
    private val getCharactersSortingUseCase: GetCharactersSortingUseCase,
    private val saveCharactersSortingUseCase: SaveCharactersSortingUseCase,
    private val coroutinesDispatchers: AppCoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    init {
        action.value = Action.GetStoredSorting
    }

    fun applySorting(orderBy: String, order: String) {
        action.value = Action.ApplySorting(orderBy, order)
    }

    sealed class UiState {
        data class SortingResult(val storedSorting: Pair<String, String>) : UiState()
        sealed class ApplyState : UiState() {
            object Loading : ApplyState()
            object Success : ApplyState()
            object Error : ApplyState()
        }
    }

    sealed class Action {
        object GetStoredSorting : Action()
        data class ApplySorting(
            val orderBy: String,
            val order: String
        ) : Action()
    }
}