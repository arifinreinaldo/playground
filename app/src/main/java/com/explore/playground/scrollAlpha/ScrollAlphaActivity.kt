package com.explore.playground.scrollAlpha

import androidx.core.widget.NestedScrollView
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scroll_alpha.*

class ScrollAlphaActivity : BaseActivity() {
    private var carouselHeight: Int = 0
    override fun setLayoutId(): Int {
        return R.layout.activity_scroll_alpha
    }

    override fun setInit() {
        header.background.alpha = 0
        NSProduct.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            carouselHeight = ivCarousel.height
            header.background.alpha = getAlpha(scrollY)
        }
    }

    private fun getAlpha(scrollY: Int): Int {
        var alpha = ((scrollY.toFloat() / carouselHeight) * 255).toInt()
        return if (alpha > 255) 255 else alpha
    }
}