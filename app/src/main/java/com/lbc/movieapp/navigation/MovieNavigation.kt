package com.lbc.movieapp.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object MovieDestinations {
    const val HOME_ROUTE = "home"
    const val FAVORITE_ROUTE = "favorite"
    const val DETAIL_ROUTE = "detail"
}

class MovieNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(MovieDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToFavorite: () -> Unit = {
        navController.navigate(MovieDestinations.FAVORITE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToDetail: (id: Long) -> Unit = {
        navController.navigate("${MovieDestinations.DETAIL_ROUTE}/$it") {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
