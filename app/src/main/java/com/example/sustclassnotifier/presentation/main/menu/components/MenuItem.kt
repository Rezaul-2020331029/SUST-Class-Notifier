package com.example.sustclassnotifier.presentation.main.menu.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sustclassnotifier.navigation.MenuItem
import com.example.sustclassnotifier.ui.theme.ButtonStyle
import com.example.sustclassnotifier.ui.theme.MediumRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun MenuItem(menuItem: MenuItem, onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = MediumSpace),
        shape = MediumRounded
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = menuItem.iconId), contentDescription = menuItem.title)
            Spacer(modifier = Modifier.width(SmallSpace))
            Text(text = menuItem.title, style = ButtonStyle)
        }
    }
}