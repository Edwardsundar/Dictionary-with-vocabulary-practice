package com.demo.englishdectionary.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.englishdectionary.common.Constants
import com.demo.englishdectionary.common.Letter
import com.demo.englishdectionary.common.Resource
import com.demo.englishdectionary.data.local.words800.SingleWordItem
import com.demo.englishdectionary.data.local.words800.Words800Entity
import com.demo.englishdectionary.data.remoat.dto.Meaning
import com.demo.englishdectionary.domain.module.WordMeaning
import com.demo.englishdectionary.domain.module.Words800
import com.demo.englishdectionary.domain.repository.WordMeaningRepository
import com.demo.englishdectionary.domain.usecase.WordMeaningUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: WordMeaningRepository,
    private val wordMeaningUseCase: WordMeaningUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    val uiState = MutableStateFlow(UiState())

    init {
        savedStateHandle.get<String>(Constants.WORD)?.let { word->
            if (word.isNotBlank()){
                onEvent(UiEvent.LocalSearch(word))
            }
        }
    }

    fun onEvent(event: UiEvent){
        when(event){
            is UiEvent.BookMark -> {
                viewModelScope.launch {
                    repository.insertBookMark(event.wordMeaning)
                    uiState.value = uiState.value.copy(
                        isLiked = true
                    )
                }
            }
            is UiEvent.DeleteWord -> {
                viewModelScope.launch {
                    repository.deleteBookMark(event.word)
                }
            }
            is UiEvent.SearchWord -> {
                searchMeaning(event.word)
            }
            is UiEvent.LocalSearch -> {
                uiState.value = uiState.value.copy(
                    isLoading = true
                )
                viewModelScope.launch {
                    val wordMeaning = repository.getLocalMeaning(event.word)
                    if (wordMeaning == null){
                        onEvent(UiEvent.SearchWord(event.word))
                        return@launch
                    }
                    combinePartOfSpeech(wordMeaning , true)

                }
            }
        }
    }

    private fun searchMeaning(word : String){
        wordMeaningUseCase.invoke(word).onEach { result->
            when(result){
                is Resource.Error -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Unknown Error",
                        wordMeaning = null
                    )
                }
                is Resource.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true,
                        errorMessage = "",
                        isLiked = false
                    )
                }
                is Resource.Success -> {
                    combinePartOfSpeech(result.data , false)
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun combinePartOfSpeech(wordMeaning: WordMeaning? , isLiked : Boolean){
        val noun = mutableListOf<Meaning>()
        val verb = mutableListOf<Meaning>()
        val exclamation = mutableListOf<Meaning>()
        val interjection = mutableListOf<Meaning>()

        wordMeaning?.meanings?.forEach { meaning->
            when(meaning.partOfSpeech){
                "noun"-> {
                    noun.add(meaning)
                }
                "verb"->{
                    verb.add(meaning)
                }
                "interjection"->{
                    interjection.add(meaning)
                }
                else->{
                    exclamation.add(meaning)
                }
            }
        }
        uiState.value = uiState.value.copy(
            errorMessage = "",
            isLoading = false,
            noun = noun,
            verb = verb,
            exclamation = exclamation,
            wordMeaning = wordMeaning,
            interjection = interjection,
            isLiked = isLiked
        )
    }
}