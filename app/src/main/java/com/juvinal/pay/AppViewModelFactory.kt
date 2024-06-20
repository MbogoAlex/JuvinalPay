package com.juvinal.pay

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.juvinal.pay.ui.screens.authentication.LoginScreenViewModel
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {

        // initialize RegistrationScreenViewModel

        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            RegistrationScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize LoginScreenViewModel

        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            LoginScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

    }
}

fun CreationExtras.juvinalPayApplication(): JuvinalPay =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JuvinalPay)