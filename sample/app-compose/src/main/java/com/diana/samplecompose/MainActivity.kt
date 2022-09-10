package com.diana.samplecompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diana.appcore.data.entity.User
import com.diana.lib.core.event.StateEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main(viewModel = viewModel)
        }
    }
}

@Composable
fun Main(viewModel: MainViewModel) {
    val userState by viewModel.usersLiveData.observeAsState()
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val data = userState) {
                    is StateEvent.Loading -> RenderText(text = "loading...")
                    is StateEvent.Failure -> {
                        val throwable = data.exception
                        RenderError(throwable = throwable)
                    }
                    is StateEvent.Success -> {
                        RenderUserList(userList = data.data)
                    }
                    else -> RenderText(text = "idle") {
                        viewModel.getUsersWithLiveData(1)
                    }
                }
            }
        }
    }
}

@Composable
fun RenderUserList(userList: List<User>) {
    LazyColumn {
        items(userList) { user -> Text(text = user.email) }
    }
}

@Composable
fun RenderError(throwable: Throwable) {
    Text(text = throwable.localizedMessage)
}

@Composable
fun RenderText(text: String, action: () -> Unit = {}) {
    Text(text = text, modifier = Modifier.clickable { action.invoke() })
}