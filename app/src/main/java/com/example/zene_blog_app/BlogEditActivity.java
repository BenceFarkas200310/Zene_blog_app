
package com.example.zene_blog_app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.zene_blog_app.BlogPost;
import com.example.zene_blog_app.FirebaseHelper;

import java.util.Arrays;

public class BlogEditActivity extends AppCompatActivity {

    private EditText inputTitle, inputContent, inputCategory, inputTags;
    private Switch publishedSwitch;
    private Button saveButton, deleteButton, copyButton;
    private String blogId = null;
    private BlogPost currentPost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_edit);

        inputTitle = findViewById(R.id.input_title);
        inputContent = findViewById(R.id.input_content);
        inputCategory = findViewById(R.id.input_category);
        inputTags = findViewById(R.id.input_tags);
        publishedSwitch = findViewById(R.id.switch_published);
        saveButton = findViewById(R.id.btn_save_blog);
        deleteButton = findViewById(R.id.btn_delete_blog);
        copyButton = findViewById(R.id.btn_copy_clipboard);

        blogId = getIntent().getStringExtra("BLOG_ID");

        if (blogId != null) {
            FirebaseHelper.getBlogPost(blogId, post -> {
                if (post != null) {
                    currentPost = post;
                    inputTitle.setText(post.getTitle());
                    inputContent.setText(post.getContent());
                    inputCategory.setText(post.getCategory());
                    publishedSwitch.setChecked(post.isPublished());
                    inputTags.setText(TextUtils.join(",", post.getTags()));
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }, e -> showToast("Hiba: " + e.getMessage()));
        }

        saveButton.setOnClickListener(v -> {
            try {
                String title = inputTitle.getText().toString().trim();
                String content = inputContent.getText().toString().trim();
                String category = inputCategory.getText().toString().trim();
                String tagInput = inputTags.getText().toString().trim();
                boolean published = publishedSwitch.isChecked();

                if (title.isEmpty() || content.isEmpty()) {
                    showToast("A cím és a tartalom kötelező!");
                    return;
                }

                BlogPost blog = new BlogPost();
                blog.setId(blogId == null ? "" : blogId);
                blog.setTitle(title);
                blog.setContent(content);
                blog.setCategory(category);
                blog.setPublished(published);
                blog.setTags(Arrays.asList(tagInput.split(",")));
                blog.setAuthorId(FirebaseHelper.getCurrentUserId());
                blog.setTimestamp(System.currentTimeMillis());

                if (blogId == null) {
                    FirebaseHelper.createBlogPost(blog, () -> {
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }, e -> showToast("Hiba: " + e.getMessage()));
                } else {
                    FirebaseHelper.updateBlogPost(blog, () -> {
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }, e -> showToast("Hiba: " + e.getMessage()));
                }
            } catch (Exception ex) {
                showToast("Váratlan hiba: " + ex.getMessage());
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (blogId != null) {
                FirebaseHelper.deleteBlogPost(blogId, () -> {
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }, e -> showToast("Hiba: " + e.getMessage()));
            }
        });

        copyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Tartalom", inputContent.getText().toString());
            clipboard.setPrimaryClip(clip);
            showToast("Tartalom vágólapra másolva");
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}