package com.demo.englishdectionary.domain.repository

import com.demo.englishdectionary.data.remoat.dto.WordMeaningDto
import com.demo.englishdectionary.domain.module.WordMeaning
import com.demo.englishdectionary.domain.module.Words800
import kotlinx.coroutines.flow.Flow

interface WordMeaningRepository {

    suspend fun getRemoteMeaning(word : String) : WordMeaningDto

    suspend fun getLocalMeaning(word : String) : WordMeaning?

    suspend fun insertBookMark(wordMeaning: WordMeaning)

    suspend fun deleteBookMark(word: String)

    fun getBookMarks():Flow<List<WordMeaning>>

    fun getWordsStartingWith(letter : Char):Flow<Words800>

    suspend fun insert800Word(words800: Words800)
}