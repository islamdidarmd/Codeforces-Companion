package com.codeforcesvisualizer.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.components.CFCard
import com.codeforcesvisualizer.core.components.VerdictDonut
import com.codeforcesvisualizer.shared.domain.entity.UserStatus

@Composable
fun VerdictCard(
    userStatusList: List<UserStatus>,
    modifier: Modifier = Modifier
) {
    val verdictMap = remember(userStatusList) {
        val map = mutableMapOf<String, Int>()
        userStatusList.forEach { status ->
            val shortVerdict = minifyVerdict(status.verdict)
            map[shortVerdict] = (map[shortVerdict] ?: 0) + 1
        }
        map.toMap()
    }

    CFCard(
        title = "verdicts",
        modifier = modifier
    ) {
        VerdictDonut(data = verdictMap)
    }
}

private fun minifyVerdict(verdict: String): String {
    return when (verdict) {
        "OK" -> "AC"
        "COMPILATION_ERROR" -> "CE"
        "RUNTIME_ERROR" -> "RE"
        "WRONG_ANSWER" -> "WA"
        "PRESENTATION_ERROR" -> "PE"
        "TIME_LIMIT_EXCEEDED" -> "TLE"
        "MEMORY_LIMIT_EXCEEDED" -> "MLE"
        "IDLENESS_LIMIT_EXCEEDED" -> "ILE"
        "SECURITY_VIOLATED" -> "SV"
        "INPUT_PREPARATION_CRASHED" -> "IPC"
        else -> verdict
    }
}
