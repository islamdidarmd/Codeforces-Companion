package com.codeforcesvisualizer.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.codeforcesvisualizer.core.components.CFCard
import com.codeforcesvisualizer.core.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.RankBadge
import com.codeforcesvisualizer.core.components.WidthSpacer
import com.codeforcesvisualizer.core.theme.CFThemeColors
import com.codeforcesvisualizer.shared.domain.entity.User

@Composable
fun UserInfoCard(
    modifier: Modifier = Modifier,
    userInfoUiState: UserInfoUiState
) {
    val colors = CFThemeColors.current

    when {
        userInfoUiState.loading -> {
            CFLoadingIndicator(modifier = modifier.padding(16.dp))
        }

        userInfoUiState.userMessage.isNotBlank() -> {
            Box(
                modifier = modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userInfoUiState.userMessage,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = colors.dim
                    )
                )
            }
        }

        userInfoUiState.user != null -> {
            UserInfoContent(
                user = userInfoUiState.user,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun UserInfoContent(
    user: User,
    modifier: Modifier = Modifier
) {
    val colors = CFThemeColors.current
    val initials = buildString {
        if (user.firstName.isNotBlank()) append(user.firstName.first().uppercase())
        if (user.lastName.isNotBlank()) append(user.lastName.first().uppercase())
        if (isEmpty()) append(user.handle.first().uppercase())
    }

    CFCard(
        title = "identity",
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colors.surface2),
                contentAlignment = Alignment.Center
            ) {
                if (user.avatar.isNotBlank()) {
                    AsyncImage(
                        model = user.avatar,
                        contentDescription = user.handle,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                } else {
                    Text(
                        text = initials,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = colors.violet
                        )
                    )
                }
            }

            WidthSpacer(width = 14.dp)

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${user.firstName} ${user.lastName}".trim().ifBlank { user.handle },
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = colors.fg
                        )
                    )
                    WidthSpacer(width = 8.dp)
                    RankBadge(rating = user.rating, rank = user.rank)
                }

                HeightSpacer(height = 4.dp)

                val details = buildList {
                    add("max ${user.maxRating}")
                    if (user.organization.isNotBlank()) add(user.organization)
                    if (user.country.isNotBlank()) add(user.country)
                }.joinToString(" · ")

                Text(
                    text = details,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = colors.dim
                    )
                )
            }
        }
    }
}
