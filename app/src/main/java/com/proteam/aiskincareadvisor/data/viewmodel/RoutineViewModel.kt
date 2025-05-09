package com.proteam.aiskincareadvisor.data.viewmodel

import androidx.lifecycle.ViewModel
import com.proteam.aiskincareadvisor.data.model.RoutineStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RoutineViewModel : ViewModel() {
    private val _routineSteps = MutableStateFlow<List<RoutineStep>>(emptyList())
    val routineSteps: StateFlow<List<RoutineStep>> = _routineSteps

    fun loadFromAnalysis(recommendations: List<String>) {
        _routineSteps.value = recommendations.map { RoutineStep(it) }
    }

    fun toggleStep(index: Int) {
        _routineSteps.value = _routineSteps.value.toMutableList().also {
            it[index] = it[index].copy(isDone = !it[index].isDone)
        }
    }

    fun updateStep(index: Int, newText: String) {
        _routineSteps.value = _routineSteps.value.toMutableList().also {
            it[index] = it[index].copy(step = newText)
        }
    }
}