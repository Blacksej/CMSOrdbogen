package dev.danieltm.cmsordbogen.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.danieltm.cmsordbogen.R


val bitterpro = FontFamily(
    Font(R.font.bitterpro_medium),
    Font(R.font.opensans_semibold)
)

val opensans = FontFamily(
    Font(R.font.opensans_semibold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = bitterpro,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp
    ),
    h5 = TextStyle(
        fontFamily = bitterpro,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    h6 = TextStyle(
        fontFamily = opensans,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)