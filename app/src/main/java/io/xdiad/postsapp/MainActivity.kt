package io.xdiad.postsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.xdiad.postsapp.adapter.PostAdapter
import io.xdiad.postsapp.viewmodel.PostViewModel
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.xdiad.postsapp.network.ApiService
import io.xdiad.postsapp.repository.PostRepository
import io.xdiad.postsapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

  //  private val viewModel: PostViewModel by viewModels()
  private val viewModel: PostViewModel by viewModels {
      ViewModelFactory(PostRepository(ApiService.create()))
  }
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        observeViewModel()


//        adapter = PostAdapter()
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
//
//        viewModel.getPosts(1, 10).observe(this) { posts ->
//            adapter.setPosts(posts)
//        }


    }

    private fun setupRecyclerView() {
        adapter = PostAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && !viewModel.isLoading.value!!) {
                    viewModel.loadPosts()
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.posts.observe(this, { posts ->
            adapter.setPosts(posts)
        })

        viewModel.error.observe(this, { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        })
    }


    private fun enableEdgeToEdge() {
        // Assuming this function sets flags for layout, modify accordingly
        WindowInsetsCompat.Type.systemBars()
    }
}