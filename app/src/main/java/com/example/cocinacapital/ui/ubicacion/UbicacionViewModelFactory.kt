package com.example.cocinacapital.ui.ubicacion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocinacapital.data.repository.UbicacionRepository

class UbicacionViewModelFactory(
    context: Context
): ViewModelProvider.Factory {
    private val repository = UbicacionRepository(
        context.applicationContext
    )

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(UbicacionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UbicacionViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}