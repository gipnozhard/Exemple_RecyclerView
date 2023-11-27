package com.lavrent.exemplerecyclerview.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lavrent.exemplerecyclerview.R
import com.lavrent.exemplerecyclerview.adapter.RecyclerAdapter
import com.lavrent.exemplerecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(getNameCat())
    }

    private fun getNameCat() : List<String> {
        return this.resources.getStringArray(R.array.cat_names).toList()
    }
}