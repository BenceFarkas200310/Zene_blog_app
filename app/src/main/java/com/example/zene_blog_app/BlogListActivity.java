
package com.example.zene_blog_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.zene_blog_app.BlogAdapter;
import com.example.zene_blog_app.FirebaseHelper;
import com.example.zene_blog_app.BlogPost;

import java.util.ArrayList;
import java.util.List;

public class BlogListActivity extends AppCompatActivity {

    private ListView listView;
    private BlogAdapter adapter;
    private List<BlogPost> blogList = new ArrayList<>();
    private Button newPostButton;

    private void loadBlogPosts() {
        FirebaseHelper.getVisiblePosts(posts -> {
            BlogAdapter adapter = new BlogAdapter(this, posts);
            listView.setAdapter(adapter);
        }, e -> {
            Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

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

        loadBlogPosts();

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(this, BlogEditActivity.class);
            intent.putExtra("BLOG_ID", blogList.get(position).getId());
            startActivity(intent);
        });

        newPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BlogEditActivity.class);
            startActivityForResult(intent, 1001);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBlogPosts();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            loadBlogPosts();
        }
    }
}