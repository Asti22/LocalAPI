@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)
package com.example.localapi.viewmodel.provider

import com.example.localapi.modeldata.DataSiswa


sealed interface StatusUIDetail {
    data class Success(val satusiswa: DataSiswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

