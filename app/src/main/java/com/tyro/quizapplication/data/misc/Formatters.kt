package com.tyro.quizapplication.data.misc

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(Locale.US,"%02d:%02d", minutes, remainingSeconds)
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatTimeStamp(timeStamp: Long): String {
    val dataDateTime = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
    val now =LocalDateTime.now()

    return when{
        isSameDay(dataDateTime, now) ->"Today at ${formatTimeData(dataDateTime)}"
        isSameDay(dataDateTime.plusDays(1), now) ->"Yesterday at ${formatTimeData(dataDateTime)}"

        else -> formatDateData(dataDateTime)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isSameDay(dateTime1: LocalDateTime, dateTime2: LocalDateTime): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return dateTime1.format(formatter) ==dateTime2.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatTimeData(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return formatter.format(dateTime)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatDateData(dateTime: LocalDateTime): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return formatter.format(dateTime)
}

fun String.toTitleCase(): String {
    return this.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}