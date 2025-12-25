package com.example.localapi.viewmodel.provider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localapi.modeldata.DataSiswa
import com.example.localapi.repositori.RepositoryDataSiswa
import com.example.localapi.view.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface StatusUIDetail {
    data class Success(val satusiswa: DataSiswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    // ✅ Mengambil ID dari navigasi sebagai String
    private val idSiswa: String = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    /**
     * Mengambil data satu siswa berdasarkan ID
     */
    fun getSatuSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            try {
                // Memanggil repository dengan ID String
                val siswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                statusUIDetail = StatusUIDetail.Success(siswa)
            } catch (e: IOException) {
                // Biasanya masalah jaringan/internet
                statusUIDetail = StatusUIDetail.Error
            } catch (e: HttpException) {
                // Error dari server (seperti 404 atau 500)
                statusUIDetail = StatusUIDetail.Error
            } catch (e: Exception) {
                // Error umum lainnya
                statusUIDetail = StatusUIDetail.Error
            }
        }
    }

    /**
     * Menghapus data satu siswa berdasarkan ID
     */
    fun hapusSatuSiswa() {
        viewModelScope.launch {
            try {
                // Memanggil fungsi hapus di repository
                repositoryDataSiswa.hapusSatuSiswa(idSiswa)
                println("Sukses Hapus Data dengan ID: $idSiswa")
            } catch (e: Exception) {
                println("Gagal Hapus Data: ${e.message}")
            }
        }
    }
}