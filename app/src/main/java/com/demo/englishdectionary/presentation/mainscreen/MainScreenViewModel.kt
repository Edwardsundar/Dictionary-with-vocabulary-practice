package com.demo.englishdectionary.presentation.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.englishdectionary.common.CharlesKOgden
import com.demo.englishdectionary.common.Letter
import com.demo.englishdectionary.data.local.words800.SingleWordItem
import com.demo.englishdectionary.data.local.words800.Words800Dao
import com.demo.englishdectionary.data.local.words800.Words800Entity
import com.demo.englishdectionary.domain.repository.WordMeaningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: WordMeaningRepository,
    private val words800Dao: Words800Dao
):ViewModel() {
    var uploadProgress by mutableStateOf(0f)
    val state = MutableStateFlow<MainScreenState>(MainScreenState())

    init {
        viewModelScope.launch {
            initializeWords()
        }
        viewModelScope.launch {
            repository.getBookMarks().collectLatest { wordMeaningList->
                state.value = state.value.copy(
                    history = wordMeaningList
                )
            }
        }
    }

    private suspend fun initializeWords(wordsList:List<Pair<List<String> , Letter>> = CharlesKOgden.letters) {
        val length = CharlesKOgden.letters.size
        if (words800Dao.getWordCount() == 0) {
            state.value = state.value.copy(
                isUploading = true
            )
            wordsList.forEachIndexed { index, pair ->
                val listOfSingleWordItem  = mutableListOf<SingleWordItem>()
                pair.first.forEach { word->
                    listOfSingleWordItem.add(
                        SingleWordItem(word = word)
                    )
                }
                words800Dao.insert800Word(
                    Words800Entity(
                        wordsList = listOfSingleWordItem ,
                        id = pair.second.value
                    )
                )
                uploadProgress =  index.toFloat() / length
            }
        }
        state.value = state.value.copy(
                isUploading = false
            )
    }

    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.DeleteBookMark -> {
                viewModelScope.launch {
                    repository.deleteBookMark(event.word)
                }
            }
        }
    }

}