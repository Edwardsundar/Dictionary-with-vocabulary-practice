package com.demo.englishdectionary.common

sealed class Navigation(val route : String){
    object MainScreen:Navigation("main_screen")
    object OnlineSearch:Navigation("online_search")
    object PracticeScreen:Navigation("practice_screen")
    object SelectedLetterScreen:Navigation("selected_letter_screen")
}
