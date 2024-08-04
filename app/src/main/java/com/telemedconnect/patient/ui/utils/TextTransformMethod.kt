package com.telemedconnect.patient.ui.utils

import android.text.method.TransformationMethod
import android.view.View

class TextTransformMethod(val type: Int = 0): TransformationMethod {

    companion object{
        const val CVC = 0
        const val ACCOUNT_NUMBER = 1
    }

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        val masked = StringBuilder()
        val sourceLength = source.length

        if(type == 0){
            for (i in 0 until sourceLength) {
                masked.append('•')
            }
        }else{

            val length = if(sourceLength > 4) 4 else sourceLength

            for (i in 0 until sourceLength) {
                masked.append(
                    if(i < sourceLength - length){
                        if(i> 0 && (i+1)%4==0){
                            "• "
                        }else{
                            "•"
                        }
                    }else{
                        if(i> 0 && (i+1)%4==0){
                            source[i] + " "
                        }else{
                            source[i]
                        }
                    }
                )
            }
            return masked.toString()

        }
        return masked.toString()
    }

    override fun onFocusChanged(view: View, sourceText: CharSequence, focused: Boolean, direction: Int, previouslyFocusedRect: android.graphics.Rect?) {
        // No need to handle focus changes for this use case
    }
}