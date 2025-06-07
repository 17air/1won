package com.example.cardify.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cardify.data.InMemoryCardRepository
import com.example.cardify.models.BusinessCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardScreen(index: Int, navController: NavController) {
    val cards = InMemoryCardRepository.getCards()
    if (index !in cards.indices) return
    val card = cards[index]
    var name by remember { mutableStateOf(card.name) }
    var phone by remember { mutableStateOf(card.phone) }
    var company by remember { mutableStateOf(card.company) }
    var position by remember { mutableStateOf(card.position) }
    var email by remember { mutableStateOf(card.email) }
    var sns by remember { mutableStateOf(card.sns) }

    Scaffold(topBar = { TopAppBar(title = { Text("명함 수정") }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(name, { name = it }, label = { Text("이름") })
            OutlinedTextField(phone, { phone = it }, label = { Text("전화번호") })
            OutlinedTextField(company, { company = it }, label = { Text("회사") })
            OutlinedTextField(position, { position = it }, label = { Text("직책") })
            OutlinedTextField(email, { email = it }, label = { Text("이메일") })
            OutlinedTextField(sns, { sns = it }, label = { Text("그 외") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                InMemoryCardRepository.updateCard(index, BusinessCard(name = name, phone = phone, company = company, position = position, email = email, sns = sns))
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("저장")
            }
        }
    }
}
