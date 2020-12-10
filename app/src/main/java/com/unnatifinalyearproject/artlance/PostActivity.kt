package com.unnatifinalyearproject.artlance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.unnatifinalyearproject.artlance.models.Posts

private const val TAG = "PostsActivity"
class PostActivity : AppCompatActivity() {

    private lateinit var firestoreDb : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        firestoreDb = FirebaseFirestore.getInstance()
        val postReference = firestoreDb.collection("posts")
        postReference.addSnapshotListener{snapshot, exception ->
            if (exception != null || snapshot == null){
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Posts::class.java)
            for (post in snapshot.documents){
                Log.i(TAG, "Posts $post")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_profile){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}