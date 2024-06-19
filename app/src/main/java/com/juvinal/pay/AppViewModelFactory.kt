package com.juvinal.pay

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelFactory {
    val Factory = viewModelFactory {

    }
}

fun CreationExtras.juvinalPayApplication(): JuvinalPay =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JuvinalPay)