package com.explore.playground.localization

import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.yariksoffice.lingver.Lingver
import kotlinx.android.synthetic.main.activity_localization.*

class LocalizationActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_localization
    }

    override fun setInit() {
        btChange.setOnClickListener {
            if (lang.equals("")) {
                lang = "in"
            } else if (lang.equals("in")) {
                lang = ""
            }
            Lingver.getInstance().setLocale(this, lang)
            recreate()
        }
    }
}
