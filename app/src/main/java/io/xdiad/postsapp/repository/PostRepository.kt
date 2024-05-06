package io.xdiad.postsapp.repository

import io.xdiad.postsapp.network.ApiService

class PostRepository(private val apiService: ApiService) {
    suspend fun getPosts(page: Int, limit: Int) = apiService.getPosts(page, limit)
}