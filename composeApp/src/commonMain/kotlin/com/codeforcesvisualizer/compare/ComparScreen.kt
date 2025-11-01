package com.codeforcesvisualizer.compare

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.*
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFAppBar
import com.codeforcesvisualizer.core.components.HeightSpacer
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CompareScreenHandleInput(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    openCompare: () -> Unit,
    viewModel: CompareHandlesViewModel = viewModel()
) {
    val snackbarHostState = SnackbarHostState()
    val coroutineScope = rememberCoroutineScope()

    val handleOne by viewModel.handle1State.collectAsState()
    val handleTwo by viewModel.handle2State.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(
                title = stringResource(Res.string.compare),
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        val errorMessage = stringResource(Res.string.hanldles_can_not_be_empty)
        fun onCompare() {
            if (handleOne.isBlank() || handleTwo.isBlank()) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                return
            }
            viewModel.compare(handleOne, handleTwo)
            openCompare()
            EventLogger.logScreenView(
                screen = "Compare",
                /*param = bundleOf(
                    "Handle1" to handleOne,
                    "Handle2" to handleTwo
                )*/
            )
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
                label = stringResource(Res.string.first_handle),
                imeAction = ImeAction.Next,
                onTextChange = { text -> viewModel.onHandle1Change(text) }
            )
            HeightSpacer(height = 16.dp)
            HandleInputField(
                text = handleTwo,
                label = stringResource(Res.string.second_handle),
                imeAction = ImeAction.Go,
                onTextChange = { text -> viewModel.onHandle2Change(text) },
                onGo = { onCompare() }
            )
            HeightSpacer(height = 16.dp)
            Button(
                shape = RoundedCornerShape(percent = 50),
                onClick = { onCompare() },
            ) {
                Text(text = stringResource(Res.string.compare_users))
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
        modifier = modifier,
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
    CompareScreenHandleInput(onNavigateBack = {}, openCompare = { })
}