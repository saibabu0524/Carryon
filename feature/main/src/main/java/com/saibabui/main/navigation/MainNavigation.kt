//package com.saibabui.main.navigation
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.saibabui.main.presentation.ui.activity.ActivityScreen
//import com.saibabui.main.presentation.ui.home.HomeScreen
//import com.saibabui.main.presentation.ui.profile.ProfileScreen
//import com.saibabui.main.presentation.ui.transactions.TransactionScreen
//
//
//@Composable
//fun HomeNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
//    NavHost(
//        navController = navController,
//        route = Graph.MAIN,
//        startDestination = Home.HomeScreen.route,
//        modifier = Modifier.padding(paddingValues)
//    ) {
//        composable(Home.HomeScreen.route) {
//            HomeScreen(navController)
//        }
//        composable(Home.Activity.route) {
//            ActivityScreen(navController)
//        }
//        composable(Home.Transactions.route) {
//            TransactionScreen(navController)
//        }
//        composable(
//            Home.ProfileScreen.route
////                    + "/{name}/{gmail}/{userId}",
////            arguments = listOf(navArgument("name") {
////                type = NavType.StringType
////            },
////                navArgument("gmail") {
////                    type = NavType.StringType
////                },
////                navArgument("userId") {
////                    type = NavType.StringType
////                }
////            )
//        ) { backStackEntry ->
//            ProfileScreen(navController)
//        }
//    }
//}