package com.example.temperatura.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.temperatura.R
import com.example.temperatura.enuns.TipoGrau
import com.example.temperatura.model.ConvetTemperatura
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var textInputLayoutGrau : TextInputLayout
    private lateinit var inputTextGrauDigitado: TextInputEditText
    private lateinit var tipoGrauSeleciona: RadioGroup
    private lateinit var grauCelsiosSelecionado: RadioButton
    private lateinit var grauFahrenheitSelecionado: RadioButton
    private lateinit var resultado: TextView
    private lateinit var tipoGrau: TipoGrau
    private lateinit var buttonConverte: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instanciaLayoutComXml()
        radioButtonCheckedChangeListener()
        calculaGrau()


    }

    private fun instanciaLayoutComXml() {

        textInputLayoutGrau = findViewById(R.id.TextInputLayoutGrau)
        inputTextGrauDigitado = findViewById(R.id.TextInputEditTextGrau)
        tipoGrauSeleciona = findViewById(R.id.radioGroupTipoGrau)
        grauCelsiosSelecionado = findViewById(R.id.radioButtonCelsios)
        grauFahrenheitSelecionado = findViewById(R.id.radioButtonFahrenheit)
        resultado = findViewById(R.id.textViewResultado)
        buttonConverte = findViewById(R.id.buttonConverte)
    }

    private fun calculaGrau() {

        verificaGrauSelecionadoRadioGroup()

        buttonConverte.setOnClickListener {
            val temperaturaConvertida = ConvetTemperatura()

            if (validaCampos() and (grauFahrenheitSelecionado())) {
                val valorEmCelsios = String.format("%.1f", temperaturaConvertida.calculaTemperaturaEmGrauCelsios(
                    inputTextGrauDigitado.text.toString().toDouble()
                ))
                val valorInformadoFahrenheit = String.format("%.1f", inputTextGrauDigitado.text.toString().toDouble())
                resultado.text =  "Grau Fahrenheit = $valorInformadoFahrenheit °F \n" +
                        "Grau em Celsios = $valorEmCelsios °C"

            }
            if (validaCampos() and grauCelsiosSelecionado()) {
                val valorEmFahrenheit = String.format("%.1f", temperaturaConvertida.calculaTemperaturaEmGrauFahrenheit(
                    inputTextGrauDigitado.text.toString().toDouble()
                ))
                val valorInformadoCelsios = String.format("%.1f",inputTextGrauDigitado.text.toString().toDouble())

                resultado.text = "Grau Celsios = $valorInformadoCelsios °C \n" +
                        "Grau em Fahrenheit = $valorEmFahrenheit °F"
            }
        }
    }

    private fun verificaGrauSelecionadoRadioGroup() {

            when {
                grauCelsiosSelecionado() -> {
                    this.tipoGrau = TipoGrau.FAHRNHEIT
                    textInputLayoutGrau.hint = "Informe grau Celsios"
                }
                grauFahrenheitSelecionado() -> {
                    this.tipoGrau = TipoGrau.CELSIOS
                    textInputLayoutGrau.hint = "Informe grau Fahrnheit"
                }
                else -> {
                    this.tipoGrau = TipoGrau.NAO_IMFORMADO
                }
            }
    }

    private fun radioButtonCheckedChangeListener(){

        radioGroupTipoGrau.setOnCheckedChangeListener{ radioGroup: RadioGroup, i: Int ->
            verificaGrauSelecionadoRadioGroup()

            if (this.radioGroupTipoGrau.checkedRadioButtonId == R.id.radioButtonCelsios){
                radioButtonCelsios.isChecked = true
                radioButtonFahrenheit.isChecked = false
                inputTextGrauDigitado.setText("")
            }else {
                radioButtonCelsios.isChecked = false
                radioButtonFahrenheit.isChecked = true
                inputTextGrauDigitado.setText("")
            }

        }
    }

    private fun grauCelsiosSelecionado() = radioButtonCelsios.isChecked

    private fun grauFahrenheitSelecionado() = radioButtonFahrenheit.isChecked

    private fun validaCampos(): Boolean {

        if (!grauFahrenheitSelecionado() and !grauCelsiosSelecionado()) {
            Toast.makeText(this, "Selecione o Tipo do GRAU", Toast.LENGTH_LONG).show()
            return false
        }

        if (inputTextGrauDigitado.text.isNullOrEmpty()) {
            inputTextGrauDigitado.error = "Campo Obrigatório"
            return false
        }

        return true
    }
}
