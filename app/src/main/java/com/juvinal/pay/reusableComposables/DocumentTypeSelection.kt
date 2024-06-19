package com.juvinal.pay.reusableComposables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.DocumentType
import com.juvinal.pay.documentTypes

@Composable
fun DocumentTypeSelection(
    documentType: DocumentType,
    onChangeDocumentType: (document: DocumentType) -> Unit,
    expanded: Boolean,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedType = if(documentType == DocumentType.NATIONAL_ID) "National Identification" else "Passport"

    Column {
        Row {
            Text(
                text = "Document Type",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "*",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .clickable {
                    onExpand()
                }
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(
                        10.dp
                    )
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(text = selectedType)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Select Document Type"
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(expanded) {
                documentTypes.forEachIndexed { index, s ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onChangeDocumentType(s.documentType)
                            }
                    ) {
                        Text(
                            text = s.name,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding()
                                .padding(
                                    top = 10.dp,
                                    bottom = 10.dp,
                                    start = 5.dp
                                )

                        )
                    }
                }
            }
        }

    }
}