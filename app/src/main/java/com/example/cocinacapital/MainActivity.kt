package com.example.cocinacapital

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.cocinacapital.ui.navigation.AppNavigation
import com.example.cocinacapital.ui.navigation.LogginNavigation
import com.example.cocinacapital.ui.theme.CocinaCapitalTheme

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