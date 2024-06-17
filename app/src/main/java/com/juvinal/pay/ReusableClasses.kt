package com.juvinal.pay

enum class DocumentType {
    NATIONAL_ID,
    PASSPORT
}

data class DocumentTypeItem(
    val name: String,
    val documentType: DocumentType
)

enum class HomeScreenSideBarMenuScreen {
    HOME,
    PROFILE
}

data class DashboardMenuItem(
    val name: String,
    val screen: HomeScreenSideBarMenuScreen
)

