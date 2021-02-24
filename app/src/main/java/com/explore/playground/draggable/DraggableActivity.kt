package com.explore.playground.draggable

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.activity_dragable.*


class DraggableActivity : AppCompatActivity() {
    private val CLICK_DRAG_TOLERANCE =
        10f // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.


    private var downRawX = 0f
    private var downRawY = 0f
    private var dX = 0f
    private var dY = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dragable)

        ivImage.load("https://static.wikia.nocookie.net/pokemon-fano/images/6/6f/Poke_Ball.png")
        ivImage.setOnTouchListener { view, motionEvent ->
            val action: Int = motionEvent.getAction()
            if (action == MotionEvent.ACTION_DOWN) {
                downRawX = motionEvent.getRawX()
                downRawY = motionEvent.getRawY()
                dX = view.x - downRawX
                dY = view.y - downRawY
                true
            } else if (action == MotionEvent.ACTION_MOVE) {
                val viewWidth = view.width
                val viewHeight = view.height
                val viewParent: View = view.parent as View
                val parentWidth: Int = viewParent.getWidth()
                val parentHeight: Int = viewParent.getHeight()
                var newX: Float = motionEvent.getRawX() + dX
                newX =
                    Math.max(0f, newX) // Don't allow the FAB past the left hand side of the parent
                newX = Math.min(
                    (parentWidth - viewWidth).toFloat(),
                    newX
                ) // Don't allow the FAB past the right hand side of the parent
                var newY: Float = motionEvent.getRawY() + dY
                newY = Math.max(0f, newY) // Don't allow the FAB past the top of the parent
                newY = Math.min(
                    (parentHeight - viewHeight).toFloat(),
                    newY
                ) // Don't allow the FAB past the bottom of the parent
                view.animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start()
                true // Consumed
            } else if (action == MotionEvent.ACTION_UP) {
                val upRawX: Float = motionEvent.getRawX()
                val upRawY: Float = motionEvent.getRawY()
                val upDX: Float = upRawX - downRawX
                val upDY: Float = upRawY - downRawY
                if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                    performClick()
                } else { // A drag
                    true // Consumed
                }
            } else {
                super.onTouchEvent(motionEvent)
            }
        }
    }

    private fun performClick(): Boolean {
        Toast.makeText(this, "hoi", Toast.LENGTH_SHORT).show()
        return false
    }
}