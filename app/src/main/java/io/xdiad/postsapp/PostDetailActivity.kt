package io.xdiad.postsapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val postId = intent.getIntExtra("postId", 0)
        val postTitle = intent.getStringExtra("postTitle") ?: ""
        val postBody = intent.getStringExtra("postBody") ?: ""

        findViewById<TextView>(R.id.textViewPostId).text = "Post ID: $postId"
        findViewById<TextView>(R.id.textViewPostTitle).text = postTitle
        findViewById<TextView>(R.id.textViewPostBody).text = postBody

    }
}