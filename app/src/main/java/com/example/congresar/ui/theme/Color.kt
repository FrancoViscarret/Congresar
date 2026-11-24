package com.example.congresar.ui.theme

import androidx.compose.ui.graphics.Color

val LogoPrimary = Color(0xFF004E89)
val LogoPrimaryBright = Color(0xFF005B99)
val LogoAccent = Color(0xFF00B4D8)
val LogoAccentBright = Color(0xFF00D2E6)

val AppBackground = Color(0xFFFFFFFF)
val AppSurface = Color(0xFFF8FAFC)
val AppSurfaceMuted = Color(0xFFF2F7FA)
val AppBorder = Color(0xFFE2E8F0)
val AppBorderStrong = Color(0xFFCBD5E1)

val TextPrimary = Color(0xFF111827)
val TextSecondary = Color(0xFF64748B)

val OnAccent = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFE7F2F8)
val SecondaryContainer = Color(0xFFE5F8FB)
val TertiaryContainer = Color(0xFFE8FBFD)

val ErrorRed = Color(0xFFBA1A1A)
val OnError = Color(0xFFFFFFFF)
val ErrorContainer = Color(0xFFFFDAD6)
val OnErrorContainer = Color(0xFF93000A)

val primaryLight = LogoPrimary
val onPrimaryLight = OnAccent
val primaryContainerLight = PrimaryContainer
val onPrimaryContainerLight = LogoPrimary
val secondaryLight = LogoAccent
val onSecondaryLight = OnAccent
val secondaryContainerLight = SecondaryContainer
val onSecondaryContainerLight = LogoPrimaryBright
val tertiaryLight = LogoAccentBright
val onTertiaryLight = OnAccent
val tertiaryContainerLight = TertiaryContainer
val onTertiaryContainerLight = LogoPrimary
val errorLight = ErrorRed
val onErrorLight = OnError
val errorContainerLight = ErrorContainer
val onErrorContainerLight = OnErrorContainer
val backgroundLight = AppBackground
val onBackgroundLight = TextPrimary
val surfaceLight = AppSurface
val onSurfaceLight = TextPrimary
val surfaceVariantLight = AppSurfaceMuted
val onSurfaceVariantLight = TextSecondary
val outlineLight = AppBorderStrong
val outlineVariantLight = AppBorder
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = TextPrimary
val inverseOnSurfaceLight = AppBackground
val inversePrimaryLight = LogoAccent
val surfaceDimLight = AppSurfaceMuted
val surfaceBrightLight = AppBackground
val surfaceContainerLowestLight = AppBackground
val surfaceContainerLowLight = AppSurface
val surfaceContainerLight = AppSurfaceMuted
val surfaceContainerHighLight = Color(0xFFEAF1F6)
val surfaceContainerHighestLight = AppBorder

/** Cyan at about 8% composited on white. Opaque so cards and chips stay readable. */
val BrandTint = Color(0xFFE8F8FB)

/** Soft cyan stroke for 1.dp borders. */
val BrandBorder = LogoAccent.copy(alpha = 0.40f)

/** Translucent cyan pill behind the selected navigation item. */
val NavIndicator = LogoAccentBright.copy(alpha = 0.18f)

val VoteAffirmative = Color(0xFF15803D)
val VoteAffirmativeContainer = Color(0xFFDCFCE7)
val VoteNegative = Color(0xFFDC2626)
val VoteNegativeContainer = Color(0xFFFEE2E2)
val VoteAbstention = Color(0xFFB45309)
val VoteAbstentionContainer = Color(0xFFFEF3C7)
val VoteAbsent = Color(0xFF64748B)
val VoteAbsentContainer = Color(0xFFF1F5F9)

val SeatInk = Color(0xFF1E3A4C)
val SeatGraphite = Color(0xFF3D5A6C)
val SeatSlate = Color(0xFF5C7384)
val SeatStone = Color(0xFF7C93A3)
val SeatAsh = Color(0xFF9AADB8)

val SeatPalette = listOf(SeatInk, SeatGraphite, SeatSlate, SeatStone, SeatAsh)
