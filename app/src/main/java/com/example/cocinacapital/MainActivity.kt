package com.example.cocinacapital

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.cocinacapital.data.repository.PreguntasRepository
import com.example.cocinacapital.ui.navigation.AppNavigation
import com.example.cocinacapital.ui.navigation.LogginNavigation
import com.example.cocinacapital.ui.screen.HomeScreen
import com.example.cocinacapital.ui.screen.LogginScreen
import com.example.cocinacapital.ui.screen.PreguntasScreen
import com.example.cocinacapital.ui.theme.CocinaCapitalTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.example.Cliente

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CocinaCapitalTheme {
                Surface() {
                    LogginNavigation()
                }
            }
        }
    }
}