package com.example.zene_blog_app;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.zene_blog_app.models.BlogPost;
import com.example.zene_blog_app.FirebaseHelper;

public class BlogEditActivity extends AppCompatActivity {

    private EditText inputTitle, inputContent;
    private Button saveButton, deleteButton;
    private String blogId = null;
    private BlogPost currentPost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_edit);

        inputTitle = findViewById(R.id.input_title);
        inputContent = findViewById(R.id.input_content);
        saveButton = findViewById(R.id.btn_save_blog);
        deleteButton = findViewById(R.id.btn_delete_blog);

        blogId = getIntent().getStringExtra("BLOG_ID");

        if (blogId != null) {
            FirebaseHelper.getBlogPost(blogId, post -> {
                if (post != null) {
                    currentPost = post;
                    inputTitle.setText(post.getTitle());
                    inputContent.setText(post.getContent());
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }, e -> Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }

        saveButton.setOnClickListener(v -> {
            String title = inputTitle.getText().toString().trim();
            String content = inputContent.getText().toString().trim();
            String authorId = FirebaseHelper.getCurrentUserId();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Minden mező kötelező!", Toast.LENGTH_SHORT).show();
                return;
            }

            BlogPost blog = new BlogPost(
                    blogId == null ? "" : blogId,
                    title,
                    content,
                    authorId,
                    System.currentTimeMillis()
            );

            if (blogId == null) {
                FirebaseHelper.createBlogPost(blog, () -> {
                    Toast.makeText(this, "Mentve!", Toast.LENGTH_SHORT).show();
                    finish();
                }, e -> Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                FirebaseHelper.updateBlogPost(blog, () -> {
                    Toast.makeText(this, "Frissítve!", Toast.LENGTH_SHORT).show();
                    finish();
                }, e -> Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (blogId != null) {
                FirebaseHelper.deleteBlogPost(blogId, () -> {
                    Toast.makeText(this, "Törölve!", Toast.LENGTH_SHORT).show();
                    finish();
                }, e -> Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}