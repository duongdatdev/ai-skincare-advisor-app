package com.proteam.aiskincareadvisor.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proteam.aiskincareadvisor.data.firestore.SkinAnalysisStorage
import com.proteam.aiskincareadvisor.data.model.SkinAnalysisResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SkinHistoryViewModel : ViewModel() {
    private val repository = SkinAnalysisStorage()

    private val _latestResult = MutableStateFlow<SkinAnalysisResult?>(null)
    val latestResult: StateFlow<SkinAnalysisResult?> = _latestResult

    init {
        loadLatest()
    }

    fun loadLatest() {
        viewModelScope.launch {
            _latestResult.value = repository.getLatestAnalysisResult()
        }
    }
}
