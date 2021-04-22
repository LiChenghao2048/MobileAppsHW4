package com.example.mobileappshw4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class DreamActivity : AppCompatActivity(){

    private lateinit var textViewTitle: TextView
    private lateinit var textViewEmotion: TextView
    private lateinit var textViewContent: TextView
    private lateinit var textViewReflection: TextView
    private lateinit var buttonDelete: Button
    private lateinit var buttonUpdate: Button

    private val dreamViewModel: DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)

        textViewTitle = findViewById(R.id.textView_dreamTitle)
        textViewEmotion = findViewById(R.id.textView_dreamEmotion)
        textViewContent = findViewById(R.id.textView_dreamContent)
        textViewReflection = findViewById(R.id.textView_dreamReflection)

        buttonDelete = findViewById(R.id.button_delete)
        buttonUpdate = findViewById(R.id.button_update)

        var receivedIntent: Intent = intent
        var id = receivedIntent.getStringExtra("id")?.toInt()!!

        dreamViewModel.getDream(id)
        dreamViewModel.returnedDream.observe(this, Observer {
            var dream: Dream? = dreamViewModel.returnedDream.value
            if (dream != null) {
                textViewTitle.text = dream.title
                textViewEmotion.text = dream.emotion
                textViewContent.text = dream.content
                textViewReflection.text = dream.reflection
            }
        })

        buttonDelete.setOnClickListener {
            dreamViewModel.delete(id)
            finish()
        }

        buttonUpdate.setOnClickListener {
            val intent = Intent(this@DreamActivity, AddActivity::class.java)
            intent.putExtra("update", true)
            intent.putExtra("title", textViewTitle.text)
            intent.putExtra("content", textViewContent.text)
            intent.putExtra("reflection", textViewReflection.text)
            intent.putExtra("emotion", textViewEmotion.text)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }
    }

}