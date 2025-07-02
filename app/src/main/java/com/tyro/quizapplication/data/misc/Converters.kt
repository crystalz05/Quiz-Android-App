package com.tyro.quizapplication.data.misc

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromString(value: String): List<String> = value.split("|||")

    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString("|||")

}