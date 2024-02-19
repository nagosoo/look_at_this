package com.eunji.lookatthis.domain

import kotlinx.coroutines.flow.MutableStateFlow

object GlobalNetworkResponseCodeFlow {
    private val _isExpiredToken = MutableStateFlow(false)
    val isExpiredToken = _isExpiredToken

    fun setGlobalResponseCodeFlow(value: Boolean) {
        _isExpiredToken.value = value
    }
}