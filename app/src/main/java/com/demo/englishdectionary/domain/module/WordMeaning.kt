package com.demo.englishdectionary.domain.module

import com.demo.englishdectionary.data.remoat.dto.License
import com.demo.englishdectionary.data.remoat.dto.Meaning
import com.demo.englishdectionary.data.remoat.dto.Phonetic

data class WordMeaning(
    val license: License?,
    val meanings: List<Meaning>?,
    val phonetic: String?,
    val phonetics: List<Phonetic>?,
    val sourceUrls: List<String>?,
    val word: String?,
    val isCompleted : Boolean
)

//fun WordMeaning.toHistoryEntity(){
//    return HistoryEntity(
//        license = license,
//        meanings = meanings ,
//        phonetic = phonetic ,
//        phonetics = phonetics,
//        sourceUrls = sourceUrls ,
//        word = word
//    )
//}
