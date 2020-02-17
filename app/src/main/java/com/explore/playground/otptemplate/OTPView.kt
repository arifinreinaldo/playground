package com.explore.playground.otptemplate

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import com.explore.playground.R
import com.explore.playground.utils.hideKeyboard
import kotlinx.android.synthetic.main.view_otp.view.*

class OTPView
@JvmOverloads
constructor(
    context: Context,
    atributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, atributeSet, defStyle) {
    private lateinit var arrayEditText: Array<EditText>
    lateinit var listener: OTPListener

    init {
        LayoutInflater.from(context).inflate(R.layout.view_otp, this, true)
        initListener()
    }

    interface OTPListener {
        fun onSuccess()
        fun onEmptyField()
    }

    fun initListener() {
        arrayEditText = arrayOf(
            etOTP1, etOTP2, etOTP3, etOTP4, etOTP5, etOTP6
        )

        arrayEditText.forEach {
            it?.let {
                it.setOnKeyListener { v, keyCode, event ->

                    false
                }
            }
        }

        etOTP1.addTextChangedListener(OTPWatcher(etOTP1, etOTP2, etOTP1))
        etOTP2.addTextChangedListener(OTPWatcher(etOTP1, etOTP3, etOTP2))
        etOTP3.addTextChangedListener(OTPWatcher(etOTP2, etOTP4, etOTP3))
        etOTP4.addTextChangedListener(OTPWatcher(etOTP3, etOTP5, etOTP4))
        etOTP5.addTextChangedListener(OTPWatcher(etOTP4, etOTP6, etOTP5))
        etOTP6.addTextChangedListener(OTPWatcher(prev = etOTP5, cur = etOTP6))

        etOTP6.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateOTPFinal()
            }
            true
        }
    }

    fun validateOTP(): Boolean {
        var rtn = false
        var text = ""
        arrayEditText.forEach {
            it?.let {
                text += it.text
            }
        }
        if (text.length == 6) {
            rtn = true
        }
        return rtn
    }

    fun validateOTPFinal() {
        if (validateOTP()) {
            hideKeyboard(context, etOTP6)
            listener.onSuccess()
        } else {
            listener.onEmptyField()
        }
    }


    inner class OTPWatcher(
        val prev: EditText,
        val next: EditText? = null,
        val cur: EditText
    ) :
        TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s.let { et ->
                if (et.toString().length == 0) {
//                    prev.requestFocus()
                } else {
                    next?.let {
                        it.requestFocus()
                    }
                }
                if (cur.id == R.id.etOTP6) {
                    validateOTPFinal()
                } else {
                    validateOTP()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //TODO
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if (it.length > 1) {
                    cur.setText(s.toString().get(start).toString())
                }
            }
        }

    }
}

