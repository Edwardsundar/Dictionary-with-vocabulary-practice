package com.demo.englishdectionary.di

import android.content.Context
import androidx.room.Room
import com.demo.englishdectionary.common.Constants
import com.demo.englishdectionary.data.local.BookMarkDatabase
import com.demo.englishdectionary.data.local.BookMarkDao
import com.demo.englishdectionary.data.local.words800.Words800Dao
import com.demo.englishdectionary.data.remoat.DictionaryApi
import com.demo.englishdectionary.data.repository.WordMeaningRepositoryImp
import com.demo.englishdectionary.domain.repository.WordMeaningRepository
import com.demo.englishdectionary.domain.usecase.WordMeaningUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDictionaryApi():DictionaryApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBookMarkDatabase(@ApplicationContext context: Context): BookMarkDatabase{
        return Room.databaseBuilder(
            context,
            BookMarkDatabase::class.java,
            "book_mark_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideBookMarkDao(bookMarkDatabase: BookMarkDatabase): BookMarkDao {
        return bookMarkDatabase.bookMarkDao()
    }

    @Singleton
    @Provides
    fun provideWords800Dao(bookMarkDatabase: BookMarkDatabase): Words800Dao {
        return bookMarkDatabase.words800Dao()
    }

    @Provides
    @Singleton
    fun provideWordMeaningRepository(
        api : DictionaryApi,
        bookMarkDao : BookMarkDao ,
        words800Dao: Words800Dao
    ):WordMeaningRepository{
        return WordMeaningRepositoryImp(api , bookMarkDao , words800Dao )
    }

    @Provides
    @Singleton
    fun provideWordMeaningUseCase(
        wordMeaningRepository: WordMeaningRepository
    ):WordMeaningUseCase{
        return WordMeaningUseCase(wordMeaningRepository)
    }

}

