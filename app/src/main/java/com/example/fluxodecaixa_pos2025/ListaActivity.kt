package com.example.fluxodecaixa_pos2025

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fluxodecaixa_pos2025.database.DatabaseHandler
import com.example.usandosqlite_pos2025.adapter.MeuAdapter

class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        val lista = findViewById<ListView>(R.id.lvLancamentos)
        val db = DatabaseHandler(this)
        val cursor = db.verLancamentos()

        lista.adapter = MeuAdapter(this, cursor)
    }
}
