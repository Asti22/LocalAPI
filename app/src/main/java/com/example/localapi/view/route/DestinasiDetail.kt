package com.example.localapi.view.route

import com.example.localapi.R

object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = R.string.detail_siswa // Pastikan resource ini ada di strings.xml
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}