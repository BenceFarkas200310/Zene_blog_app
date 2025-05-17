package com.example.zene_blog_app;

import com.example.zene_blog_app.BlogPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.List;
import java.util.function.Consumer;

public class FirebaseHelper {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final CollectionReference blogRef = db.collection("blogs");

    public static String getCurrentUserId() {
        return FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getUid() : null;
    }

    public static void getAllBlogPosts(Consumer<List<BlogPost>> onSuccess, Consumer<Exception> onFailure) {
        blogRef.orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<BlogPost> list = snapshot.toObjects(BlogPost.class);
                    onSuccess.accept(list);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public static void getPublishedPosts(Consumer<List<BlogPost>> onSuccess, Consumer<Exception> onFailure) {
        blogRef.whereEqualTo("published", true)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<BlogPost> list = snapshot.toObjects(BlogPost.class);
                    onSuccess.accept(list);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public static void getPostsByCategory(String category, Consumer<List<BlogPost>> onSuccess, Consumer<Exception> onFailure) {
        blogRef.whereEqualTo("category", category)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<BlogPost> list = snapshot.toObjects(BlogPost.class);
                    onSuccess.accept(list);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public static void searchPostsByTitlePrefix(String prefix, Consumer<List<BlogPost>> onSuccess, Consumer<Exception> onFailure) {
        blogRef.orderBy("title")
                .startAt(prefix)
                .endAt(prefix + "\uf8ff")
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<BlogPost> list = snapshot.toObjects(BlogPost.class);
                    onSuccess.accept(list);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public static void getVisiblePosts(Consumer<List<BlogPost>> onSuccess, Consumer<Exception> onFailure) {
        String currentUserId = getCurrentUserId();

        blogRef
                .where(
                        Filter.or(
                                Filter.equalTo("published", true),
                                Filter.equalTo("authorId", currentUserId)
                        )
                )
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<BlogPost> list = snapshot.toObjects(BlogPost.class);
                    onSuccess.accept(list);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public static void getBlogPost(String id, Consumer<BlogPost> onSuccess, Consumer<Exception> onFailure) {
        blogRef.document(id).get()
                .addOnSuccessListener(doc -> {
                    BlogPost post = doc.toObject(BlogPost.class);
                    onSuccess.accept(post);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public static void createBlogPost(BlogPost post, Runnable onSuccess, Consumer<Exception> onFailure) {
        DocumentReference doc = blogRef.document();
        post.setId(doc.getId());
        doc.set(post)
                .addOnSuccessListener(r -> onSuccess.run())
                .addOnFailureListener(onFailure::accept);
    }

    public static void updateBlogPost(BlogPost post, Runnable onSuccess, Consumer<Exception> onFailure) {
        blogRef.document(post.getId())
                .set(post)
                .addOnSuccessListener(r -> onSuccess.run())
                .addOnFailureListener(onFailure::accept);
    }

    public static void deleteBlogPost(String id, Runnable onSuccess, Consumer<Exception> onFailure) {
        blogRef.document(id)
                .delete()
                .addOnSuccessListener(r -> onSuccess.run())
                .addOnFailureListener(onFailure::accept);
    }
}