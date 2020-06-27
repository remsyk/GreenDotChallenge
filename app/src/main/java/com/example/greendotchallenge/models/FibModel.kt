package com.example.greendotchallenge.models
import com.google.gson.annotations.SerializedName

data class FibModel (

    @SerializedName("input_number")
    var inputNumber: Int,
    @SerializedName("fib_numbers")
    var fibNumbers: Map<Int,Int>,
    @SerializedName("process_time")
    var processTime: Long
)