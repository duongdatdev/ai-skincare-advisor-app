package com.proteam.aiskincareadvisor.data.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proteam.aiskincareadvisor.data.repository.SkinAnalysisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class SkinAnalysisViewModel(context: Context) : ViewModel() {
    private val repository = SkinAnalysisRepository(context)

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _analysisResult = MutableStateFlow<String?>(null)
    val analysisResult: StateFlow<String?> = _analysisResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun setImage(uri: Uri) {
        _imageUri.value = uri
        _analysisResult.value = null
        _errorMessage.value = null
    }

    fun analyzeSkin() {
        val currentUri = _imageUri.value ?: run {
            _errorMessage.value = "No image selected"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            repository.analyzeSkinImage(currentUri).fold(
                onSuccess = { result ->
                    _analysisResult.value = result
                },
                onFailure = { error ->
                    _errorMessage.value = "Analysis failed: ${error.localizedMessage}"
                }
            )
            _isLoading.value = false
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SkinAnalysisViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SkinAnalysisViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}