package com.lbc.movieapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.lbc.movieapp.navigation.MovieNavGraph
import com.lbc.movieapp.navigation.MovieNavigationActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        MovieNavigationActions(navController)
    }
    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    val menuItems =
        listOf(
            MenuItem("홈", Icons.Default.Home, navigationActions.navigateToHome),
            MenuItem("즐겨찾기", Icons.Default.Favorite, navigationActions.navigateToFavorite)
        )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(coroutineScope, scaffoldState)
        },
        drawerContent = {
            AppDrawer(
                menuItems = menuItems,
                closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } },
                modifier = Modifier
                    .navigationBarsPadding()
            )
        }
    ) {
        MovieNavGraph(
            navController = navController
        )
    }
}

@Composable
fun AppDrawer(
    menuItems: List<MenuItem>,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))

        menuItems.forEach {
            DrawerContent(it.title, it.icon) {
                it.navigator()
                closeDrawer()
            }
        }
    }
}

data class MenuItem(val title: String, val icon: ImageVector, val navigator: () -> Unit)

@Composable
fun DrawerContent(
    title: String,
    icon: ImageVector,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Text(text = title)
    }
}

@Composable
fun AppBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(text = "MovieApp", fontSize = 12.sp)
        },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null
                )
            }
        }
    )
}
