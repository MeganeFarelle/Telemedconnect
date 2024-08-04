package com.telemedconnect.patient.ui.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.core.content.ContextCompat
import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.entities.Subscription

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("backgroundRes")
    fun setBackground(view: View, resource: Int?) {
        if (resource != null) {
            view.background = ContextCompat.getDrawable(view.context, resource)
        }
    }

    @JvmStatic
    @BindingAdapter("subscriptionBackground")
    fun setSubscriptionBackground(view: View, item: Subscription?) {
        val appContext = view.context.applicationContext
        view.background =  if(item!!.active){
            appContext.resources.getDrawable(R.drawable.bg_subscription_selected, appContext.theme)
        }else{
            appContext.resources.getDrawable(R.drawable.bg_subscription_default, appContext.theme)
        }
    }
}