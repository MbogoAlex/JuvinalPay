package com.juvinal.pay.reusableComposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.juvinal.pay.model.LoanTypeDt

@Composable
fun LoanTypeSelection(
    loanType: LoanTypeDt,
    loanTypes: List<LoanTypeDt>,
    expanded: Boolean,
    onExpand: () -> Unit,
    onSelectLoanType: (loanType: LoanTypeDt) -> Unit,
    modifier: Modifier = Modifier
) {

    var dropDownIcon: ImageVector
    if(expanded) {
        dropDownIcon = Icons.Default.KeyboardArrowUp
    } else {
        dropDownIcon = Icons.Default.KeyboardArrowDown
    }
    Column(
        modifier = modifier
    ) {
        Card(
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent
//            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onExpand()
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                if(loanType.loan_type_name.isEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier

                    ) {
                        Text(
                            text = "Loan type",

                            )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "*",
                            color = Color.Red
                        )
                    }

                } else {
                    Text(
                        text = loanType.loan_type_name,
                        modifier = Modifier

                    )

                }
                Icon(
                    imageVector = dropDownIcon,
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onExpand,
            modifier = Modifier
//                .padding(
//                    horizontal = 20.dp
//                )
//                .fillMaxWidth()
        ) {
            loanTypes.forEachIndexed { index, i ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = i.loan_type_name
                        )
                    },
                    onClick = {
                        onSelectLoanType(i)
                    },
                    modifier = Modifier
                )
            }
        }
    }
}