package com.lbc.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lbc.feature_detail.DetailRoute
import com.lbc.feature_favorite.FavoriteRoute
import com.lbc.feature_home.HomeRoute

@Composable
fun MovieNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MovieDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MovieDestinations.HOME_ROUTE) {
            HomeRoute(
                homeViewModel = hiltViewModel(),
                moveToDetail = MovieNavigationActions(navController).navigateToDetail,
            )
        }

        composable(MovieDestinations.FAVORITE_ROUTE) {
            FavoriteRoute(
                favoriteViewModel = hiltViewModel(),
                moveToDetail = MovieNavigationActions(navController).navigateToDetail,
            )
        }

        composable(
            route = "${MovieDestinations.DETAIL_ROUTE}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            val id = entry.arguments!!.getLong("id", 0)
            DetailRoute(
                detailViewModel = hiltViewModel(),
                id = id
            )
        }
    }
}
