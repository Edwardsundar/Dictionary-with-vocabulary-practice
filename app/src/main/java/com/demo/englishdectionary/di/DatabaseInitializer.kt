package com.demo.englishdectionary.di

import com.demo.englishdectionary.common.CharlesKOgden
import com.demo.englishdectionary.common.Constants
import com.demo.englishdectionary.common.Letter
import com.demo.englishdectionary.data.local.words800.SingleWordItem
import com.demo.englishdectionary.data.local.words800.Words800Dao
import com.demo.englishdectionary.data.local.words800.Words800Entity
import javax.inject.Inject

class DatabaseInitializer @Inject constructor(private val words800Dao: Words800Dao) {

//    suspend fun initializeWord(wordsList:Pair< Char , String>){
//        words800Dao.insert800Word(
//            Words800Entity(
//                id = wordsList.first,
//                wordsList = listOf(
//                    SingleWordItem(
//                        word = wordsList.second
//                    )
//                )
//            )
//        )
//    }
//

}