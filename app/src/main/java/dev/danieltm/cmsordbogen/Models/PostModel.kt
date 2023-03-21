package dev.danieltm.cmsordbogen.Models

import android.net.Uri
import java.time.LocalDate

data class PostModel(
    val name: String,
    val body: String,
    val type: String,
    val uri: Uri?,
    val uris: List<Uri?>,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
