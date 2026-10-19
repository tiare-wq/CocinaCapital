package com.example.cocinacapital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.cocinacapital.ui.navigation.AppNavigation
import com.example.cocinacapital.ui.theme.CocinaCapitalTheme

@kotlin.time.ExperimentalTime
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CocinaCapitalTheme {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}