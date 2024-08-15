package ru.androidschool.intensiv.data

fun ratingCalculation(vote: Double?): Float{
    val coefficient = 2
    return vote?.div(coefficient)?.toFloat() ?: 0.0f
}