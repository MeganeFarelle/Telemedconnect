package com.telemedconnect.patient.ui.adapters

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.telemedconnect.patient.data.entities.InsuranceProvider

object EditTextBindingAdapter {

    private var isProgrammaticChange = false

    @JvmStatic
    @BindingAdapter("insuranceProvider")
    fun setInsuranceProvider(editText: EditText, insuranceProvider: InsuranceProvider?){
        insuranceProvider?.let {
            if(isProgrammaticChange && editText.text.toString() != it.name) {
                isProgrammaticChange = true
                editText.setText(it.name)
                editText.setSelection(editText.text.length)
                isProgrammaticChange = false
            }

        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "insuranceProvider")
    fun getInsuranceProvider(view: EditText): InsuranceProvider {
        val text = view.text.toString()
        return if (text.isNotEmpty()){
            InsuranceProvider(name = text)
        }else{
            InsuranceProvider(name = "")
        }
    }

    @JvmStatic
    @BindingAdapter("insuranceProviderAttrChanged")
    fun setInsuranceProviderListener(view: EditText, listener: InverseBindingListener?) {
        if (listener != null) {
            view.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if(!isProgrammaticChange){
                        listener.onChange()
                    }
                }
            })
        }
    }

}