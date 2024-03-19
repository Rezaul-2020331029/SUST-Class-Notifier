package com.example.sustclassnotifier.presentation.main.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Bolt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.R
import com.example.sustclassnotifier.components.CustomElevatedButton
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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
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
        
        Spacer(modifier = Modifier.height(20.dp)
        )

        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.not_1), contentDescription = "" )
            Text(text = "Notifactions ", fontSize = 24.sp)

            
        }



        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(60.dp)

        ) {
            Icon(painter = painterResource(id = R.drawable.up_1), contentDescription = "" )
            Text(text = "Update Routine ", fontSize = 24.sp)

        }
    }
}