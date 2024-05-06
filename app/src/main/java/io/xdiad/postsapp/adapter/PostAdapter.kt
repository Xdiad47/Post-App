package io.xdiad.postsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.xdiad.postsapp.PostDetailActivity
import io.xdiad.postsapp.databinding.ItemPostBinding
import io.xdiad.postsapp.model.Post


class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var posts: List<Post> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    fun setPosts(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }

    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.textViewTitle.text = post.title
            binding.textViewBody.text = post.body

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, PostDetailActivity::class.java).apply {
                    putExtra("postId", post.id)
                    putExtra("postTitle", post.title)
                    putExtra("postBody", post.body)
                }
                itemView.context.startActivity(intent)

            }
        }
    }
}


/*class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var posts: List<Post> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    fun setPosts(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }


    class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.textViewTitle.text = post.title
            binding.textViewBody.text = post.body
        }
    }
}*/