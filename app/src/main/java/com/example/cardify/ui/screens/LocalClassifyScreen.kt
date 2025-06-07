package com.example.cardify.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cardify.data.InMemoryCardRepository
import com.example.cardify.data.TempImageHolder
import com.example.cardify.features.OcrNerProcessor
import com.example.cardify.models.BusinessCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalClassifyScreen(
    navController: NavController,
    processor: OcrNerProcessor
) {
    val bitmap = TempImageHolder.bitmap
    val scope = rememberCoroutineScope()
    var card by remember { mutableStateOf<BusinessCard?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(bitmap) {
        bitmap?.let {
            card = processor.process(it)
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("명함 분석") })
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                card?.let { c ->
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        bitmap?.let { Image(it.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp)) }
                        Spacer(modifier = Modifier.height(16.dp))
                        var name by remember { mutableStateOf(c.name) }
                        var phone by remember { mutableStateOf(c.phone) }
                        var company by remember { mutableStateOf(c.company) }
                        var position by remember { mutableStateOf(c.position) }
                        var email by remember { mutableStateOf(c.email) }
                        var etc by remember { mutableStateOf("") }

                        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("이름") })
                        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("전화번호") })
                        OutlinedTextField(value = company, onValueChange = { company = it }, label = { Text("회사") })
                        OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("직책") })
                        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("이메일") })
                        OutlinedTextField(value = etc, onValueChange = { etc = it }, label = { Text("그 외") })

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val newCard = c.copy(name = name, phone = phone, company = company, position = position, email = email, sns = etc)
                            InMemoryCardRepository.addCard(newCard.copy(imageUrl = "temp"))
                            navController.navigateUp()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("저장")
                        }
                    }
                }
            }
        }
    }
}
