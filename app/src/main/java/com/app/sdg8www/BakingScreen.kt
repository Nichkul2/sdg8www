package com.app.sdg8www

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
//import androidx.compose.foundation.layout.requiredSize
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.itemsIndexed
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

val jobTitles = arrayOf(
    "Teacher / Consultant Job",
    "Computer Engineering Job",
    "Business Analyst Job"
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
            jobTitles.forEachIndexed { index, jobTitle ->
                Text(
                    text = jobTitle,
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
                val selectedJobTitle = jobTitles[selectedIndex]
                viewModel.sendPrompt(selectedJobTitle, prompt)
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