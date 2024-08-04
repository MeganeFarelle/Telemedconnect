package com.telemedconnect.patient.ui.adapters

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.entities.PaymentMethod
import com.telemedconnect.patient.data.entities.Subscription
import com.telemedconnect.patient.data.entities.Transaction

object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("bitmap")
    fun setBitmap(view: ShapeableImageView, image: Bitmap?) {
        if (image != null) {
            view.setImageBitmap(image)
        }
    }

    @JvmStatic
    @BindingAdapter("paymentIcon")
    fun setPaymentIcon(view: ImageView, item: PaymentMethod?) {
        when (item?.id){
            "1"->{
                view.setImageResource(R.drawable.ic_master_card)
            }
            "2"->{
                view.setImageResource(R.drawable.ic_visa)
            }
            "3"->{
                view.setImageResource(R.drawable.ic_paypal)
            }
            "4"->{
                view.setImageResource(R.drawable.ic_apple_pay)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("paymentIconFull")
    fun setPaymentIconFull(view: ImageView, item: PaymentMethod?) {
        when (item?.id){
            "1"->{
                view.setImageResource(R.drawable.ic_full_master_card)
            }
            "2"->{
                view.setImageResource(R.drawable.ic_visa)
            }
            "3"->{
                view.setImageResource(R.drawable.ic_paypal)
            }
            "4"->{
                view.setImageResource(R.drawable.ic_apple_pay)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("paymentBackgroundFull")
    fun setPaymentBackground(view: ConstraintLayout, item: PaymentMethod?) {
        when (item?.id){
            "1"->{
                view.setBackgroundResource(R.drawable.bg_full_mastercard)
            }
            "2"->{
                view.setBackgroundResource(R.drawable.bg_full_visa)
            }
            "3"->{
                view.setBackgroundResource(R.drawable.bg_full_paypal)
            }
            "4"->{
                view.setBackgroundResource(R.drawable.bg_full_apple_pay)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("transactionStatus")
    fun setTransactionStatus(view: ImageView, transaction: Transaction?) {
        if (transaction!= null) {
            when(transaction.status) {
                "Completed" -> {
                    view.setImageResource(R.drawable.ic_completed)
                }
                "Failed" -> {
                    view.setImageResource(R.drawable.ic_failed)
                }
                "Processing" -> {
                    view.setImageResource(R.drawable.ic_processing)
                }
            }

        }
    }

    @JvmStatic
    @BindingAdapter("check")
    fun setCheck(view : ImageView, subscription: Subscription?){
        val context = view.context
        view.visibility = if(subscription?.active!!)  View.VISIBLE else View.GONE

        view.background = if(subscription.active){
            context.resources.getDrawable(R.drawable.ic_round_check_opposite, context.theme)
        }else{
            context.resources.getDrawable(R.drawable.ic_round_check, context.theme)
        }
    }
}



