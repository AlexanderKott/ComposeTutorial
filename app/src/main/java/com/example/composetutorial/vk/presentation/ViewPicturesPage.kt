package com.example.composetutorial.sumin.vk.presentation.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Preview
@Composable
fun ViewPicturesPage() {

    val imageURI = rememberSaveable{ mutableStateOf(Uri.EMPTY) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent() , onResult =  { uri ->
            imageURI.value = uri
        })

    Column(modifier = Modifier.fillMaxWidth()) {

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            placeholder =  painterResource(id = com.example.composetutorial.R.drawable.instagram_2016_6),
            model = imageURI.value,
            contentDescription = null,
        )

      Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            onClick = { imageLauncher.launch("image/*") })
        {
            Text("Выбрать изображение")
         }
    }


}