package com.juvinal.pay

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            RegistrationScreenViewModel(
                apiRepository = apiRepository
            )
        }

    }
}

fun CreationExtras.juvinalPayApplication(): JuvinalPay =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JuvinalPay)