package com.juvinal.pay.resusableFunctions

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
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