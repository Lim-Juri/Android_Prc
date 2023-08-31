package com.example.recyclerview

import android.content.Context

class MyStickyLayoutManager(context: Context, headerHandler: MyAdapter) : StickyLayoutManager(context, headerHandler) {
    override fun scrollToPosition(position:Int){
        super.scrollToPositionWithOffset(position, 0)
    }
}