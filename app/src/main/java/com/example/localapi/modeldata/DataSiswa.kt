package com.example.localapi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataSiswa(
    val id: String? = null,
    val nama: String,
    val alamat: String,
    val telpon: String
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

data class DetailSiswa(
    val id: String = "", // ✅ Diubah ke String agar konsisten
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

// --- Fungsi Konversi ---

fun DetailSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = if (id.isBlank()) null else id, // ✅ Jika kosong, kirim null agar DB handle Auto-Increment
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toUiStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
    detailSiswa = this.toDetailSiswa(),
    isEntryValid = isEntryValid
)

fun DataSiswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    id = id ?: "", // ✅ Jika id null, jadikan string kosong
    nama = nama,
    alamat = alamat,
    telpon = telpon
)