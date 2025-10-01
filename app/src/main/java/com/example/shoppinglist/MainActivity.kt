package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.component.*
import com.example.shoppinglist.component.ProfileScreen
import com.example.shoppinglist.components.ShoppingList
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Panggil fungsi utama navigasi
                    MainNavigationApp()
                }
            }
        }
    }

    sealed class AppScreen(val route: String, val title: String, val icon: ImageVector? = null) {
        data object ShoppingList : AppScreen("shoppinglist", "Belanja", Icons.Default.Home)
        data object Profile : AppScreen("profile", "Profil", Icons.Default.Person)

        data object Settings : AppScreen("settings", "Pengaturan", Icons.Default.Settings)
        data object Favorites : AppScreen("favorites", "Favorit", Icons.Default.Favorite)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val bottomNavItems = listOf(
        MainActivity.AppScreen.ShoppingList,
        MainActivity.AppScreen.Profile
    )
    val routesWithBottomBar = listOf(
        MainActivity.AppScreen.ShoppingList.route,
        MainActivity.AppScreen.Favorites.route,
        MainActivity.AppScreen.Profile.route,
        MainActivity.AppScreen.Settings.route
    )

    val drawerNavItems = listOf(
        MainActivity.AppScreen.Settings,
        MainActivity.AppScreen.Favorites,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))

                drawerNavItems.forEach { screen ->
                    NavigationDrawerItem(
                        icon = { Icon(screen.icon!!, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        gesturesEnabled = currentRoute !in drawerNavItems.map { it.route } // Izinkan gesture di luar halaman Drawer
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentRoute?.let {

                        when (it) {
                            MainActivity.AppScreen.ShoppingList.route -> MainActivity.AppScreen.ShoppingList.title
                            MainActivity.AppScreen.Profile.route -> MainActivity.AppScreen.Profile.title
                            MainActivity.AppScreen.Settings.route -> MainActivity.AppScreen.Settings.title
                            MainActivity.AppScreen.Favorites.route -> MainActivity.AppScreen.Favorites.title
                            else -> "Aplikasi Belanja"
                        }
                    } ?: "Aplikasi Belanja") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply { if (isClosed) open() else close() }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu Drawer")
                        }
                    }
                )
            },
            bottomBar = {
                if (currentRoute in routesWithBottomBar) {
                    BottomNavigationBar(navController, bottomNavItems, currentRoute)
                }
            }
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
                startDestination = MainActivity.AppScreen.ShoppingList.route
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = MainActivity.AppScreen.ShoppingList.route,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) }
        ) {
            ShoppingListScreen()
        }

        composable(
            route = MainActivity.AppScreen.Profile.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(400)
                ) + fadeIn(tween(400))
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(400)
                ) + fadeOut(tween(400))
            }
        ) {
            ProfileScreen()
        }

        composable(
            route = MainActivity.AppScreen.Settings.route,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) }
        ) {
            Setting()
        }

        composable(
            route = MainActivity.AppScreen.Favorites.route,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) }
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ini Halaman Favorit")
            }
        }
    }
}


@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<MainActivity.AppScreen>,
    currentRoute: String?
) {
    NavigationBar {
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                icon = { Icon(screen.icon!!, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun ShoppingListScreen() {
    var newItemText by rememberSaveable { mutableStateOf("") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val shoppingItems = remember { mutableStateListOf<String>() }

    val filteredItems by remember(searchQuery, shoppingItems) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                shoppingItems
            } else {
                shoppingItems.filter {
                    it.contains(searchQuery, ignoreCase = true)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Title()

        ItemInput(
            text = newItemText,
            onTextChange = { newItemText = it },
            onAddItem = {
                if (newItemText.isNotBlank()) {
                    shoppingItems.add(newItemText)
                    newItemText = ""
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SearchInput(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ShoppingList(items = filteredItems)
    }
}
