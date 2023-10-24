package com.demo.englishdectionary.presentation.mainscreen

import com.demo.englishdectionary.domain.module.WordMeaning

data class MainScreenState(
    val history: List<WordMeaning> = emptyList(),
    val isUploading : Boolean = false
)
