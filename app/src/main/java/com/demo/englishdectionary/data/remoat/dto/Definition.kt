package com.demo.englishdectionary.data.remoat.dto

data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val example: String,
    val synonyms: List<String>
)