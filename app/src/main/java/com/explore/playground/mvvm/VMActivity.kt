package com.explore.playground.mvvm

import androidx.lifecycle.ViewModelProvider
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_model.*

class VMActivity : BaseActivity() {
    private var count = 0
    override fun setLayoutId(): Int {
        return R.layout.activity_view_model
    }

    override fun setInit() {
        val view = ViewModelProvider(this).get(VMViewModel::class.java)

        tvText.text = view.score.toString() + " AND " + count.toString()

        btAdd.setOnClickListener {
            view.score = view.score + 1
            count++
            tvText.text = view.score.toString() + " AND " + count.toString()
        }
    }
}
