@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)

package com.example.localapi.viewmodel.provider

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localapi.modeldata.DataSiswa
import com.example.localapi.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
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

    private val idSiswa: Int =
        checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    fun getSatuSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                val siswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                StatusUIDetail.Success(siswa)
            } catch (e: IOException) {
                StatusUIDetail.Error
            } catch (e: HttpException) {
                StatusUIDetail.Error
            }
        }
    }

    fun hapusSatuSiswa() {
        viewModelScope.launch {
            val resp: Response<Void> =
                repositoryDataSiswa.hapusSatuSiswa(idSiswa)

            if (resp.isSuccessful) {
                println("Sukses Hapus Data")
            } else {
                println("Gagal Hapus Data")
            }
        }
    }
}
