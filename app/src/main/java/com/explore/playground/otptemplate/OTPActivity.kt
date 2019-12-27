package com.explore.playground.otptemplate

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import kotlinx.android.synthetic.main.activity_otp.*

class OTPActivity : AppCompatActivity() {
    private lateinit var arrayEditText: Array<EditText>
    private lateinit var ctx: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        ctx = this
        initUI()

    }

    fun initUI() {
        //get lisetener from otpview
        otp_view.listener = object : OTPView.OTPListener {
            override fun onEmptyField() {
                Toast.makeText(ctx, "ERROR", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Toast.makeText(ctx, "SUCCESS", Toast.LENGTH_SHORT).show()
                //call otpview function examle
                Log.d("OTP", "CALL OTP Function" + otp_view.validateOTP())
            }
        }


    }
}
