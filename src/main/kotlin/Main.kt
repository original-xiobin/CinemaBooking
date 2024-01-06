package org.example

import java.util.Scanner

data class TheatreData(var purchasedTickets: Int, var currentIncome: Int)
val theatreData = TheatreData(0,0)

val sc: Scanner = Scanner(System.`in`)
var isBooking = true

fun main() {
    val theatre = initTheatre()
    while(isBooking){
        menu()
        val selected = sc.nextInt()
        process(selected, theatre)
    }
}

fun initTheatre() : MutableList<MutableList<Char>>{
    println("Enter the number of rows:")
    val rows = sc.nextInt()
    println("Enter the number of seats in each row:")
    val cols = sc.nextInt()
    return MutableList(rows){ MutableList(cols){'S'}}
}

fun menu(){
    println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
}

fun process(selection: Int, theatre: MutableList<MutableList<Char>>){
    when(selection){
        1 -> show(theatre)
        2 -> buy(theatre)
        3 -> statistics(theatre)
        0 -> exit()
        else -> println("No such a selection!")
    }
}

fun statistics(theatre: MutableList<MutableList<Char>>){
    val halfTheatre = theatre.size/2
    val maxIncome = if(theatre.size*theatre[0].size > 60) (halfTheatre*theatre[0].size*10) +
            ((theatre.size-halfTheatre)*theatre[0].size*8) else theatre.size*theatre[0].size*10
    val percentage = (theatreData.purchasedTickets.toDouble() / (theatre.size.toDouble()*theatre[0].size.toDouble()))*100
    val formatPercentage = "%.2f".format(percentage)
    println("\nNumber of purchased tickets: ${theatreData.purchasedTickets}\nPercentage: $formatPercentage%" +
    "\nCurrent income: \$${theatreData.currentIncome}\nTotal income: \$$maxIncome")
}

fun show(seats: MutableList<MutableList<Char>>){
    println("\nCinema:")
    for (i in 0..seats[0].size){
        if (i == 0) print(" ")
        else print(" $i")
    }
    for (r in 0..<seats.size){
        print("\n${r+1}")
        for(s in 0..<seats[0].size) {
            print(" ${seats[r][s]}")
        }
    }
    println()
}

fun buy(seats: MutableList<MutableList<Char>>){
    var isFound = false
    while(!isFound){
        println("\nEnter a row number:")
        val row = sc.nextInt()
        println("Enter a seat number in that row:")
        val seat = sc.nextInt()
        if(row !in 1..seats.size || seat !in 1..seats[0].size){
            println("\nWrong input!")
        }
        else if(seats[row-1][seat-1] != 'B'){
            val price = if(seats.size*seats[0].size> 60 && (row > (seats.size/2))) 8 else 10
            println("Ticket price: \$$price")
            seats[row-1][seat-1] = 'B'
            theatreData.purchasedTickets++
            theatreData.currentIncome += price
            isFound = true
        } else{
            println("That ticket has already been purchased!")
        }
    }
}

fun exit(){
    isBooking = false
}