package com.example.congresar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.congresar.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Noto Sans"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Lato"),
        fontProvider = provider,
    )
)

val baseline = Typography()

private fun TextStyle.withFont(fontFamily: FontFamily): TextStyle =
    copy(fontFamily = fontFamily)

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.withFont(displayFontFamily),
    displayMedium = baseline.displayMedium.withFont(displayFontFamily),
    displaySmall = baseline.displaySmall.withFont(displayFontFamily),
    headlineLarge = baseline.headlineLarge.withFont(displayFontFamily),
    headlineMedium = baseline.headlineMedium.withFont(displayFontFamily),
    headlineSmall = baseline.headlineSmall.withFont(displayFontFamily),
    titleLarge = baseline.titleLarge.withFont(displayFontFamily),
    titleMedium = baseline.titleMedium.withFont(displayFontFamily),
    titleSmall = baseline.titleSmall.withFont(displayFontFamily),
    bodyLarge = baseline.bodyLarge.withFont(bodyFontFamily),
    bodyMedium = baseline.bodyMedium.withFont(bodyFontFamily),
    bodySmall = baseline.bodySmall.withFont(bodyFontFamily),
    labelLarge = baseline.labelLarge.withFont(bodyFontFamily),
    labelMedium = baseline.labelMedium.withFont(bodyFontFamily),
    labelSmall = baseline.labelSmall.withFont(bodyFontFamily),
)
