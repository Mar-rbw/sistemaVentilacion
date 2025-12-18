package com.example.sistemaventilacion

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class SistemaVentilacionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
