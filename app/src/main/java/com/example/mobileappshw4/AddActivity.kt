package com.example.mobileappshw4

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var editTextReflection: EditText
    private lateinit var spinnerEmotion: Spinner
    private lateinit var buttonSave: Button

    private val dreamViewModel: DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    } // call the one and only repository we created in the Dream Application class
    // so that we are not creating multiple instances of the repository in our app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editTextTitle = findViewById(R.id.editText_title)
        editTextContent = findViewById(R.id.editText_content)
        editTextReflection = findViewById(R.id.editText_reflection)

        spinnerEmotion = findViewById(R.id.spinner_emotion)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.emotion_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerEmotion.adapter = adapter
        }

        var receivedIntent: Intent = intent
        if (receivedIntent.getBooleanExtra("update", false)) {
            editTextTitle.setText(receivedIntent.getStringExtra("title"))
            editTextContent.setText(receivedIntent.getStringExtra("content"))
            editTextReflection.setText(receivedIntent.getStringExtra("reflection"))
            val emotionArray: Array<out String> = applicationContext.resources.getStringArray(R.array.emotion_array)
            var position: Int = 0
            for (i in emotionArray) {
                if (i == receivedIntent.getStringExtra("emotion")) {
                    break
                }
                position += 1
            }
            spinnerEmotion.setSelection(position)
        }

        buttonSave = findViewById(R.id.button_save)
        // catch user input error in front end
        buttonSave.setOnClickListener {

            if (receivedIntent.getBooleanExtra("update", false)) {
                dreamViewModel.update(editTextTitle.text.toString(), editTextContent.text.toString(),
                    editTextReflection.text.toString(), spinnerEmotion.selectedItem.toString(),
                    receivedIntent.getIntExtra("id", 0))
                finish()
            } else {
                if (TextUtils.isEmpty(editTextTitle.text) or TextUtils.isEmpty(editTextContent.text)
                    or TextUtils.isEmpty(editTextReflection.text)
                ) {
                    toastError("Missing fields")
                } else {
                    // I would grab the text, make it into a Dream type
                    // call the insert function to insert
                    val dream = Dream(
                        editTextTitle.text.toString(), editTextContent.text.toString(),
                        editTextReflection.text.toString(), spinnerEmotion.selectedItem.toString()
                    )
                    dreamViewModel.insert(dream)
                    finish()

                    // if we would would be deleting
                    // dreamViewModel.delete(dream.id)
                }
            }
        }

    }

    private fun toastError(text:String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}