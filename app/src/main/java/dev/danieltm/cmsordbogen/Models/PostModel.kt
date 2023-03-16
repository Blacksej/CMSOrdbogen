package dev.danieltm.cmsordbogen.Models

import android.media.Image
import java.time.LocalDate
import java.util.Date

data class PostModel(
    val name: String,
    val body: String,
    val type: String,
    val image: List<Image>?,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
