package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.Chip
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import com.codeforcesvisualizer.core.data.components.WidthSpacer
import com.codeforcesvisualizer.core.data.utils.convertTimeStampToDateString
import com.codeforcesvisualizer.domain.entity.User

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
    Column(modifier = modifier.padding(12.dp)) {
        Text(
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        HeightSpacer(height = 8.dp)

        Row(verticalAlignment = Alignment.CenterVertically) {
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
                    icon = Icons.Default.OnlinePrediction
                )
            }
        }
    }
}

@Preview
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
                avatar = "https://userpic.codeforces.org/314660/title/8dde589b372911ff.jpg",
                titlePhoto = "https://userpic.codeforces.org/314660/title/8dde589b372911ff.jpg"
            )
        )
    )
}