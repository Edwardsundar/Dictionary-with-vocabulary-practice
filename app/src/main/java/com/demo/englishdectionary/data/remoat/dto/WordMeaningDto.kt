package com.demo.englishdectionary.data.remoat.dto

import com.demo.englishdectionary.domain.module.WordMeaning

class WordMeaningDto : ArrayList<WordMeaningDtoItem>()

fun WordMeaningDto.toWordMeaning():WordMeaning{
    val wordMeaningDtoItem = this[0]
    return WordMeaning(
        license = wordMeaningDtoItem.license,
        meanings = wordMeaningDtoItem.meanings,
        phonetic = wordMeaningDtoItem.phonetic,
        phonetics = wordMeaningDtoItem.phonetics,
        sourceUrls = wordMeaningDtoItem.sourceUrls,
        word = wordMeaningDtoItem.word,
        isCompleted = false
    )
}