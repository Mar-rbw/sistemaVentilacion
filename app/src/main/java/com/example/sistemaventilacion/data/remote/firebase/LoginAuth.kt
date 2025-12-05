package com.example.sistemaventilacion.data.remote.firebase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemaventilacion.ui.navigation.NavegacionApp


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegacionApp()
        }
    }
}