package com.juvinal.pay

enum class DocumentType {
    NATIONAL_ID,
    PASSPORT
}

data class DocumentTypeItem(
    val name: String,
    val documentType: DocumentType
)

