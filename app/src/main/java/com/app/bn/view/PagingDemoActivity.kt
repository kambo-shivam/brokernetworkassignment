package com.app.bn.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.bn.R
import com.app.bn.adapter.UserAdapter
import com.app.bn.viewModel.PagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PagingDemoActivity : AppCompatActivity() {

    private val viewModel: PagingViewModel by viewModels()

    @Inject
    lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        rvCharacters.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.usersFlow.collectLatest { adapter.submitData(it) }
        }
    }
}