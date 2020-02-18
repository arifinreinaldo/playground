package com.explore.playground.utils

import android.content.Context
import android.content.SharedPreferences

object LocalValue {
    private lateinit var preferences: SharedPreferences
    private const val MODE = Context.MODE_PRIVATE
    private const val NAME = "HOMECARE_HALODUC_"

    val CURRENT_NUMBER = "${NAME}current_phone_number"
    val CURRENT_NUMBER_RETRY = "${NAME}current_phone_number_retry"
    val ISLOGGED = "${NAME}is_logged"
    val BIRTHDATE = "${NAME}birth_date"
    val CRTDATE = "${NAME}created_at"
    val DEVICEID = "${NAME}device_id"
    val FCMTOK = "${NAME}fcm_token"
    val GENDER = "${NAME}gender"
    val USERID = "${NAME}id"
    val USERNAM = "${NAME}name"
    val OTPCOD = "${NAME}otp_code"
    val OTPSTS = "${NAME}otp_status"
    val PHONUM = "${NAME}phone_numbers"
    val ROLID = "${NAME}role_id"
    val STATS = "${NAME}status"
    val TOKEN = "${NAME}token"
    val UPDAT = "${NAME}updated_at"

    fun init(ctx: Context) {
        preferences = ctx.getSharedPreferences(NAME, MODE)
    }

    var fcm: String
        get() = preferences.getString(FCMTOK, "").toString()
        set(value) = setPref(value, FCMTOK)

    fun setPref(value: Any?, TAG: String) {
        value?.let {
            val editor = preferences.edit()
            if (value is String) {
                editor.putString(TAG, value)
            } else if (value is Int) {
                editor.putInt(TAG, value)
            } else if (value is Boolean) {
                editor.putBoolean(TAG, value)
            }
            editor.apply()
        }
    }
}