package com.juvinal.pay.resusableFunctions

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


fun formatMoneyValue(amount: Double): String {
    return  NumberFormat.getCurrencyInstance(Locale("en", "KE")).format(amount)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTimeValue(dateTime: String): String {
    val dateTimeParts = dateTime.split("T")
    val datePart = dateTimeParts[0] // yyyy-MM-dd
    val timePart = dateTimeParts[1].substring(0, 5) // HH:mm
    return "$datePart $timePart"
}

fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d'suffix' MMMM yyyy, h:mm a", Locale.getDefault())

    val date = inputFormat.parse(inputDate)

    val day = SimpleDateFormat("d", Locale.getDefault()).format(date).toInt()
    val suffix = getDayOfMonthSuffix(day)

    val outputFormatStr = outputFormat.format(date).replace("suffix", suffix)

    return outputFormatStr
}

fun getDayOfMonthSuffix(n: Int): String {
    return if (n in 11..13) {
        "th"
    } else {
        when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}