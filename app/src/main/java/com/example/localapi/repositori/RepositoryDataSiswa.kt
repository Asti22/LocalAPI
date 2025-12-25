package com.example.localapi.repositori

import com.example.localapi.apiservice.ServiceApiSiswa
import com.example.localapi.modeldata.DataSiswa
import retrofit2.Response

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<Void>
    suspend fun getSatuSiswa(id: String): DataSiswa // ✅ Ubah ke String
    suspend fun editSatuSiswa(id: String, dataSiswa: DataSiswa): Response<Void> // ✅ Ubah ke String
    suspend fun hapusSatuSiswa(id: String): Response<Void> // ✅ Ubah ke String
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()

    override suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<Void> =
        serviceApiSiswa.postSiswa(dataSiswa)

    override suspend fun getSatuSiswa(id: String): DataSiswa =
        serviceApiSiswa.getSatuSiswa(id) // ✅ Sekarang kirim String

    override suspend fun editSatuSiswa(id: String, dataSiswa: DataSiswa): Response<Void> =
        serviceApiSiswa.editSatuSiswa(id, dataSiswa) // ✅ Sekarang kirim String

    override suspend fun hapusSatuSiswa(id: String): Response<Void> =
        serviceApiSiswa.hapusSatuSiswa(id) // ✅ Sekarang kirim String
}