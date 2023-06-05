package com.id.etourism.data.network.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList


data class Wisata(

    var Place_Id: String? = null,
    var Place_Name : String? = null,
    var Description : String? = null,
    var Category: String? = null,
    var City : String? = null,
    var Price : String? = null,
    var Rating: String? = null,
    var Time_Minutes : String? = null,
    var Coordinate : String? = null,
    var Lat : String? = null,
    var Long: String? = null,

)
