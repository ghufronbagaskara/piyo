package com.example.piyo.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute

@Serializable
data object SplashRoute : AppRoute

@Serializable
data object OnboardingRoute : AppRoute

@Serializable
data object LoginRoute : AppRoute

@Serializable
data object RegisterRoute : AppRoute

@Serializable
data object InfoAnakRoute : AppRoute

@Serializable
data object MainRoute : AppRoute

@Serializable
data object PiyoHomeRoute : AppRoute

@Serializable
data object PiyoParentRoute : AppRoute

@Serializable
data object PiyoPlanRoute : AppRoute

@Serializable
data object SettingsRoute : AppRoute

@Serializable
data object ChatbotRoute : AppRoute

@Serializable
data object NotifikasiRoute : AppRoute

@Serializable
data object InsightRoute : AppRoute

@Serializable
data object KeamananIzinRoute : AppRoute
