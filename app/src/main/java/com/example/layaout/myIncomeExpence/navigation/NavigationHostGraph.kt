package com.example.layaout.myIncomeExpence.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.layaout.myIncomeExpence.screen.AddCategoryDialog
import com.example.layaout.myIncomeExpence.screen.AddTransactionDialog
import com.example.layaout.myIncomeExpence.screen.ExpanseScreen
import com.example.layaout.myIncomeExpence.screen.IncomeScreen
import com.example.layaout.myIncomeExpence.screen.TransactionScreen


@Composable
fun NavHostGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = Screens.TRANSACTION.name,
        modifier = modifier
    ) {
        composable(
            Screens.TRANSACTION.name,
            enterTransition = { fadeIn(tween()) + slideInVertically(tween(300,100)){-it} },
            exitTransition = { fadeOut()+ slideOutVertically(tween(100))  }
        ) {
            TransactionScreen(navController)
        }
        composable(
            Dialogs.AddCategory.name
        ){
            AddCategoryDialog {
                navController.popBackStack()
            }
        }
        composable(Dialogs.AddTransaction.name){
            AddTransactionDialog {
                navController.popBackStack()
            }
        }

        composable(
            Screens.INCOME.name,
            enterTransition = { fadeIn() + slideInVertically(tween(300,100)){-it} },
            exitTransition = { fadeOut() + slideOutVertically(tween(100))  }
        ){
            IncomeScreen(navController = navController)
        }
        
        composable(
            Screens.EXPANSE.name,
            enterTransition = { fadeIn() + slideInVertically(tween(300,100)){-it} },
            exitTransition = { fadeOut()+ slideOutVertically(tween(100))  }
        ){
            ExpanseScreen(navController = navController)
        }
    }
}
