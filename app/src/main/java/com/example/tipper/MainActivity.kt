package com.example.tipper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipper.ui.theme.TipperTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15.0) }

    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercent)
    val totalAmount = remember(amount, tip) { amount + tip }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { serviceCostAmountInput = generateRandomBill() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        {
            Text(text = stringResource(R.string.random_bill))
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TipPercentButton(
                text = "10%",
                isSelected = tipPercent == 10.0,
                onClick = { tipPercent = 10.0 }
            )
            TipPercentButton(
                text = "15%",
                isSelected = tipPercent == 15.0,
                onClick = { tipPercent = 15.0 }
            )
            TipPercentButton(
                text = "18%",
                isSelected = tipPercent == 18.0,
                onClick = { tipPercent = 18.0 }
            )
            TipPercentButton(
                text = "20%",
                isSelected = tipPercent == 20.0,
                onClick = { tipPercent = 20.0 }
            )

        }
        Spacer(Modifier.height(60.dp))
        Text(
            text = stringResource(R.string.bill_amount, NumberFormat.getCurrencyInstance().format(amount)),
            modifier = Modifier.align(Alignment.End),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.tip_amount, NumberFormat.getCurrencyInstance().format(tip)),
            modifier = Modifier.align(Alignment.End),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        Text (
            text = stringResource(R.string.total_amount, NumberFormat.getCurrencyInstance().format(totalAmount)),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(Modifier.height(24.dp))
    }
}


@Composable
fun TipPercentButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(72.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
        )
    ) {
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary
        )
    }
}

//Create button to generate a random bill
private fun generateRandomBill(): String {
    return (0..1000).random().toString()
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0
): Double {
    return tipPercent / 100 * amount
}

@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.service_cost)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipperTheme {
        TipCalculatorScreen()
    }
}