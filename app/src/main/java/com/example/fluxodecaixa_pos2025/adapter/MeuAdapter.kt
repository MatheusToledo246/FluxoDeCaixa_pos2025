package com.example.usandosqlite_pos2025.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.fluxodecaixa_pos2025.database.DatabaseHandler
import com.example.fluxodecaixa_pos2025.entity.Lancamento
import com.example.fluxodecaixa_pos2025.R
import java.text.NumberFormat
import java.util.Locale

class MeuAdapter(var context : Context, val cursor: Cursor) :  BaseAdapter() {

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(pos: Int): Any {
        cursor.moveToPosition(pos)
        val Lancamento = Lancamento(
            cursor.getInt(DatabaseHandler.ID).toInt(),
            cursor.getString(DatabaseHandler.TIPO).toString(),
            cursor.getString(DatabaseHandler.DETALHE).toString(),
            cursor.getDouble(DatabaseHandler.VALOR),
            cursor.getString(DatabaseHandler.DATA).toString()
        )
        return Lancamento
    }

    override fun getItemId(pos: Int): Long {
        cursor.moveToPosition(pos)
        return cursor.getInt(DatabaseHandler.ID).toLong()
    }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val elementoLista = inflater.inflate(R.layout.elemento_lista, null)

        val tvDetalheElementoLista = elementoLista.findViewById<TextView>(R.id.tvDetalheElementoLista)
        val tvDataElementoLista = elementoLista.findViewById<TextView>(R.id.tvDataElementoLista)
        val tvValorElementoLista = elementoLista.findViewById<TextView>(R.id.tvValorElementoLista)
        val ivIcone = elementoLista.findViewById<ImageView>(R.id.ivIcone)

        cursor.moveToPosition(pos)

        val tipo = cursor.getString(DatabaseHandler.TIPO)
        val valor = cursor.getDouble(DatabaseHandler.VALOR)

        tvDetalheElementoLista.text = cursor.getString(DatabaseHandler.DETALHE)
        tvDataElementoLista.text = cursor.getString(DatabaseHandler.DATA)

        val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val valorFormatado = formato.format(valor)

        tvValorElementoLista.text = valorFormatado

        val corFundo = if (tipo == "Crédito") {
            ivIcone.setImageResource(R.drawable.baseline_attach_money_24)
            ContextCompat.getColor(context, R.color.verde_claro)
        } else {
            ivIcone.setImageResource(R.drawable.baseline_call_received_24)
            ContextCompat.getColor(context, R.color.vermelho_claro)
        }
        elementoLista.setBackgroundColor(corFundo)

        return elementoLista
    }
}