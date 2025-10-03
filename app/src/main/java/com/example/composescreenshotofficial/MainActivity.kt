package com.example.composescreenshotofficial

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleScreen()
        }
    }
}

@Composable
fun SampleScreen() {
    Surface(
         modifier = Modifier.fillMaxSize(),
         color = MaterialTheme.colorScheme.background
     ) {
         Column(
             modifier = Modifier.fillMaxSize().padding(16.dp),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             Text(
                 "Hello, Compose Official Screenshot!",
                 style = MaterialTheme.typography.headlineSmall
             )
             Spacer(Modifier.height(8.dp))
             Button(onClick = {}) {
                 Text("Press me")
             }
         }
     }
}
