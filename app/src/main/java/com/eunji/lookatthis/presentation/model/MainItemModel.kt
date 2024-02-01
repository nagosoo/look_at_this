package com.eunji.lookatthis.presentation.model

data class MainItemModel(
    val image: String,
    val description: String,
    val time: String,
    val isLiked: Boolean,
    val link: String,
)