package com.pzwebdev.skytrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pzwebdev.skytrack.ui.component.AppNavigation
import com.pzwebdev.skytrack.ui.theme.SkyTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyTrackTheme {
                AppNavigation()
            }
        }
    }
}
