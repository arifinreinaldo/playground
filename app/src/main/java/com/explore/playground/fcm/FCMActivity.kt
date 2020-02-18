package com.explore.playground.fcm

import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.explore.playground.utils.LocalValue
import kotlinx.android.synthetic.main.activity_fcm.*

class FCMActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_fcm
    }

    override fun setInit() {
        tvFCM.setText(LocalValue.fcm)
    }
}
