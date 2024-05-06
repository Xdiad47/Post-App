package io.xdiad.postsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import io.xdiad.postsapp.model.Post
import io.xdiad.postsapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.*

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    private val currentPage = MutableLiveData(1)
    private val _isLoading = MutableLiveData(false)
    private val _error = MutableLiveData<String?>()

    val posts: LiveData<List<Post>> = _posts
    val isLoading: LiveData<Boolean> = _isLoading
    val error: LiveData<String?> = _error

    init {
        loadPosts()
    }

    fun loadPosts() {
        if (_isLoading.value == true) {
            return
        }
        _isLoading.value = true


        viewModelScope.launch {
            val response = repository.getPosts(currentPage.value ?: 1, 10)
            if (response.isSuccessful) {
                response.body()?.let {
                    val enhancedPosts = it.map { post ->
                        // Perform heavy computation on each post
                        post.title = performHeavyComputation(post.title)
                        post.body = performHeavyComputation(post.body)
                        post
                    }
                    val currentPosts = _posts.value ?: emptyList()
                    _posts.value = currentPosts + enhancedPosts
                    currentPage.value = (currentPage.value ?: 1) + 1
                }
            } else {
                val errorMessage = "Failed to load posts: ${response.errorBody()?.string() ?: "Unknown error"}"
                _error.value = errorMessage
                Log.e("PostViewModel", errorMessage)
            }
            _isLoading.value = false
        }

    }

    private fun performHeavyComputation(data: String): String {
        val startTime = System.nanoTime()
        // Simulate heavy computation by reversing the string (example)
        val result = data.reversed()
        val endTime = System.nanoTime()
        Log.d("Computation", "Time taken for $data: ${endTime - startTime} ns")
        return result
    }

    fun clearError() {
        _error.value = null
    }




}

//Correct data
//class PostViewModel(private val repository: PostRepository) : ViewModel() {
//    fun getPosts(page: Int, limit: Int) = liveData(Dispatchers.IO) {
//        val response = repository.getPosts(page, limit)
//        if (response.isSuccessful) {
//            response.body()?.let { emit(it) }
//        }
//    }
//}