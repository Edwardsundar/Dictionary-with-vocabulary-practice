package com.demo.englishdectionary.data.remoat

import com.demo.englishdectionary.data.remoat.dto.WordMeaningDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("{word}")
    suspend fun getWordMeaning(@Path("word")  word : String):WordMeaningDto

}