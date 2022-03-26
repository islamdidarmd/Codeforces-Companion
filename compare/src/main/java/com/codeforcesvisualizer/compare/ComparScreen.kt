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
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFAppBar
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.launch

@Composable
fun CompareScreenHandleInput(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    openCompare: () -> Unit,
    viewModel: CompareHandlesViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val handleOne by viewModel.handle1State.collectAsState()
    val handleTwo by viewModel.handle2State.collectAsState()

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
        fun onCompare() {
            if (handleOne.isBlank() || handleTwo.isBlank()) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.hanldles_can_not_be_empty))
                }
                return
            }
            viewModel.compare(handleOne, handleTwo)
            openCompare()
            EventLogger.logScreenView(
                screen = "Compare",
                param = bundleOf(
                    "Handle1" to handleOne,
                    "Handle2" to handleTwo
                )
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
                label = stringResource(R.string.first_handle),
                imeAction = ImeAction.Next,
                onTextChange = { text -> viewModel.onHandle1Change(text) }
            )
            HeightSpacer(height = 16.dp)
            HandleInputField(
                text = handleTwo,
                label = stringResource(R.string.second_handle),
                imeAction = ImeAction.Go,
                onTextChange = { text -> viewModel.onHandle2Change(text) },
                onGo = { onCompare() }
            )
            HeightSpacer(height = 16.dp)
            Button(
                shape = RoundedCornerShape(percent = 50),
                onClick = { onCompare() },
            ) {
                Text(text = stringResource(R.string.compare_users))
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