package com.example.sistemaventilacion.data.remote.modelView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemaventilacion.data.remote.firebase.ResetPasswordRepository
import com.example.sistemaventilacion.dataclass.PasswordReset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ResetPasswordViewModel(
    private val resetPasswordRepository: ResetPasswordRepository = ResetPasswordRepository()
) : ViewModel() {

    private val _resetState = MutableStateFlow(PasswordReset())
    val resetState: StateFlow<PasswordReset> = _resetState

    fun sendPasswordReset(email: String){
        if(email.isBlank()) {
            _resetState.value = PasswordReset(error = "El campo de correo electrónico no puede estar vacío.")
            return
        }

        _resetState.value = PasswordReset(isLoading = true)

        viewModelScope.launch {
            try {
                resetPasswordRepository.sendPassword(email)
                _resetState.value = PasswordReset(isSent = true)
            } catch ( e: Exception) {
                val errorMessage = e.message ?: "Error desconocido al enviar el correo."
                _resetState.value = PasswordReset(error = errorMessage)
            }
        }
    }
    fun resetState() {
        _resetState.value = PasswordReset()
    }
}