package com.demo.englishdectionary.domain.usecase

import com.demo.englishdectionary.common.Resource
import com.demo.englishdectionary.data.remoat.dto.toWordMeaning
import com.demo.englishdectionary.domain.module.WordMeaning
import com.demo.englishdectionary.domain.repository.WordMeaningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class WordMeaningUseCase @Inject constructor(
    private val repository: WordMeaningRepository
) {

    operator fun invoke(word : String):Flow<Resource<WordMeaning>> = flow {

        try {
            emit(Resource.Loading())
            val wordMeaning = repository.getRemoteMeaning(word).toWordMeaning()
            emit(Resource.Success(wordMeaning))
        }
        catch (http : HttpException){
            emit(Resource.Error(message = http.localizedMessage ?: "Unknown Error"))
        }
        catch (io : IOException){
            emit(Resource.Error(message = "Turn On Your InterNet Connections"))
        }
        catch (e : Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }

    }
}