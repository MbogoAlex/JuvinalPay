package com.juvinal.pay

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

val documentTypes = listOf(
    DocumentTypeItem(
        name = "National Identification",
        documentType = DocumentType.NATIONAL_ID
    ),
    DocumentTypeItem(
        name = "Passport",
        documentType = DocumentType.PASSPORT
    ),
)

@RequiresApi(Build.VERSION_CODES.O)
val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")