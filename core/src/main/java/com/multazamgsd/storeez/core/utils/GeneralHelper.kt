package com.multazamgsd.storeez.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object GeneralHelper {
    private const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    @SuppressLint("SimpleDateFormat")
    fun String.toDateFormat(toFormat: String = "dd MMM yyy HH:mm"): String {
        return try {
            val idf = SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, Locale("IND", "ID")).parse(this)
            SimpleDateFormat(toFormat).format(idf!!)
        } catch (e: Exception) {
            this
        }
    }
}