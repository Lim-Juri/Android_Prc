package com.example.recyclerview

sealed class IconItem {
    data class MyTitle(val age: Int) : IconItem(), StickyHeader
    data class MyItem(val Icon: Int, val Name: String, val Age: Int) : IconItem()
}
