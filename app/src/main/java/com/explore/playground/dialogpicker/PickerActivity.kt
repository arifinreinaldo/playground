package com.explore.playground.dialogpicker

import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.mensa.homecare.util.DatePickerFragment
import com.mensa.homecare.util.TimePickerFragment
import kotlinx.android.synthetic.main.activity_picker.*

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
    }

}
