package dev.danieltm.cmsordbogen.Models

import android.net.Uri
import java.time.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    // NEEDS TO BE CHANGED BACK TO VAL WHEN API IS MORE READY
    var id: Int,
    // NEEDS TO BE CHANGED BACK TO VAL WHEN API IS MORE READY
    var type: String,
    val title: String,
    val sites: List<String>,
    val body: String,
    //val author: String,
    val image: String?,
    val startDate: String,
    val endDate: String
    //val creationTime: LocalDate
)
