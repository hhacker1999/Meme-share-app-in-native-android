package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    lateinit var memeView: ImageView
    lateinit var shareButton: Button
    lateinit var progressBar: ProgressBar
    lateinit var memeUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        memeView = findViewById(R.id.meme_image)
        progressBar = findViewById(R.id.progress_bar)
        shareButton = findViewById(R.id.share_button)
        loadMeme()
    }
    private fun loadMeme() {
progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                memeUrl = response.getString("url")
                Glide.with(this).load(memeUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(memeView)
            },
            {})
MySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Nigga this meme is cool check this out $memeUrl")
        val chooser = Intent.createChooser(intent, "Share this meme via ...")
        startActivity(chooser)
    }

    fun nextMeme(view: View) {
        loadMeme()
    }
}