package com.example.localapi.view.uicontroller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localapi.modeldata.DataSiswa
import com.example.localapi.view.SiswaTopAppBar
import com.example.localapi.view.route.DestinasiHome
import com.example.localapi.viewmodel.provider.HomeViewModel
import com.example.localapi.viewmodel.provider.PenyediaViewModel
import com.example.localapi.viewmodel.provider.StatusUIHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (String) -> Unit, // ✅ Pastikan ini (String) -> Unit
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Siswa"
                )
            }
        }
    ) { innerPadding ->
        HomeBody(
            statusUiHome = viewModel.statusUiHome,
            onSiswaClick = navigateToItemUpdate,
            retryAction = { viewModel.getSiswa() },
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        )
    }
}

@Composable
fun HomeBody(
    statusUiHome: StatusUIHome,
    onSiswaClick: (String) -> Unit, // ✅ Ubah parameter Int menjadi String
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        when (statusUiHome) {
            is StatusUIHome.Loading -> LoadingScreen()
            is StatusUIHome.Success -> DaftarSiswa(
                itemSiswa = statusUiHome.siswa,
                // ✅ Sekarang it.id sudah String, berikan "" jika null
                onSiswaClick = { onSiswaClick(it.id ?: "") }
            )
            is StatusUIHome.Error -> ErrorScreen(retryAction = retryAction)
        }
    }
}

@Composable
fun DaftarSiswa(
    itemSiswa: List<DataSiswa>,
    onSiswaClick: (DataSiswa) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        // ✅ Gunakan safe call pada key karena id String bisa null
        items(items = itemSiswa, key = { it.id ?: it.hashCode() }) { siswa ->
            ItemSiswa(
                siswa = siswa,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onSiswaClick(siswa) }
            )
        }
    }
}

@Composable
fun ItemSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = siswa.telpon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Gagal memuat data siswa")
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}