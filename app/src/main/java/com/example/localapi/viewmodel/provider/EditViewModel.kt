package com.example.localapi.viewmodel.provider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localapi.modeldata.DetailSiswa
import com.example.localapi.modeldata.UIStateSiswa
import com.example.localapi.modeldata.toDataSiswa
import com.example.localapi.modeldata.toUiStateSiswa
import com.example.localapi.repositori.RepositoryDataSiswa
import com.example.localapi.view.route.DestinasiDetail
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // ✅ UBAH KE String agar cocok dengan Repository
    private val idSiswa: String = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    init {
        viewModelScope.launch {
            try {
                // Mengambil data dan mengubahnya ke UI State
                val dataSiswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                uiStateSiswa = dataSiswa.toUiStateSiswa(isEntryValid = true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = uiStateSiswa.copy(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(detailSiswa: DetailSiswa): Boolean {
        return with(detailSiswa) {
            nama.isNotBlank() &&
                    alamat.isNotBlank() &&
                    telpon.isNotBlank()
        }
    }

    fun editSatuSiswa() {
        if (!validasiInput(uiStateSiswa.detailSiswa)) return

        viewModelScope.launch {
            try {
                // ✅ idSiswa sudah String, jadi tidak merah lagi
                val response = repositoryDataSiswa.editSatuSiswa(
                    idSiswa,
                    uiStateSiswa.detailSiswa.toDataSiswa()
                )

                if (response.isSuccessful) {
                    println("Update Sukses")
                } else {
                    println("Update Gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Update Error: ${e.message}")
            }
        }
    }
}