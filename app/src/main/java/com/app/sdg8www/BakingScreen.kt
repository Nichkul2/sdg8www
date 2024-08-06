package com.app.sdg8www

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

val AiRecommendations = arrayOf(
    "Comment on my interview responses",
    "Recommend me beneficial skills",
    "Recommend me guideline for more study"
)

@Composable
fun BakingScreen(viewModel: BakingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }
    val promptPlaceholder = stringResource(id = R.string.prompt_placeholder)
    var prompt by rememberSaveable { mutableStateOf(promptPlaceholder) }
    var isPromptFocused by remember { mutableStateOf(false) }
    val teal200 = colorResource(id = R.color.teal_200)
    val black = colorResource(id = R.color.black)
    var aiDialog by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    lateinit var database: DatabaseReference

    LaunchedEffect(Unit) {
        // Get the logged-in user's email
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            userEmail = user.email ?: "unknown_user"
            database = Firebase.database.reference.child("Interview").child(userEmail.replace(".", ","))

            // Load conversation from database
            database.get().addOnSuccessListener { dataSnapshot ->
                val conversations = dataSnapshot.children.mapNotNull { it.getValue(ConversationEntry::class.java) }
                val conversationText = conversations.joinToString(separator = "\n") {
                    "User: ${it.userMessage}\nBot: ${it.botResponse}"
                }
                aiDialog = conversationText
            }.addOnFailureListener { exception ->
                aiDialog = "Failed to load conversations"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            AiRecommendations.forEachIndexed { index, AiRecommendation ->
                Text(
                    text = AiRecommendation,
                    color = if (selectedIndex == index) teal200 else black,
                    fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(8.dp)
                        .requiredWidth(250.dp)
                        .border(
                            BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            MaterialTheme.shapes.small
                        )
                        .clickable {
                            selectedIndex = index
                        }
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        TextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text(stringResource(id = R.string.label_prompt)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .onFocusChanged { focusState ->
                    isPromptFocused = focusState.isFocused
                    if (!isPromptFocused && prompt.isEmpty()) {
                        prompt = promptPlaceholder
                    }
                },
            placeholder = { if (!isPromptFocused && prompt == promptPlaceholder) Text(text = promptPlaceholder) }
        )

        Button(onClick = {
            if (selectedIndex >= 0) {
                val selectedAiRecommendation = AiRecommendations[selectedIndex]
                viewModel.sendPrompt(selectedAiRecommendation, prompt, aiDialog)
            }
        }) {
            Text(text = stringResource(id = R.string.action_go))
        }

        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> Text((uiState as UiState.Success).outputText)
            is UiState.Error -> Text((uiState as UiState.Error).errorMessage)
            else -> Text(text = stringResource(id = R.string.results_placeholder))
        }
    }
}

data class ConversationEntry(val userMessage: String = "", val botResponse: String = "")
