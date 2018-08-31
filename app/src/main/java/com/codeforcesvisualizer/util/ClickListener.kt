package com.codeforcesvisualizer.util

interface ClickListener<T> {
    fun onClicked(item: T, position: Int)
}