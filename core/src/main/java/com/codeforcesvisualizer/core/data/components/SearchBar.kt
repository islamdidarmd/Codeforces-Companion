package com.codeforcesvisualizer.core.data.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String = "",
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit,
    onClearText: () -> Unit,
    onNavigateBack: () -> Unit,
    result: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            searchText = searchText,
            placeholderText = placeholderText,
            onSearchTextChanged = onSearchTextChanged,
            onClearText = onClearText,
            onNavigateBack = onNavigateBack
        )
        result()
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
internal fun SearchBar(
    searchText: String = "",
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit,
    onClearText: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
                    .onFocusChanged { showClearButton = it.isFocused }
                    .focusRequester(focusRequester),
                value = searchText,
                onValueChange = onSearchTextChanged,
                placeholder = { Text(text = placeholderText) },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = showClearButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = onClearText) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    SearchBar(
        onSearchTextChanged = {},
        onClearText = { /*TODO*/ },
        onNavigateBack = { /*TODO*/ }) {

    }
}