package com.mensa.homecare.util

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
import java.util.*

class DatePickerFragment(val act: Activity) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var editDate: EditText

    fun showDate(editText: EditText, fragment: FragmentManager?, tag: String) {
        editDate = editText
        fragment?.let {
            show(fragment, tag)
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        editDate.setText(String.format("%02d/%02d/%d", day, (month + 1), year))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        var date: String = editDate.text.toString()
        if (date.isEmpty()) {
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(act, this, year, month, day)
        } else {
            val splitDate = date.split("/")
            val year = splitDate[2].toInt()
            val month = splitDate[1].toInt() - 1
            val day = splitDate[0].toInt()
            return DatePickerDialog(act, this, year, month, day)
        }

    }
}


class TimePickerFragment(val act: Activity) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        editDate.setText(String.format("%02d:%02d", hourOfDay, minute))
    }

    private lateinit var editDate: EditText

    fun showTime(editText: EditText, fragment: FragmentManager?, tag: String) {
        editDate = editText
        fragment?.let {
            show(fragment, tag)
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