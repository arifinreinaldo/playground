package com.explore.playground.dialogpicker

import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_picker.*
import java.util.*

class PickerActivity : BaseActivity() {
    private lateinit var datePicker: DatePickerFragment
    private lateinit var timePicker: TimePickerFragment
    override fun setLayoutId(): Int {
        return R.layout.activity_picker
    }

    override fun setInit() {
        datePicker = DatePickerFragment(act)
        timePicker = TimePickerFragment(act)
        etDate.setOnClickListener {
            datePicker.showDate(etDate, supportFragmentManager, "")
        }
        etTime.setOnClickListener {
            timePicker.showTime(etTime, supportFragmentManager, "")
        }
        etTime2.setOnClickListener {
            showTimePicker(etTime2, supportFragmentManager)
        }
    }

}

fun showTimePicker(editText: EditText, fragment: FragmentManager) {
    var hour = 0
    var minute = 0
    var timeStr = editText.text.toString()
    if (timeStr.isNotEmpty()) {
        var splitDate = timeStr.split(":")
        hour = splitDate[0].toInt()
        minute = splitDate[1].toInt()
    } else {
        val c = Calendar.getInstance()
        hour = c.get(Calendar.HOUR_OF_DAY)
        minute = c.get(Calendar.MINUTE)

    }
    var timePickerFragment = TimePickerDialog.newInstance(
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute, second ->
            editText.setText(String.format("%02d:%02d", hourOfDay, minute))
        },
        hour,
        minute,
        true
    )
    timePickerFragment.version = TimePickerDialog.Version.VERSION_2
    timePickerFragment.setTimeInterval(1, 30)
    timePickerFragment.show(fragment, "")

}