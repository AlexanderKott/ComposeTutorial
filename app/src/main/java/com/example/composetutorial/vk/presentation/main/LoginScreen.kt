package com.example.composetutorial.sumin.vk.presentation.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composetutorial.R


@Composable
fun LoginScreen( loginClick: ()-> Unit){

    Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement =  Arrangement.SpaceEvenly ,
            horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Image(
            modifier = Modifier.size(160.dp),
            painter = painterResource(id = R.drawable.vk_compact_logo__2021_present_),
            contentDescription = null )

        Button(
            colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = vkBlue),
            onClick = { loginClick() }) {
            Text(text = "Логинится в VK")
        }

    }



}