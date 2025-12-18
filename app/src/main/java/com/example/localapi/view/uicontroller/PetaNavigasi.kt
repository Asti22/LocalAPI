package com.example.localapi.view.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun DataSiswaApp(navController: NavHostController = rememberNavController(),
                 modifier: Modifier = Modifier) {
    HostNavigasi(navController = navController)
}