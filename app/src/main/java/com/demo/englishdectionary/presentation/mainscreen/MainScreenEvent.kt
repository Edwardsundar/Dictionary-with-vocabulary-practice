package com.demo.englishdectionary.presentation.mainscreen

sealed class MainScreenEvent{
    data class DeleteBookMark(val word : String):MainScreenEvent()
}
