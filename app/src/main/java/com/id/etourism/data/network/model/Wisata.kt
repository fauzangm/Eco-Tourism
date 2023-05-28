package com.id.etourism.data.network.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList


data class Wisata(

    var image : String? = null,
    var deskripsi : String? = null,
    var name : String? = null,

    var wisata : ArrayList<Map<String,String>>? = null

)
