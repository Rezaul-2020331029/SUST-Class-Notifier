package com.example.sustclassnotifier.presentation.main.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.domain.event.MenuUIEvent
import com.example.sustclassnotifier.navigation.MenuItem
import com.example.sustclassnotifier.navigation.TabItem
import com.example.sustclassnotifier.presentation.main.components.ClassMateTabRow
import com.example.sustclassnotifier.presentation.main.menu.components.MenuItem
import com.example.sustclassnotifier.presentation.main.menu.components.SignOutItem
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight

@Composable
fun MenuScreen(menuViewModel: MenuViewModel = hiltViewModel()) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClassMateTabRow(tab = TabItem.Menu)
        Spacer(modifier = Modifier.height(NormalHeight))
        MenuItem(menuItem = MenuItem.Profile, onClick = {
            menuViewModel.onMenuEvent(MenuUIEvent.ProfileButtonClicked)
        })
        Spacer(modifier = Modifier.height(MediumSpace))
        MenuItem(menuItem = MenuItem.Routine, onClick = {
            menuViewModel.onMenuEvent(MenuUIEvent.RoutineButtonClicked)
        })
        Spacer(modifier = Modifier.height(MediumSpace))
        SignOutItem(menuItem = MenuItem.SignOut, onClick = {
            menuViewModel.onMenuEvent(MenuUIEvent.SignOutButtonClicked)
        })
    }
}