package com.juvinal.pay.ui.screens.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.DocumentType
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.UserRegistrationRequestBody
import com.juvinal.pay.model.dbModel.User
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistrationScreenUiState (
    val surname: String = "",
    val fName: String = "",
    val lName: String = "",
    val documentType: DocumentType = DocumentType.NATIONAL_ID,
    val documentNo: String = "",
    val phoneNo: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val saveButtonEnabled: Boolean = false,
    val registrationFailureMessage: String = "",
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class RegistrationScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val dbRepository: DBRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(value = RegistrationScreenUiState())
    val uiState: StateFlow<RegistrationScreenUiState> = _uiState.asStateFlow()

    private var users = mutableStateListOf<User>()
    private var registrationSuccess = false
    fun registerUser() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        val user = UserRegistrationRequestBody(
            surname = uiState.value.surname,
            fname = uiState.value.fName,
            lname = uiState.value.lName,
            document_type = uiState.value.documentType.name,
            document_no = uiState.value.documentNo,
            phone_no = uiState.value.phoneNo,
            email = uiState.value.email,
            password = uiState.value.password,
            password_confirmation = uiState.value.passwordConfirmation
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.registerUser(
                    password = uiState.value.password,
                    userRegistrationRequestBody = user
                )
                if(response.isSuccessful) {
                    users.addAll(dbRepository.getUsers().first())

                    when(users.isNotEmpty() && !registrationSuccess) {
                        true -> {
                            registrationSuccess = true
                            _uiState.update {
                                it.copy(
                                    loadingStatus = LoadingStatus.SUCCESS
                                )
                            }
                        }
                        false -> {
                            delay(2000L)
                            users.addAll(dbRepository.getUsers().first())
                        }
                    }


                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL,
                            registrationFailureMessage = "User with existing details exists"
                        )
                    }
                    Log.e("USER_REGISTRATION_FAIL_ERROR_RESPONSE", response.toString())
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL,
                        registrationFailureMessage = "Check your connection or try again later"
                    )
                }
                Log.e("USER_REGISTRATION_FAIL_EXCEPTION", e.toString())
            }
        }
    }

    fun updateSurname(name: String) {
        _uiState.update {
            it.copy(
                surname = name
            )
        }
    }

    fun updateFName(name: String) {
        _uiState.update {
            it.copy(
                fName = name
            )
        }
    }

    fun updateLName(name: String) {
        _uiState.update {
            it.copy(
                lName = name
            )
        }
    }

    fun updateDocumentType(documentType: DocumentType) {
        _uiState.update {
            it.copy(
                documentType = documentType
            )
        }
    }

    fun updateDocumentNo(documentNo: String) {
        _uiState.update {
            it.copy(
                documentNo = documentNo
            )
        }
    }

    fun updatePhoneNo(phoneNo: String) {
        _uiState.update {
            it.copy(
                phoneNo = phoneNo
            )
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun updatePasswordConfirmation(password: String) {
        _uiState.update {
            it.copy(
                passwordConfirmation = password
            )
        }
    }

    fun resetLoadingStatus() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.INITIAL
            )
        }
    }

    fun checkIfAllFieldsAreFilled() {
        _uiState.update {
            it.copy(
                saveButtonEnabled = uiState.value.fName.isNotEmpty() &&
                        uiState.value.lName.isNotEmpty() &&
                        uiState.value.surname.isNotEmpty() &&
                        uiState.value.email.isNotEmpty() &&
                        uiState.value.documentNo.isNotEmpty() &&
                        uiState.value.phoneNo.isNotEmpty() &&
                        uiState.value.password.isNotEmpty() &&
                        uiState.value.passwordConfirmation.isNotEmpty()
            )
        }
    }
}