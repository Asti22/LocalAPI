package com.example.localapi.viewmodel.provider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localapi.modeldata.DataSiswa
import com.example.localapi.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// ✅ Samakan nama dengan UI
sealed interface StatusUIHome {
    object Loading : StatusUIHome
    data class Success(val siswa: List<DataSiswa>) : StatusUIHome
    object Error : StatusUIHome
}

class HomeViewModel(
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    // ✅ Samakan nama variabel dengan UI
    var statusUiHome: StatusUIHome by mutableStateOf(StatusUIHome.Loading)
        private set

    init {
        getSiswa()
    }

    fun getSiswa() {
        viewModelScope.launch {
            statusUiHome = StatusUIHome.Loading
            statusUiHome = try {
                StatusUIHome.Success(
                    repositoryDataSiswa.getDataSiswa()
                )
            } catch (e: IOException) {
                StatusUIHome.Error
            } catch (e: HttpException) {
                StatusUIHome.Error
            }
        }
    }
}
