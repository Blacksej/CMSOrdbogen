package dev.danieltm.cmsordbogen.Models

import android.net.Uri
import java.time.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    val id: Int,
    val type: String,
    val title: String,
    val sites: List<String>,
    val body: String,
    //val author: String,
    val image: String?,
    val startDate: String,
    val endDate: String
    //val creationTime: LocalDate
)
