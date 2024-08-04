package com.telemedconnect.patient.ui.utils

import android.text.InputFilter
import android.text.Spanned

class MinMaxInputFilter(private val min: Int, private val max: Int) : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest?.subSequence(0, dstart).toString() + source + dest?.subSequence(dend, dest.length))
            val inputInt = input.toInt()
            if (inputInt in min..max) {
                return null
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }
}