package com.codeforcesvisualizer.core.data.data

sealed class Either<E, V> {
    class Left<E, V>(val data: E) : Either<E, V>()
    class Right<E, V>(val data: V) : Either<E, V>()
}