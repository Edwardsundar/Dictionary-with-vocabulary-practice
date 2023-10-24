package com.demo.englishdectionary.data.local.words800

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.demo.englishdectionary.common.Letter
import com.demo.englishdectionary.data.local.StringListConverter
import com.demo.englishdectionary.data.local.WordItemListConverter
import com.demo.englishdectionary.domain.module.Words800

@Entity
data class Words800Entity(
    @TypeConverters(WordItemListConverter::class)
    val wordsList : List<SingleWordItem>,
    @PrimaryKey(autoGenerate = false)
    val id: Char
)

data class SingleWordItem(
    val word : String,
    val isCompleted : Boolean = false
)


fun Words800Entity.toWords800():Words800{
    return Words800(
        wordsList = wordsList,
        letter = Letter.valueOf(id.toString())
    )
}
