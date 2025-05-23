package com.example.zene_blog_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button blogButton = findViewById(R.id.btn_view_blogs);
        Button logoutButton = findViewById(R.id.logout_button);
        Button createBlogButton = findViewById(R.id.btn_create_blog);

        blogButton.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, BlogListActivity.class));
        });

        createBlogButton.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, BlogEditActivity.class));
        });

        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}