package com.example.piyo.presentation.home.beranda

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BerandaViewModel : ViewModel() {

    private val _dummy = MutableStateFlow(true)
    val dummy = _dummy.asStateFlow()
}
