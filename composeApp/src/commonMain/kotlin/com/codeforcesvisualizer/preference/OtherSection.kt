package com.codeforcesvisualizer.preference

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.current_version
import codeforces_visualizer.composeapp.generated.resources.google_play_store_not_found
import codeforces_visualizer.composeapp.generated.resources.others
import codeforces_visualizer.composeapp.generated.resources.rate_app
import com.codeforcesvisualizer.core.components.HeightSpacer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OtherSection(
    modifier: Modifier = Modifier,
    rateAppHandler: RateAppHandler
) {
    var showStoreError by remember { mutableStateOf(false) }

    Column(modifier.padding(16.dp)) {
        Text(text = stringResource(Res.string.others), style = MaterialTheme.typography.titleMedium)
        HeightSpacer(height = 16.dp)

        TextButton(onClick = {
            showStoreError = !rateAppHandler.openStore()
        }) {
            Text(text = stringResource(Res.string.rate_app))
        }

        if (showStoreError) {
            HeightSpacer(height = 8.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.google_play_store_not_found),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.current_version, rateAppHandler.versionName),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun Preview() {
    OtherSection(
        rateAppHandler = remember {
            object : RateAppHandler {
                override val versionName: String = "2.0.0"
                override fun openStore(): Boolean = false
            }
        }
    )
}