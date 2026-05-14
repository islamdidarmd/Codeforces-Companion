package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.profile
import coil3.compose.AsyncImage
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.Chip
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.WidthSpacer
import com.codeforcesvisualizer.core.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.shared.domain.entity.User
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserInfoCard(
    modifier: Modifier = Modifier,
    userInfoUiState: UserInfoUiState
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 100.dp
            )
    ) {
        when {
            userInfoUiState.loading -> {
                Center {
                    CircularProgressIndicator()
                }
            }

            userInfoUiState.userMessage.isNotBlank() -> {
                Center {
                    Text(text = userInfoUiState.userMessage)
                }
            }

            userInfoUiState.user != null -> UserInfoCard(user = userInfoUiState.user)
        }
    }
}

@Composable
private fun UserInfoCard(
    modifier: Modifier = Modifier,
    user: User
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Text(
            text = stringResource(Res.string.profile),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape),
                model = user.avatar,
                contentDescription = user.firstName,
                contentScale = ContentScale.Crop,
            )
            WidthSpacer(width = 8.dp)
            Column {
                Text(text = "${user.firstName} ${user.lastName}")
                HeightSpacer(height = 8.dp)
                if (user.city.isNotBlank() || user.country.isNotBlank()) {
                    Chip(
                        label = "${user.city.plus(",")} ${user.country}",
                        icon = Icons.Default.Home
                    )
                }
                HeightSpacer(height = 2.dp)
                if (user.organization.isNotBlank()) {
                    Chip(
                        label = user.organization,
                        icon = Icons.Default.Work
                    )
                }
                HeightSpacer(height = 2.dp)
                Chip(
                    label = "${user.friendOfCount} friends",
                    icon = Icons.Default.People
                )

                HeightSpacer(height = 2.dp)
                Chip(
                    label = user.rank,
                    icon = Icons.Default.MilitaryTech
                )

                HeightSpacer(height = 2.dp)
                Chip(
                    label = "${user.rating}",
                    icon = Icons.Default.ShowChart
                )

                HeightSpacer(height = 2.dp)
                Chip(
                    label = user.lastOnlineTimeSeconds.convertTimeStampToDateString(),
                    icon = Icons.Default.RemoveRedEye
                )
            }
        }
    }
}

@Preview()
@Composable
private fun Preview() {
    UserInfoCard(
        userInfoUiState = UserInfoUiState(
            loading = false,
            userMessage = "",
            user = User(
                handle = "handle",
                email = "email",
                firstName = "firstName",
                lastName = "lastName",
                country = "country",
                city = "city",
                organization = "organization",
                contribution = 0,
                rank = "Rank",
                rating = 100,
                maxRank = "maxRank",
                maxRating = 1600,
                lastOnlineTimeSeconds = 0,
                registrationTimeSeconds = 0,
                friendOfCount = 0,
                avatar = "https://userpic.codeforces.org/314660/avatar/40f7521c050c5727.jpg",
                titlePhoto = "https://userpic.codeforces.org/314660/title/8dde589b372911ff.jpg"
            )
        )
    )
}