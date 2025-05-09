package com.proteam.aiskincareadvisor.data.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.proteam.aiskincareadvisor.data.model.SkinAnalysisResult
import kotlinx.coroutines.tasks.await

class SkinAnalysisStorage {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun saveAnalysisResult(result: SkinAnalysisResult): Result<Unit> {
        val userId = auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))
        val docRef = firestore
            .collection("users")
            .document(userId)
            .collection("skin_analysis")
            .document() // Auto-generated ID

        return try {
            val dataWithTimestamp = result.copy(timestamp = System.currentTimeMillis())
            docRef.set(dataWithTimestamp).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLatestAnalysisResult(): SkinAnalysisResult? {
        val userId = auth.currentUser?.uid ?: return null
        val snapshot = firestore
            .collection("users")
            .document(userId)
            .collection("skin_analysis")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return snapshot.documents.firstOrNull()?.toObject(SkinAnalysisResult::class.java)
    }
}
