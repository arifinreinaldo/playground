package com.explore.playground.bottomsheetdialog

import android.widget.Button
import android.widget.EditText
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_bottom_sheet.*

class BottomSheetActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_bottom_sheet
    }

    override fun setInit() {
        val layout = layoutInflater.inflate(R.layout.bottom_layout, null)
        val dialog = BottomSheetDialog(ctx)
        dialog.setContentView(layout)

        btShow.setOnClickListener {
            dialog.show()
        }
        layout.findViewById<Button>(R.id.btFilter).setOnClickListener {
            val value = layout.findViewById<EditText>(R.id.etPicker).text.toString()
            showMessage(value)
            dialog.dismiss()
        }
    }
}
