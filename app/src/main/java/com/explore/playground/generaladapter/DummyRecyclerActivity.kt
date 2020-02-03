package com.explore.playground.generaladapter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.explore.playground.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_dummy_recycler.*

class DummyRecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy_recycler)

        val adapter = DummyRecylerAdapter(this)
        val dummies = mutableListOf<Dummy>()
        val dummy = Dummy("1", "Belajar")
        val ctx: Context = this
        dummies.add(dummy)
        rvGeneral.layoutManager = LinearLayoutManager(this)
        rvGeneral.adapter = adapter
        adapter.setItem(dummies)
        adapter.listen = object :
            DummyRecylerAdapter.DummyListener {
            override fun onItemClicked(item: Dummy) {
                Toast.makeText(ctx, "Clicked ${item.desc}", Toast.LENGTH_SHORT).show()
            }
        }
        val sheet = BottomSheetBehavior.from(llFilter)
        sheet.state = BottomSheetBehavior.STATE_HIDDEN
        btFilter.setOnClickListener {
            if (sheet.state == BottomSheetBehavior.STATE_HIDDEN) {
                sheet.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                sheet.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
        sheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
            }
        })
    }
}
