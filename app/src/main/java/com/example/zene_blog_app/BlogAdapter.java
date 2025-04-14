package com.example.zene_blog_app.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.example.zene_blog_app.R;
import com.example.zene_blog_app.models.BlogPost;

import java.util.List;

public class BlogAdapter extends BaseAdapter {
    private final Context context;
    private final List<BlogPost> blogList;

    public BlogAdapter(Context context, List<BlogPost> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @Override
    public int getCount() {
        return blogList.size();
    }

    @Override
    public Object getItem(int position) {
        return blogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.blog_list_item, parent, false);
        TextView title = view.findViewById(R.id.blog_title);
        TextView excerpt = view.findViewById(R.id.blog_excerpt);

        BlogPost post = blogList.get(position);
        title.setText(post.getTitle());
        excerpt.setText(post.getContent().length() > 50
                ? post.getContent().substring(0, 50) + "..."
                : post.getContent());

        return view;
    }
}
