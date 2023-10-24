package com.demo.englishdectionary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.demo.englishdectionary.data.remoat.dto.License
import com.demo.englishdectionary.data.remoat.dto.Meaning
import com.demo.englishdectionary.data.remoat.dto.Phonetic
import com.demo.englishdectionary.domain.module.WordMeaning

@Entity
data class BookMarkEntity(
    @TypeConverters(LicenseConverter::class)
    val license: License?,
    val phonetic: String?,
    @TypeConverters(PhoneticListConverter::class)
    val phonetics: List<Phonetic>?,
    @TypeConverters(MeaningListConverter::class)
    val meanings: List<Meaning>?,
    @TypeConverters(StringListConverter::class)
    val sourceUrls: List<String>?,
    val word: String?,
    val isCompleted : Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)

fun BookMarkEntity.toWordMeaning():WordMeaning{
    return WordMeaning(
        license = license,
        meanings = meanings,
        phonetic = phonetic,
        phonetics = phonetics,
        sourceUrls =sourceUrls ,
        word = word,
        isCompleted = isCompleted
    )
}
