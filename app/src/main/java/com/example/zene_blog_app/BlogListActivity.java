// --- fájl: BlogListActivity.java ---
package com.example.zene_blog_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.zene_blog_app.adapters.BlogAdapter;
import com.example.zene_blog_app.models.BlogPost;
import com.example.zene_blog_app.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class BlogListActivity extends AppCompatActivity {

    private ListView listView;
    private BlogAdapter adapter;
    private List<BlogPost> blogList = new ArrayList<>();
    private Button newPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);

        listView = findViewById(R.id.blog_list_view);
        newPostButton = new Button(this);
        newPostButton.setText("Új bejegyzés létrehozása");
        ((LinearLayout) listView.getParent()).addView(newPostButton);

        adapter = new BlogAdapter(this, blogList);
        listView.setAdapter(adapter);

        FirebaseHelper.getAllBlogPosts(posts -> {
            blogList.clear();
            blogList.addAll(posts);
            adapter.notifyDataSetChanged();
        }, e -> Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(this, BlogEditActivity.class);
            intent.putExtra("BLOG_ID", blogList.get(position).getId());
            startActivity(intent);
        });

        newPostButton.setOnClickListener(v -> {
            startActivity(new Intent(this, BlogEditActivity.class));
        });
    }
}