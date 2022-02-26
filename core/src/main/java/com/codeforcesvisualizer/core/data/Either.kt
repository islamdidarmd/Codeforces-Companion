package com.codeforcesvisualizer.core.data

sealed class Either<E, V> {
    class Left<E, V>(val left: E) : Either<E, V>()
    class Right<E, V>(val right: V) : Either<E, V>()

    fun isLeft(): Boolean = this is Left
    fun isRight(): Boolean = this is Right
}