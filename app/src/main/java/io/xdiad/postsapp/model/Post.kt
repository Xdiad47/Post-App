package io.xdiad.postsapp.model

data class Post(
    val userId: Int,
    val id: Int,
    var title: String,
    var body: String
)
