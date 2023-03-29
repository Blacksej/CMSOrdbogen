package dev.danieltm.cmsordbogen.Models

import android.net.Uri
import java.time.LocalDate

data class PostModel(
    val title: String,
    val body: String,
    val type: String,
    val site: List<String>,
    val author: String,
    val uri: Uri?,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val creationTime: LocalDate
)
