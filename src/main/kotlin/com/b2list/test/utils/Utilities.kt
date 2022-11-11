package com.b2list.test.utils

import java.util.*

object Utilities {
    fun capitalizeString(string:String):String{
        var full = ""
        val separated = string.lowercase().split(" ")
        val clearedSeparated = mutableListOf<String>()
        separated.forEach { if(it != ""){clearedSeparated.add(it)} }
        var index = 0
        clearedSeparated.forEach { str ->
            index++
            if(str.isNotEmpty()){
                full += if(index == clearedSeparated.size){
                    str.replaceFirstChar{it.titlecase(Locale.getDefault())}
                } else{
                    str.replaceFirstChar{it.titlecase(Locale.getDefault())} + " "
                }
            }
        }
        return full
    }
}