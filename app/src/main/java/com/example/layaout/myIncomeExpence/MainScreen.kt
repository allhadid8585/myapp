package com.example.layaout.myIncomeExpence

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.layaout.R
import com.example.layaout.myIncomeExpence.helperFunctions.changeLanguage
import com.example.layaout.myIncomeExpence.helperFunctions.getText
import com.example.layaout.myIncomeExpence.navigation.Dialogs
import com.example.layaout.myIncomeExpence.navigation.NavHostGraph
import com.example.layaout.myIncomeExpence.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    activity: Activity
) {
    val context = LocalContext.current as Activity

    val navController = rememberNavController()

    var isAddTransactionDialog by remember { mutableStateOf(false) }
    var isAddCategoryDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {

            Box(modifier = Modifier
                .height(170.dp)){
            var visible by remember {
                mutableStateOf(false)
            }
                if (visible){
                        Button(onClick = {
                            navController.navigate(Dialogs.AddCategory.name)
                            visible =  false
                        },
                            modifier = Modifier.align(Alignment.TopEnd)) {
                            Text(text = stringResource(id = R.string.Category_Name))
                        }
                        Button(onClick = {
                            navController.navigate(Dialogs.AddTransaction.name)
                            visible =false
                        },
                            modifier = Modifier.align(Alignment.CenterEnd)) {
                            Text(text = stringResource(id = R.string.Transaction_Name))
                        }
                }
            FloatingActionButton(
                onClick = { visible = !visible},
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.BottomEnd)

            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
            }
        },
        bottomBar = {
          NavigationBar {
              var currentRoot = navController.currentBackStackEntryAsState().value?.destination?.route
              NavigationBarItem(
                  selected = currentRoot == Screens.TRANSACTION.name,
                  onClick = { navController.navigate(Screens.TRANSACTION.name){
                      popUpTo(Screens.TRANSACTION.name){inclusive = true}
                      launchSingleTop = true
                      restoreState = true
                  } },
                  icon = {
                      AnimatedVisibility(
                          visible = currentRoot == Screens.TRANSACTION.name,
                          enter = fadeIn(spring(Spring.DampingRatioHighBouncy)) + expandVertically(spring(Spring.DampingRatioHighBouncy)),
                          exit = fadeOut()
                      ) {
                          Row {
                              Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                              Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                          }
                      }
                      Row {
                          Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                          Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                      }
                 },
                  label = { Text(text = getText(key = "transaction_name"))}
              )

              NavigationBarItem(
                  selected = currentRoot == Screens.INCOME.name,
                  onClick = { navController.navigate(Screens.INCOME.name){
                      popUpTo(Screens.TRANSACTION.name)
                      launchSingleTop = true
                      restoreState = true
                  } },
                  icon = {

                          AnimatedVisibility(
                              visible = currentRoot == Screens.INCOME.name,
                              enter = fadeIn(spring(Spring.DampingRatioHighBouncy)) + expandVertically(spring(Spring.DampingRatioHighBouncy)),
                              exit = fadeOut()
                          )
                          {
                              Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                          }
                      Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)


                  },
                  label = { Text(text = getText(key = "income_name"))}
              )
              NavigationBarItem(
                  selected = currentRoot == Screens.EXPANSE.name,
                  onClick = { navController.navigate(Screens.EXPANSE.name){
                      popUpTo(Screens.TRANSACTION.name)
                      launchSingleTop = true
                      restoreState = true
                  } },
                  icon = {
                      AnimatedVisibility(
                          visible = currentRoot == Screens.EXPANSE.name,
                          enter = fadeIn(spring(Spring.DampingRatioHighBouncy)) + expandVertically(spring(Spring.DampingRatioHighBouncy)),
                          exit = fadeOut()
                      ){
                          Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                      }
                         Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                  },
                  label = { Text(text = getText(key = "expense_name"))}
              )
          }
        }
    ) { paddingValues ->


        NavHostGraph(navController = navController, modifier = Modifier.padding(paddingValues))



    }
}
