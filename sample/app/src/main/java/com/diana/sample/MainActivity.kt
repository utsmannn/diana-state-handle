package com.diana.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diana.lib.subscriber.StateEventSubscriber
import com.diana.appcore.data.entity.User
import com.diana.lib.subscriber.createStateEventListener
import com.diana.sample.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*viewModel.subscribeUserManager(subscriber())
        binding.mainText.setOnClickListener {
            viewModel.getUsersWithManager(1)
        }*/

        viewModel.usersLiveData.observe(this) {
            createStateEventListener(it, subscriber())
        }
        binding.mainText.setOnClickListener {
            viewModel.getUsersWithLiveData(1)
        }
    }

    private fun subscriber() = object : StateEventSubscriber<List<User>> {
        override fun onIdle() {
            binding.mainText.text = "idle"
        }

        override fun onLoading() {
            binding.mainText.text = "loading"
        }

        override fun onFailure(throwable: Throwable) {
            binding.mainText.text = throwable.message
        }

        override fun onSuccess(data: List<User>) {
            binding.mainText.text = data.map { it.email }.toString()
        }

    }
}