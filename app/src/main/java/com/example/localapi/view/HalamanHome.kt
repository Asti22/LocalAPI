package com.example.localapi.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localapi.R
import com.example.localapi.modeldata.DataSiswa
import com.example.localapi.view.route.DestinasiHome
import com.example.localapi.view.uicontroller.ErrorScreen
import com.example.localapi.view.uicontroller.LoadingScreen
import com.example.localapi.viewmodel.provider.HomeViewModel
import com.example.localapi.viewmodel.provider.PenyediaViewModel
import com.example.localapi.viewmodel.provider.StatusUIHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit, // ✅ Ubah parameter dari Int menjadi String
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        }
    ) { innerPadding ->
        HomeStatus(
            statusUiHome = viewModel.statusUiHome,
            retryAction = { viewModel.getSiswa() },
            onDetailClick = onDetailClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeStatus(
    statusUiHome: StatusUIHome,
    retryAction: () -> Unit,
    onDetailClick: (String) -> Unit, // ✅ Ubah ke String
    modifier: Modifier = Modifier
) {
    when (statusUiHome) {
        is StatusUIHome.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is StatusUIHome.Success -> {
            if (statusUiHome.siswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.no_data_siswa))
                }
            } else {
                ListSiswa(
                    itemSiswa = statusUiHome.siswa,
                    onDetailClick = onDetailClick,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        is StatusUIHome.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ListSiswa(
    itemSiswa: List<DataSiswa>,
    onDetailClick: (String) -> Unit, // ✅ Ubah ke String
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // ✅ Tambahkan handle null pada key id
        items(items = itemSiswa, key = { it.id ?: it.hashCode() }) { siswa ->
            CardSiswa(
                siswa = siswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(siswa.id ?: "") } // ✅ Berikan string kosong jika null
            )
        }
    }
}

@Composable
fun CardSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = stringResource(R.string.phone_label, siswa.telpon),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.address_label, siswa.alamat),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// LoadingScreen dan ErrorScreen tetap sama seperti sebelumnya...