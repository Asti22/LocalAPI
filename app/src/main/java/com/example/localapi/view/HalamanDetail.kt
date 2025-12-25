package com.example.localapi.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.localapi.viewmodel.provider.DetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            val uiState = viewModel.statusUIDetail
            FloatingActionButton(
                onClick = {
                    if (uiState is StatusUIDetail.Success) {
                        navigateToEditItem(uiState.satusiswa.id) // Sesuai nama property di ViewModel Anda
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.update),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()
        BodyDetailSiswa( // Mengganti nama fungsi agar konsisten
            statusUiDetail = viewModel.statusUIDetail,
            onDelete = {
                coroutineScope.launch {
                    viewModel.hapusSatuSiswa()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}
@Composable
private fun BodyDetailSiswa(
    statusUiDetail: StatusUIDetail,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

            when (statusUiDetail) {
                is StatusUIDetail.Loading -> {
                    Text(text = "Loading...")
                }
                is StatusUIDetail.Success -> {
                    ItemDetailSiswa(
                        dataSiswa = statusUiDetail.satusiswa,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedButton(
                        onClick = { deleteConfirmationRequired = true },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.delete))
                    }
                    if (deleteConfirmationRequired) {
                        DeleteConfirmationDialog(
                            onDeleteConfirm = {
                                deleteConfirmationRequired = false
                                onDelete()
                            },
                            onDeleteCancel = { deleteConfirmationRequired = false },
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                        )
                    }
                }
                is StatusUIDetail.Error -> {
                    Text(text = "Terjadi Kesalahan")
                }
            }
        }
    }
    @Composable
    fun ItemDetailSiswa(
        dataSiswa: DataSiswa,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                ItemDetailRow(label = "ID", value = dataSiswa.id.toString())
                ItemDetailRow(label = "Nama", value = dataSiswa.nama)
                ItemDetailRow(label = "Alamat", value = dataSiswa.alamat)
                ItemDetailRow(label = "Telpon", value = dataSiswa.telpon)
            }
        }
    }
    @Composable
    private fun ItemDetailRow(
        label: String,
        value: String,
        modifier: Modifier = Modifier
    ) {
        Row(modifier = modifier) {
            Text(text = label, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Text(text = value)
        }
    }
    @Composable
    private fun DeleteConfirmationDialog(
        onDeleteConfirm: () -> Unit,
        onDeleteCancel: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(stringResource(R.string.attention)) },
            text = { Text(stringResource(R.string.delete_confirmation)) },
            modifier = modifier,
            dismissButton = {
                TextButton(onClick = onDeleteCancel) {
                    Text(stringResource(R.string.no))
                }
            },
            confirmButton = {
                TextButton(onClick = onDeleteConfirm) {
                    Text(stringResource(R.string.yes))
                }
            }
        )
    }