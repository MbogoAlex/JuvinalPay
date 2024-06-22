package com.juvinal.pay

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.juvinal.pay.ui.screens.SplashScreenViewModel
import com.juvinal.pay.ui.screens.authentication.LoginScreenViewModel
import com.juvinal.pay.ui.screens.authentication.MembershipFeeScreenViewModel
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.HomeScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ChangePasswordScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PersonalDetailsScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ProfileScreenViewModel

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
                dsRepository = dsRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        // initialize MembershipFeeScreenViewModel

        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            MembershipFeeScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize SplashScreenViewModel
        initializer {
            val dsRepository = juvinalPayApplication().dsRepository
            SplashScreenViewModel(
                dsRepository = dsRepository
            )
        }

        // initialize HomeScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            HomeScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize ProfileScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            ProfileScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize PersonalDetailsScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            PersonalDetailsScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }

        // initialize ChangePasswordScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            ChangePasswordScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository
            )
        }
    }
}

fun CreationExtras.juvinalPayApplication(): JuvinalPay =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JuvinalPay)