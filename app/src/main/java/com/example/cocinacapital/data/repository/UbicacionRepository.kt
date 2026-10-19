
package com.example.cocinacapital.data.repository

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.cocinacapital.data.model.Ubicacion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "preferencias_usuario"
)

private val LATITUD = doublePreferencesKey("latitud")
private val LONGITUD = doublePreferencesKey("longitud")

object UbicacionRepository {

    // Ubicación activa en memoria
    private val _ubicacionActual =
        MutableStateFlow<Ubicacion?>(null)

    val ubicacionActual: StateFlow<Ubicacion?> =
        _ubicacionActual.asStateFlow()

    // Actualizar ubicación activa
    fun actualizarUbicacion(
        latitud: Double,
        longitud: Double
    ) {
        _ubicacionActual.value = Ubicacion(
            latitud = latitud,
            longitud = longitud
        )
    }

    // Obtener ubicación activa
    fun obtenerUbicacion(): Ubicacion? {
        return _ubicacionActual.value
    }

    // Guardar última ubicación en DataStore
    suspend fun guardarUbicacion(
        context: Context,
        ubicacion: Ubicacion
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[LATITUD] = ubicacion.latitud
            preferencias[LONGITUD] = ubicacion.longitud
        }

        _ubicacionActual.value = ubicacion
    }

    // Recuperar última ubicación guardada
    fun obtenerUltimaUbicacion(
        context: Context
    ): Flow<Ubicacion?> {

        return context.dataStore.data.map { preferencias ->

            val latitud = preferencias[LATITUD]
            val longitud = preferencias[LONGITUD]

            if (latitud != null && longitud != null) {
                Ubicacion(
                    latitud = latitud,
                    longitud = longitud
                )
            } else {
                null
            }
        }
    }

    // Limpiar ubicación activa
    fun limpiarUbicacion() {
        _ubicacionActual.value = null
    }

    // Eliminar ubicación persistida
    suspend fun eliminarUbicacion(context: Context) {
        context.dataStore.edit { preferencias ->
            preferencias.remove(LATITUD)
            preferencias.remove(LONGITUD)
        }

        limpiarUbicacion()
    }
}
