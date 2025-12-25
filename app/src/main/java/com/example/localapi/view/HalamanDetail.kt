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

}