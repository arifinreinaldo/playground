package com.explore.playground.dialogpicker

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(val act: Activity) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var editDate: EditText
    private lateinit var formatter: SimpleDateFormat
    private val format = "dd-MMM-yyyy"
    private lateinit var cal: Calendar

    fun showDate(editText: EditText, fragment: FragmentManager?, tag: String) {
        formatter = SimpleDateFormat(format)
        cal = Calendar.getInstance()
        if (!this.isAdded) {
            editDate = editText
            fragment?.let {
                show(fragment, tag)
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        cal.set(year, month, day)
        val date = cal.time
        editDate.setText(formatter.format(date))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var date: String = editDate.text.toString()
        var year = 0
        var month = 0
        var day = 0
        if (date.isEmpty()) {
            year = cal.get(Calendar.YEAR)
            month = cal.get(Calendar.MONTH)
            day = cal.get(Calendar.DAY_OF_MONTH)
        } else {
            val pointerDate = formatter.parse(date)
            cal.time = pointerDate
            year = cal.get(Calendar.YEAR)
            month = cal.get(Calendar.MONTH)
            day = cal.get(Calendar.DAY_OF_MONTH)
        }
        return DatePickerDialog(act, this, year, month, day)

    }
}


class TimePickerFragment(val act: Activity) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        editDate.setText(String.format("%02d:%02d", hourOfDay, minute))
    }

    private lateinit var editDate: EditText

    fun showTime(editText: EditText, fragment: FragmentManager?, tag: String) {
        if (!this.isAdded) {
            editDate = editText
            fragment?.let {
                show(fragment, tag)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var date: String = editDate.text.toString()
        if (date.isEmpty()) {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            return TimePickerDialog(
                activity,
                this,
                hour,
                minute,
                true
            )

        } else {
            val splitDate = date.split(":")
            val hour = splitDate[0].toInt()
            val minute = splitDate[1].toInt()
            return TimePickerDialog(
                activity,
                this,
                hour,
                minute,
                true
            )
        }

    }
}