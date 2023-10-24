package com.demo.englishdectionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.englishdectionary.data.local.words800.Words800Dao
import com.demo.englishdectionary.data.local.words800.Words800Entity

@Database(entities = [BookMarkEntity::class , Words800Entity::class], version = 2, exportSchema = false)
@TypeConverters(
    PhoneticListConverter::class,
    MeaningListConverter::class,
    StringListConverter::class,
    LicenseConverter::class,
    WordItemListConverter::class
)
abstract class BookMarkDatabase : RoomDatabase(){
    abstract fun bookMarkDao(): BookMarkDao
    abstract fun words800Dao(): Words800Dao
}