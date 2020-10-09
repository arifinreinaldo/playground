package com.explore.playground.utils

import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
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
    for (x in 0..(ar.size - 1)) {
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

// Complete the countApplesAndOranges function below.
fun countApplesAndOranges(
    s: Int,
    t: Int,
    a: Int,
    b: Int,
    apples: Array<Int>,
    oranges: Array<Int>
): Unit {
    var homeApple = 0
    var homeOrange = 0
    apples.forEach {
        if ((it + a) >= s && (it + a) <= t) homeApple++
    }
    oranges.forEach {
        if ((it + b) <= t && (it + b) >= s) homeOrange++
    }
    print("$homeApple \n$homeOrange")
}


// Complete the kangaroo function below.
fun kangaroo(x1: Int, v1: Int, x2: Int, v2: Int): String {
    return if (x1 == x2) {
        if (v1 == v2) "YES" else "NO"
    } else {
        if (x1 < x2) {
            if (v1 <= v2) "NO" else {
                if ((x2 - x1) % (v1 - v2) == 0) "YES" else "NO"
            }
        } else {
            if (v2 <= v1) "NO" else {
                if ((x1 - x2) % (v2 - v1) == 0) "YES" else "NO"
            }
        }
    }
}

//although brute still faster (for number with small limit faster, for number with higher limit goes for LCM GCM)
fun getTotalXBrute(a: Array<Int>, b: Array<Int>): Int {


    var list = mutableListOf<Int>()
    a.sort()
    b.sort()

    val largestA = a[a.size - 1]
    val largestB = b[b.size - 1]
    var isOkay = true
    var bees = arrayListOf<Int>()
    bees.addAll(b)
    bees.add(largestA)

    for (bee in largestA..largestB) {
        isOkay = true
        a.forEach { ay ->
            if (bee % ay != 0) isOkay = false
        }
        b.forEach { bay ->
            if (bay % bee != 0) isOkay = false
        }
        if (isOkay) list.add(bee)
    }
    return list.size
}

//a.k.a KPK
fun getLCM(a: Array<Int>): Map<Int, Int> {
    var lcm = hashMapOf<Int, Int>()
    a.distinct().forEach {
        var number = it
        var divider = 1
        var stop = false
        var list = mutableListOf<Int>()

        //LCM a.k.a KPK
        while (number != 1) {
            divider = 1
            stop = false
            do {
                divider++
                if (number % divider == 0) {
                    stop = true
                    number /= divider
                    list.add(divider)
                }
            } while (!stop)
        }
        if (list.isEmpty()) {
            list.add(1)
        }
        if (lcm.isEmpty()) {
            lcm.putAll(list.groupingBy { it }.eachCount())
        } else {
            list.groupingBy { it }.eachCount() //kpk result of the number
                .forEach {
                    lcm.get(it.key)?.let { rst ->
                        if (rst < it.value) {
                            lcm.put(it.key, it.value)
                        }
                    } ?: run {
                        lcm.put(it.key, it.value)
                    }
                }//concatenate to kpk result
        }
    }
    return lcm
}


//a.k.a FPB
fun getGCM(a: Array<Int>): Map<Int, Int> {
    var gcm = hashMapOf<Int, Int>()
    var listFactorial = mutableListOf<Int>()
    var data = mutableListOf<Map<Int, Int>>()
    a.distinct().forEach {
        var number = it
        var divider = 1
        var stop = false
        var list = mutableListOf<Int>()

        //LCM a.k.a KPK
        while (number != 1) {
            divider = 1
            stop = false
            do {
                divider++
                if (number % divider == 0) {
                    stop = true
                    number /= divider
                    list.add(divider)
                }
            } while (!stop)
        }
        if (list.isEmpty()) {
            list.add(1)
        }
        if (gcm.isEmpty()) {
            gcm.putAll(list.groupingBy { it }.eachCount())
        } else {
            list.groupingBy { it }.eachCount() //kpk result of the number
                .forEach {
                    gcm.get(it.key)?.let { rst ->
                        if (rst > it.value) {
                            gcm.put(it.key, it.value)
                        }
                    } ?: run {
                        gcm.put(it.key, it.value)
                    }
                }
        }
        listFactorial.addAll(list.distinct())
    }
    var mapFactorial = listFactorial.groupingBy { it }.eachCount()
    val distinctFactorial = mutableListOf<Int>()
    mapFactorial.forEach {
        if (it.value == a.size) {
            distinctFactorial.add(it.key)
        }
    }
    var maps = mutableMapOf<Int, Int>()
    distinctFactorial.forEach { idx ->
        gcm.get(idx)?.let { value ->
            maps.put(idx, value)
        }
    }
    return maps
}

fun getTotalX(a: Array<Int>, b: Array<Int>): Int {
    var lists = mutableListOf<Int>()
    a.sort()
    b.sort()
    val times = mutableListOf<Long>()
    times.add(System.nanoTime())

    val gcmData = getGCM(b)
    val multiplier = mutableListOf<Int>()
    gcmData.forEach {
        for (x in 1..it.value) {
            multiplier.add(it.key)
        }
    }
    val possibilities = mutableListOf<Int>()
    for (x in 0 until multiplier.size) {
        var value = multiplier[x]
        possibilities.add(value)
        for (y in 0 until multiplier.size) {
            if (x != y) {
                value *= multiplier[y]
                possibilities.add(value)
                possibilities.add(multiplier[x] * multiplier[y])
            }
        }
    }
    possibilities.distinct().forEach { possible ->
        var isOkay = true
        a.forEach { divider ->
            if (possible % divider != 0) isOkay = false
        }
        if (isOkay) lists.add(possible)
    }

    times.add(System.nanoTime())
    val sec = TimeUnit.MICROSECONDS.convert(times[1] - times[0], TimeUnit.NANOSECONDS)
    return lists.size
}