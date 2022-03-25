package com.codeforcesvisualizer.preference

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import kotlinx.coroutines.launch

@Composable
fun OtherSection(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val gpNotFoundText = stringResource(R.string.google_play_store_not_found)

    Column(Modifier.padding(16.dp)) {
        Text(text = "Others", style = MaterialTheme.typography.subtitle1)
        HeightSpacer(height = 16.dp)

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Give Feedback")
        }

        TextButton(onClick = {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.codeforcesvisualizer")
                    )
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(gpNotFoundText)
                }
            }
        }) {
            Text(text = "Rate App")
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Current Version: 1.0.2",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
private fun Preview() {
    OtherSection()
}