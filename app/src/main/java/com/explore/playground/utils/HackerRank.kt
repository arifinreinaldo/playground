package com.explore.playground.utils

import java.text.DecimalFormat
import kotlin.math.abs


fun simpleArraySum(ar: Array<Int>): Int {
    /*
     * Write your code here.
     */
    var rtn = 0
    ar.forEach {
        rtn += it
    }
    return rtn
}

fun compareTriplets(a: Array<Int>, b: Array<Int>): Array<Int> {
    var andra = 0
    var berdo = 0
    for (x in 0..2) {
        if (a[x] > b[x]) {
            andra++
        } else if (a[x] < b[x]) {
            berdo++
        }
    }
    var array = arrayOf(andra, berdo)

    return array
}

fun diagonalDifference(arr: Array<Array<Int>>): Int {
    val sizex = arr.size.minus(1)
    val sizey = arr[0].size.minus(1)
    var diag1 = 0
    var diag2 = 0
    for (x in 0..sizex) {
        diag1 += arr[x][x]
        diag2 += arr[x][sizey - x]
    }
    return abs(diag1 - diag2)
}

// Complete the plusMinus function below.
fun plusMinus(arr: Array<Int>): Unit {
    var plus = 0.0
    var minus = 0.0
    var zero = 0.0
    val size = arr.size
    arr.forEach {
        if (it > 0) {
            plus++
        } else if (it < 0) {
            minus++
        } else {
            zero++
        }
    }
    val decimal = DecimalFormat("#.####")

    println(decimal.format((plus / size)))
    println(decimal.format((minus / size)))
    println(decimal.format((zero / size)))
}

fun staircase(n: Int): Unit {
    for (x in 1..n) {
        for (y in 1..n) {
            if (y <= (n - x)) {
                print(" ")
            } else {
                print("#")
            }
        }
        println("")
    }
}

// Complete the miniMaxSum function below.
fun miniMaxSum(arr: Array<Int>): Unit {
    arr.sort()
    var sum: Long = 0
    val max = arr[(arr.size - 1)]
    val min = arr[0]
    arr.forEach {
        sum += it
    }
    print("${sum.minus(max)} ${sum.minus(min)}")
}

fun birthdayCakeCandles(ar: Array<Int>): Int {
    ar.sortDescending()
    var max = 0
    var count = 0
    for (x in 0..ar.size) {
        if (x == 0) {
            count++
            max = ar[x]
        } else {
            if (max == ar[x]) {
                count++
            } else {
                break
            }
        }
    }
    return count
}

fun timeConversion(s: String): String {
    var str = ""
    if (s.endsWith("AM")) {
        var hour = s.substring(0, 2)
        if (hour == "12") {
            hour = "00"
        }
        str = hour.toString() + s.substring(2, 8)
    } else if (s.endsWith("PM")) {
        var hour = s.substring(0, 2).toInt() + 12
        if (hour == 24) {
            hour = 12
        }
        str = hour.toString() + s.substring(2, 8)
    }
    return str
}

