package com.demo.englishdectionary.presentation.practicsword

import com.demo.englishdectionary.domain.module.Words800

data class SelectedLetterState(
    val selectedLetterLists: Words800 = Words800(),
    val isLoading : Boolean = true
)
