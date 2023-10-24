package com.demo.englishdectionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface BookMarkDao {

    @Query("SELECT * FROM bookmarkentity")
    fun getAllBookMarks(): Flow<List<BookMarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookMark(historyEntity: BookMarkEntity)

    @Query("DELETE FROM bookmarkentity WHERE word = :word")
    suspend fun deleteBookMark(word: String)

    @Query("SELECT * FROM bookmarkentity WHERE word = :word")
    suspend fun getBookMark(word: String) : BookMarkEntity?
}