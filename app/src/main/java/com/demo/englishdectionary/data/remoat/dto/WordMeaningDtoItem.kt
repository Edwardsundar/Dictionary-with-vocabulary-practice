package com.demo.englishdectionary.data.remoat.dto

data class WordMeaningDtoItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)