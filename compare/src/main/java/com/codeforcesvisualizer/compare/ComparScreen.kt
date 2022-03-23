package com.codeforcesvisualizer.compare

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.components.CFAppBar
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import kotlinx.coroutines.launch

@Composable
fun CompareScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onCompare: (String, String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            CFAppBar(
                title = stringResource(R.string.compare),
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        var handleOne by remember { mutableStateOf("") }
        var handleTwo by remember { mutableStateOf("") }

        fun onCompare() {
            if (handleOne.isBlank() || handleTwo.isBlank()) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.hanldles_can_not_be_empty))
                }
                return
            }
            onCompare(handleOne, handleTwo)
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HandleInputField(
                text = handleOne,
                label = stringResource(R.string.first_handle),
                imeAction = ImeAction.Next,
                onTextChange = { text -> handleOne = text }
            )
            HeightSpacer(height = 16.dp)
            HandleInputField(
                text = handleTwo,
                label = stringResource(R.string.second_handle),
                imeAction = ImeAction.Go,
                onTextChange = { text -> handleTwo = text },
                onGo = { onCompare() }
            )
            HeightSpacer(height = 16.dp)
            Button(
                shape = RoundedCornerShape(percent = 50),
                onClick = { onCompare() },
            ) {
                Text(text = "Compare Users")
            }
        }
    }
}

@Composable
private fun HandleInputField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    imeAction: ImeAction,
    onTextChange: (String) -> Unit,
    onGo: (() -> Unit)? = null
) {
    OutlinedTextField(
        label = { Text(label) },
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                onGo?.invoke()
            }
        )
    )
}

@Preview
@Composable
private fun Preview() {
    CompareScreen(onNavigateBack = {}, onCompare = { _, _ -> })
}