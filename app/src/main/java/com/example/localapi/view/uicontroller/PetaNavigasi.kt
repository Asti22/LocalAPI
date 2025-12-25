package com.example.localapi.view.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.localapi.view.DetailSiswaScreen
import com.example.localapi.view.EditSiswaScreen
import com.example.localapi.view.route.DestinasiDetail
import com.example.localapi.view.route.DestinasiEdit
import com.example.localapi.view.route.DestinasiEntry
import com.example.localapi.view.route.DestinasiHome

/**
 * Fungsi utama yang dipanggil di MainActivity.
 */
@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // --- HALAMAN HOME ---
        // Di dalam HostNavigasi.kt
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                // ✅ Ganti nama parameter ini agar sama dengan yang ada di HomeScreen.kt
                navigateToItemUpdate = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id")
                }
            )
        }

        // --- HALAMAN ENTRY (TAMBAH) ---
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // --- HALAMAN DETAIL ---
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.itemIdArg) {
                // ✅ WAJIB StringType karena ID di DB adalah String
                type = NavType.StringType
            })
        ) {
            DetailSiswaScreen(
                navigateToEditItem = { id ->
                    navController.navigate("${DestinasiEdit.route}/$id")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // --- HALAMAN EDIT ---
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.itemIdArg) {
                // ✅ WAJIB StringType agar SavedStateHandle di EditViewModel tidak null
                type = NavType.StringType
            })
        ) {
            EditSiswaScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}