package com.codeforcesvisualizer.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.codeforcesvisualizer.core.data.theme.CFTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CFTheme {
                Home()
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    CFTheme {
        Home()
    }
}