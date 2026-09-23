package com.example.cocinacapital

import android.os.Bundle
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
import com.example.cocinacapital.ui.navigation.AppNavigation
import com.example.cocinacapital.ui.screen.HomeScreen
import com.example.cocinacapital.ui.theme.CocinaCapitalTheme
import org.example.Cliente

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocinaCapitalTheme {
                Surface() {
                    AppNavigation()
                }
            }
        }
    }
}