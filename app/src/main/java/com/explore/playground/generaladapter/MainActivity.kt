package com.explore.playground.generaladapter

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.explore.playground.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = DummyRecylerAdapter(this)
        val dummies = mutableListOf<Dummy>()
        val dummy = Dummy("1", "Belajar")
        val ctx: Context = this
        dummies.add(dummy)
        rvGeneral.layoutManager = LinearLayoutManager(this)
        rvGeneral.adapter = adapter
        adapter.setItem(dummies)
        adapter.listen = object : DummyRecylerAdapter.DummyListener {
            override fun onItemClicked(item: Dummy) {
                Toast.makeText(ctx, "Clicked ${item.desc}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
