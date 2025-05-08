package com.proteam.aiskincareadvisor

import android.app.Application
import com.google.firebase.FirebaseApp

class AISkinCareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}
