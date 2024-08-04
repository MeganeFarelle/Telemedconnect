package com.telemedconnect.patient.ui.adapters

import android.graphics.Bitmap
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.imageview.ShapeableImageView
import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.entities.PaymentMethod
import com.telemedconnect.patient.data.entities.Subscription
import com.telemedconnect.patient.data.entities.Transaction

object SpinnerBindingAdapter {
    @BindingAdapter("selectedValue")
    @JvmStatic
    fun bindSelectedValue(spinner: Spinner, selectedValue: String?) {
        val adapter = spinner.adapter as? ArrayAdapter<String> ?: return
        val position = selectedValue?.let { adapter.getPosition(it) } ?: AdapterView.INVALID_POSITION
        if (position != spinner.selectedItemPosition) {
            spinner.setSelection(position)
        }
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    @JvmStatic
    fun captureSelectedValue(spinner: Spinner): String? {
        return spinner.selectedItem as? String
    }

    @BindingAdapter("selectedValueAttrChanged")
    @JvmStatic
    fun setListeners(spinner: Spinner, attrChange: InverseBindingListener?) {
        if (attrChange == null) {
            spinner.onItemSelectedListener = null
        } else {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                    attrChange.onChange()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    attrChange.onChange()
                }
            }
        }
    }
}



