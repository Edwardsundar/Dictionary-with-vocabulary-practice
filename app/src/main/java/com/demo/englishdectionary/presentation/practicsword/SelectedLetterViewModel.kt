package com.demo.englishdectionary.presentation.practicsword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.englishdectionary.common.Constants
import com.demo.englishdectionary.domain.repository.WordMeaningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedLetterViewModel @Inject constructor(
    private val repository: WordMeaningRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    val state = MutableStateFlow(SelectedLetterState())

    init {
        savedStateHandle.get<String>(Constants.LETTER)?.let { letter->
            viewModelScope.launch {
                repository.getWordsStartingWith(letter[0]).collectLatest {wordList->
                    state.value = SelectedLetterState(selectedLetterLists = wordList)
                }
                state.value = state.value.copy(isLoading = false)
            }
        }
    }

    fun onEvent(event: SelectedLetterEven){
        when(event){
            is SelectedLetterEven.WordIsChecked -> {

            }
            is SelectedLetterEven.WordIsUnChecked -> {

            }
        }
    }
}