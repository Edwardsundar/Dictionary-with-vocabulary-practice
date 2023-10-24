package com.demo.englishdectionary.presentation

import com.demo.englishdectionary.data.remoat.dto.Definition
import com.demo.englishdectionary.data.remoat.dto.Meaning
import com.demo.englishdectionary.domain.module.WordMeaning
import com.demo.englishdectionary.domain.module.Words800

data class UiState(
    val wordMeaning: WordMeaning? = null,
    val searchWord: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val noun: List<Meaning> = emptyList(),
    val verb: List<Meaning> = emptyList(),
    val exclamation: List<Meaning> = emptyList(),
    val interjection: List<Meaning> = emptyList(),
    val isLiked : Boolean = false
)