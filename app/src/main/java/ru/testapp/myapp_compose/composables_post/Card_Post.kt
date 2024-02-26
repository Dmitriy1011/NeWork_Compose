package ru.testapp.myapp_compose.composables_post

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.flow.update
import ru.testapp.myapp_compose.R
import ru.testapp.myapp_compose.viewmodels.ViewModelPosts

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CardPost(
    postId: Long,
    avatar: String,
    authorName: String,
    publishedDate: String,
    content: String,
    imageRes: String,
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeletePost: () -> Unit,
    ownedByMe: Boolean,
    onClickPost: (String) -> Unit,
    onClickEdit: (String) -> Unit,
    viewModelPosts: ViewModelPosts = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

    var isPopupMenuVisible by remember {
        mutableStateOf(false)
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClickPost(postId.toString())
            viewModelPosts.postId.update { postId }
            Log.d("postId", "${viewModelPosts.postId.value}")
        }
    ) {
        Row(
            modifier = modifier.padding(16.dp)
        ) {
            GlideImage(
                model = avatar,
                contentDescription = null,
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                loading = placeholder(R.drawable.baseline_supervised_user_circle_24),
                failure = placeholder(R.drawable.baseline_supervised_user_circle_24)
            )

            Spacer(modifier = modifier.width(16.dp))
            Column(modifier = modifier.weight(1f)) {
                Text(
                    text = authorName,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = modifier.height(4.dp))
                Text(
                    text = publishedDate,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Box(modifier = modifier
                .wrapContentSize(Alignment.TopEnd)) {

                if(ownedByMe) {
                    IconButton(onClick = { isPopupMenuVisible = true }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_more_vert_24),
                            contentDescription = null
                        )
                    }
                }

                DropdownMenu(
                    expanded = isPopupMenuVisible,
                    onDismissRequest = { isPopupMenuVisible = false },
                    offset = DpOffset(x = 20.dp, y = 0.dp)
                )
                {
                    DropdownMenuItem(
                        text = {
                            Text(stringResource(R.string.editPost))
                        },
                        onClick = {
                            viewModelPosts.postContent.update { content }
                            onClickEdit(Uri.encode(content))
                            isPopupMenuVisible = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(stringResource(R.string.removePost))
                        },
                        onClick = {
                            onDeletePost()
                            isPopupMenuVisible = false
                        }
                    )
                }
            }
        }

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = Shapes().small
        ) {
            GlideImage(
                model = imageRes,
                contentDescription = null,
                modifier = modifier
                    .heightIn(max = 200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
        }

        Text(
            text = content,
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


