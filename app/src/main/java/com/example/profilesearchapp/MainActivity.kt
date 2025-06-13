package com.example.profilesearchapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profilesearchapp.databinding.ActivityMainBinding
import com.example.profilesearchapp.local.AppDatabase
import com.example.profilesearchapp.repository.GHRepoRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: RepoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup ViewModel
        val dao = AppDatabase.getInstance(this).repoDao()
        val repository = GHRepoRepository(dao)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        // Setup RecyclerView
        adapter = RepoAdapter(emptyList()) { repo ->
            openWebView(repo.repoURL)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Fetch data
        viewModel.fetchRepositories()

        // Observe data
        viewModel.repos.observe(this) {
            if(!it.isEmpty())
            {
                adapter.updateData(it)
                binding.tvNoData.visibility=View.GONE
                binding.recyclerView.visibility=View.VISIBLE
            }else{
                binding.tvNoData.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.GONE
            }

        }
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        binding.etSearch.addTextChangedListener {
            val input = it?.toString()?.trim()
            binding.ivClose.visibility = if (!input.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        binding.ivClose.setOnClickListener {
            binding.etSearch.text.clear()
            binding.ivClose.visibility = View.GONE
            viewModel.fetchRepositories()
        }
        // Search logic
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchLocally(query)
            }
        }
    }
    private fun openWebView(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("repo_url", url)
        startActivity(intent)
    }
}