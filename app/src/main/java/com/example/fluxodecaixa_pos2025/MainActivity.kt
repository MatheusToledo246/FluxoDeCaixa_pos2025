package com.example.fluxodecaixa_pos2025

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.fluxodecaixa_pos2025.database.DatabaseHandler
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var spTipo: Spinner
    private lateinit var spDetalhe: Spinner
    private lateinit var etValor: EditText
    private lateinit var btSelecionarData: Button
    private lateinit var tvDataSelecionada: TextView
    private lateinit var btLancar: Button
    private lateinit var btVerLancamento: Button
    private lateinit var btSaldo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spTipo = findViewById(R.id.spTipo)
        spDetalhe = findViewById(R.id.spDetalhe)
        etValor = findViewById(R.id.etValor)
        btSelecionarData = findViewById(R.id.btSelecionarData)
        tvDataSelecionada = findViewById(R.id.tvDataSelecionada)
        btLancar = findViewById(R.id.btLancar)
        btVerLancamento = findViewById(R.id.btVerLancamento)
        btSaldo = findViewById(R.id.btSaldo)

        val tipo = listOf("Crédito", "Débito")
        val listaCredito = listOf("Salário", "Extra")
        val listaDebito = listOf("Alimentação", "Transporte", "Moradia", "Saúde", "Outras")

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipo)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipo.adapter = spinnerAdapter

        spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val tipoSelecionado = tipo[position]
                Toast.makeText(this@MainActivity, "Selecionado: $tipoSelecionado", Toast.LENGTH_SHORT).show()

                val detalhes = if (tipoSelecionado == "Crédito") listaCredito else listaDebito
                val detalheAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, detalhes)
                detalheAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spDetalhe.adapter = detalheAdapter
            }

            //só para aparecer na tela a opção selecionada
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nenhuma ação necessária aqui agora
            }
        }

        btSelecionarData.setOnClickListener {
            abrirDatePicker()
        }

        btLancar.setOnClickListener {
            val tipoSelecionado = spTipo.selectedItem.toString()
            val detalhe = spDetalhe.selectedItem.toString()
            val valor = etValor.text.toString().replace(",", ".").toDoubleOrNull()
            val data = tvDataSelecionada.text.toString()

            if (valor == null || data == "Data não selecionada ainda") {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val lancamento = com.example.fluxodecaixa_pos2025.entity.Lancamento(0, tipoSelecionado, detalhe, valor, data)
            val db = com.example.fluxodecaixa_pos2025.database.DatabaseHandler(this)
            db.lancar(lancamento)

            Toast.makeText(this, "Lançamento salvo com sucesso!", Toast.LENGTH_SHORT).show()

            etValor.text.clear()
            tvDataSelecionada.text = ""
        }
        btVerLancamento.setOnClickListener {
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        }

        btSaldo.setOnClickListener {
            val db = DatabaseHandler(this)
            val cursor = db.selectTodasTransacoes()

            var totalCredito = 0.0
            var totalDebito = 0.0

            if (cursor.moveToFirst()) {
                do {
                    val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                    val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))

                    if (tipo == "Crédito") {
                        totalCredito += valor
                    } else if (tipo == "Débito") {
                        totalDebito += valor
                    }
                } while (cursor.moveToNext())
            }

            val saldo = totalCredito - totalDebito
            val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            val saldoFormatado = formato.format(saldo)


            val cor = if (saldo >= 0) {
                ContextCompat.getColor(this, R.color.verde)
            } else {
                ContextCompat.getColor(this, R.color.vermelho)
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Saldo Atual")
            builder.setMessage("\n\n$saldoFormatado")
            builder.setPositiveButton("OK", null)

            val dialog = builder.create()
            dialog.show()

            cursor.close()
            db.close()
        }

    }

    private fun abrirDatePicker() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Formata a data escolhida para dd/MM/yyyy
                val dataSelecionada = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                tvDataSelecionada.text = dataSelecionada
            },
            ano, mes, dia
        )
        datePickerDialog.show()
    }

}
