package com.demo.englishdectionary.data.local.words800

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Words800Dao {
    @Query("SELECT * FROM words800entity WHERE id = :letter")
    fun getWordsStartingWith(letter: Char): Flow<Words800Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert800Word(words800Entity: Words800Entity)

    @Query("SELECT COUNT(*) FROM words800entity")
    suspend fun getWordCount(): Int

}