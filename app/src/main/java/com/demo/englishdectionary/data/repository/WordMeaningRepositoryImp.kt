package com.demo.englishdectionary.data.repository

import com.demo.englishdectionary.data.local.BookMarkDao
import com.demo.englishdectionary.data.local.BookMarkEntity
import com.demo.englishdectionary.data.local.toWordMeaning
import com.demo.englishdectionary.data.local.words800.Words800Dao
import com.demo.englishdectionary.data.local.words800.Words800Entity
import com.demo.englishdectionary.data.local.words800.toWords800
import com.demo.englishdectionary.data.remoat.DictionaryApi
import com.demo.englishdectionary.data.remoat.dto.WordMeaningDto
import com.demo.englishdectionary.domain.module.WordMeaning
import com.demo.englishdectionary.domain.module.Words800
import com.demo.englishdectionary.domain.repository.WordMeaningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordMeaningRepositoryImp @Inject constructor(
    private val api : DictionaryApi,
    private val bookMarkDao : BookMarkDao,
    private val words800Dao: Words800Dao
) :WordMeaningRepository {

    override suspend fun getLocalMeaning(word: String): WordMeaning? {
        return bookMarkDao.getBookMark(word)?.toWordMeaning()
    }

    override suspend fun getRemoteMeaning(word: String): WordMeaningDto {
        return api.getWordMeaning(word)
    }

    override suspend fun insertBookMark(wordMeaning: WordMeaning) {
        bookMarkDao.insertBookMark(
            BookMarkEntity(
                license = wordMeaning.license,
                meanings = wordMeaning.meanings,
                phonetic = wordMeaning.phonetic ,
                phonetics = wordMeaning.phonetics ,
                sourceUrls = wordMeaning.sourceUrls,
                word = wordMeaning.word,
                isCompleted = wordMeaning.isCompleted
            )
        )
    }

    override suspend fun deleteBookMark(word: String) {
        bookMarkDao.deleteBookMark(word)
    }

    override fun getBookMarks(): Flow<List<WordMeaning>> {
        val wordMeaningsFlow = bookMarkDao.getAllBookMarks().map { listOfBookMark->
            listOfBookMark.map { bookMarkEntity->
                bookMarkEntity.toWordMeaning()
            }
        }
        return wordMeaningsFlow
    }

    override fun getWordsStartingWith(letter: Char): Flow<Words800> {
        val words800Flow = words800Dao.getWordsStartingWith(letter).map { wordEntity->
            wordEntity.toWords800()
        }
        return words800Flow
    }

    override suspend fun insert800Word(words800: Words800) {
        words800Dao.insert800Word(
            Words800Entity(
                wordsList = words800.wordsList,
                id = words800.letter.value
            )
        )
    }
}