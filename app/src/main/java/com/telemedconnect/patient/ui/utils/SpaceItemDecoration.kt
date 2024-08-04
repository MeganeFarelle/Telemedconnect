package com.telemedconnect.patient.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val verticalSpace: Int,
    private val horizontalSpace: Int = 0,
    private val type : Int = 0,
    private val columns : Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)

            when(type){
                1 -> {
                    if(columns > 0){
                        bottom = verticalSpace
                        if((position+1)%columns == 0){
                            left = horizontalSpace/2
                        }else{
                            right = horizontalSpace/2
                        }

                        if(position < columns){
                            top = verticalSpace
                        }
                    }
                }
                else->{
                    if (parent.getChildAdapterPosition(view) == 0) {
                        top = verticalSpace
                    }
                    left = horizontalSpace
                    right = horizontalSpace
                    bottom = verticalSpace
                }
            }

        }
    }
}