package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ZakaIndigoLight,
    onPrimary = ZakaNavy,
    primaryContainer = ZakaIndigo,
    onPrimaryContainer = ZakaTextPrimaryDark,
    secondary = ZakaTealLight,
    onSecondary = ZakaNavy,
    tertiary = ZakaAmber,
    background = ZakaBackgroundDark,
    surface = ZakaSurfaceDark,
    surfaceVariant = ZakaCardDark,
    onBackground = ZakaTextPrimaryDark,
    onSurface = ZakaTextPrimaryDark,
    onSurfaceVariant = ZakaTextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = ZakaIndigo,
    onPrimary = ZakaSurfaceLight,
    primaryContainer = Color(0xFFE0E7FF),
    onPrimaryContainer = ZakaNavy,
    secondary = ZakaTeal,
    onSecondary = ZakaSurfaceLight,
    tertiary = ZakaAmber,
    background = ZakaBackgroundLight,
    surface = ZakaSurfaceLight,
    surfaceVariant = Color(0xFFF1F5F9),
    onBackground = ZakaTextPrimaryLight,
    onSurface = ZakaTextPrimaryLight,
    onSurfaceVariant = ZakaTextSecondaryLight
)

@Composable
fun ZakaAcademyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

// Keep alias for compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) = ZakaAcademyTheme(darkTheme = darkTheme, content = content)
