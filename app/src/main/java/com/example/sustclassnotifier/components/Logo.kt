package com.example.sustclassnotifier.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sustclassnotifier.R
import com.example.sustclassnotifier.strings.APP_LOGO
import com.example.sustclassnotifier.strings.CLASSMATE_LOGO

@Composable
fun Logo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = APP_LOGO,
            modifier = Modifier.weight(0.75f)
        )

        Card (
            colors = CardDefaults.cardColors(Color.Yellow)
        ){
            OutlinedTextField(value = "SUST Class Notifier",
                textStyle = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Italic
                ),
                enabled = false,
                readOnly = true,
                onValueChange = {})
        }
    }
}