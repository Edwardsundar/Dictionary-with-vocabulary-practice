package com.demo.englishdectionary.presentation

import com.demo.englishdectionary.domain.module.WordMeaning

sealed class UiEvent(){
    data class SearchWord(val word : String) : UiEvent()
    data class LocalSearch(val word : String) : UiEvent()
    data class DeleteWord(val word : String) : UiEvent()
    data class BookMark(val wordMeaning: WordMeaning) : UiEvent()
}
