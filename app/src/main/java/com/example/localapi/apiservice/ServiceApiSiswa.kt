package com.example.localapi.apiservice

import com.example.localapi.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET("bacateman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(@Body dataSiswa: DataSiswa): Response<Void>

    @GET("baca1teman.php")
    suspend fun getSatuSiswa(@Query("id") id: String): DataSiswa // ✅ Ubah ke String

    @POST("editTM.php")
    suspend fun editSatuSiswa(@Query("id") id: String, @Body dataSiswa: DataSiswa): Response<Void> // ✅ Ubah ke String

    @GET("deleteTM.php")
    suspend fun hapusSatuSiswa(@Query("id") id: String): Response<Void> // ✅ Ubah ke String
}