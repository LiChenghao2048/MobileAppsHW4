package com.example.mobileappshw4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAdd: Button

    private val dreamViewModel: DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        buttonAdd = findViewById(R.id.button_add)

        val adapter = DreamListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        dreamViewModel.allDreams.observe(this, Observer {
            // update the cashed copy of tasks in the adapter to it
            dreams -> dreams?.let {
                adapter.submitList(it)
            }
        })

        buttonAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            intent.putExtra("update", false)
            startActivity(intent)
        }
    }
}