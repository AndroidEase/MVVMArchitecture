package com.example.androidmvvmarchitecture_1.ui.screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmvvmarchitecture_1.ui.adapter.UserAdapter
import com.example.androidmvvmarchitecture_1.ui.adapter.UserLoadStateAdapter
import com.example.androidmvvmarchitecture_1.ui.viewmodel.GitHubUserViewModel
import com.example.androidmvvmarchitecture_1.utils.Resource
import com.example.androidmvvmrchitecture_1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GitHubUserViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = UserLoadStateAdapter { adapter.retry() }
        )

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // Optional: handle before text changed
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Optional: handle text being changed
            }

            override fun afterTextChanged(editable: Editable?) {
                val query = editable.toString().trim()
                searchUsers1(query) // Call the method to search users
            }
        })

        searchUsers1("c")

        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error

            errorState?.let {
                Toast.makeText(this, "Error: ${it.error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun searchUsers1(query: String) {
        lifecycleScope.launch {
            viewModel.searchUsers(query).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}