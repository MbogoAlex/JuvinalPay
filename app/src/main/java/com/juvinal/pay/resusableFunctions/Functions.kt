package com.juvinal.pay.resusableFunctions

import java.text.NumberFormat
import java.util.Locale

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

val formattedRent = NumberFormat.getCurrencyInstance(Locale("en", "KE")).format(10.0)

fun formatMoneyValue(amount: Double): String {
    return  NumberFormat.getCurrencyInstance(Locale("en", "KE")).format(amount)
}