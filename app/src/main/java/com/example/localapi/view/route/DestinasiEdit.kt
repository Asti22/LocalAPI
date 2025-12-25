package com.example.localapi.view.route

import com.example.localapi.R

object DestinasiEdit : DestinasiNavigasi { // Ganti dari Destinasi ke DestinasiNavigasi
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}