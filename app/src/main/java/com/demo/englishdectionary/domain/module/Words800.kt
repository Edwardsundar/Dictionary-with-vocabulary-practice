package com.demo.englishdectionary.domain.module

import com.demo.englishdectionary.common.Letter
import com.demo.englishdectionary.data.local.words800.SingleWordItem

data class Words800(
    val wordsList: List<SingleWordItem> = emptyList(),
    val letter: Letter = Letter.A
)
