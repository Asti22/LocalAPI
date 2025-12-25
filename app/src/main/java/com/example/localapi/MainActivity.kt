package com.example.localapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.localapi.ui.theme.LocalAPITheme
import com.example.localapi.view.uicontroller.DataSiswaApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengaktifkan fitur tampilan penuh (edge-to-edge)
        enableEdgeToEdge()

        setContent {
            LocalAPITheme {
                /* Langsung memanggil DataSiswaApp tanpa Scaffold tambahan di sini.
                   Padding dan Scaffold sudah dikelola di dalam masing-masing screen
                   (Home, Entry, Detail) agar TopAppBar dan FloatingActionButton
                   muncul di posisi yang tepat.
                */
                DataSiswaApp()
            }
        }
    }
}