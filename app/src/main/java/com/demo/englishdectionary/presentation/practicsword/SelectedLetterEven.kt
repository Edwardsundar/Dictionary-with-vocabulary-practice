package com.demo.englishdectionary.presentation.practicsword

import com.demo.englishdectionary.presentation.UiEvent

sealed class SelectedLetterEven{
    data class WordIsChecked(val word:String): SelectedLetterEven()
    data class WordIsUnChecked(val word:String): SelectedLetterEven()
}
