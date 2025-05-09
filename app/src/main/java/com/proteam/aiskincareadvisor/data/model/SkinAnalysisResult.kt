package com.proteam.aiskincareadvisor.data.model

data class SkinAnalysisResult(
    val skinType: String = "",
    val hydrationLevel: String = "",
    val oilLevel: String = "",
    val overallCondition: String = "",
    val concerns: List<String> = emptyList(),
    val recommendations: List<String> = emptyList(),
    val tips: List<String> = emptyList(),
    val recommendedProductIds: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

