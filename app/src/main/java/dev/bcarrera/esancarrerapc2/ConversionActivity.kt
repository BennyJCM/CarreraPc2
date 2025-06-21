package dev.bcarrera.esancarrerapc2
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.LinearLayout
import android.view.Gravity
import android.text.InputType
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class ConversionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear el layout programáticamente
        val linearLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(16, 16, 16, 16)
        }

        // Crear EditText para la cantidad
        val amountEditText = EditText(this).apply {
            hint = "Amount"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            setPadding(16, 16, 16, 16)  // Espaciado para un mejor aspecto
        }

        // Crear Spinner para la moneda de origen
        val currencies = arrayOf("USD", "EUR", "PEN", "GBP", "JPY")
        val fromSpinner = Spinner(this).apply {
            val adapter = ArrayAdapter(this@ConversionActivity, android.R.layout.simple_spinner_item, currencies)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter
        }

        // Crear Spinner para la moneda de destino
        val toSpinner = Spinner(this).apply {
            val adapter = ArrayAdapter(this@ConversionActivity, android.R.layout.simple_spinner_item, currencies)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter
        }

        // Crear el botón de conversión
        val convertButton = Button(this).apply {
            text = "Convert"
            setOnClickListener {
                val amount = amountEditText.text.toString().toDoubleOrNull()
                if (amount != null) {
                    val fromCurrency = fromSpinner.selectedItem.toString()
                    val toCurrency = toSpinner.selectedItem.toString()
                    convertCurrency(fromCurrency, toCurrency, amount)
                } else {
                    Toast.makeText(this@ConversionActivity, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Crear el TextView para mostrar el resultado
        val resultTextView = TextView(this).apply {
            text = "Result will appear here"
            textSize = 18f
            gravity = Gravity.CENTER
        }

        // Añadir los elementos al layout
        linearLayout.addView(amountEditText)
        linearLayout.addView(fromSpinner)
        linearLayout.addView(toSpinner)
        linearLayout.addView(convertButton)
        linearLayout.addView(resultTextView)

        // Establecer el layout de la actividad
        setContentView(linearLayout)
    }

    private fun convertCurrency(from: String, to: String, amount: Double) {
        val rates = mapOf(
            "USD" to mapOf("EUR" to 0.925, "PEN" to 3.72, "GBP" to 0.75, "JPY" to 110.5),
            "EUR" to mapOf("USD" to 1.08, "PEN" to 4.02, "GBP" to 0.81, "JPY" to 119.6)
        )

        val rate = rates[from]?.get(to)
        if (rate != null) {
            val result = amount * rate
            Toast.makeText(this, "$amount $from = $result $to", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Conversion rate not available", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun ConversionScreen() {
    Text(text = "Welcome to Conversion Screen!")
}

@Preview(showBackground = true)
@Composable
fun ConversionScreenPreview() {
    ConversionScreen() // Vista previa para la pantalla de Conversion
}