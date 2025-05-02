package com.proteam.aiskincareadvisor.data.model

data class SkinAnalysisResult(
    val skinType: String,
    val concerns: List<String>,
    val recommendations: List<String>
)