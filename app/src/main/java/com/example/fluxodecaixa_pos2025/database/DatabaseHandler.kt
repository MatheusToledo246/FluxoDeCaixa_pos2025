package com.example.fluxodecaixa_pos2025.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.fluxodecaixa_pos2025.entity.Lancamento

class DatabaseHandler(context: Context):
        SQLiteOpenHelper( context, DATABASE_NAME, null, DATABASE_VERSION){
            override fun onCreate(banco: SQLiteDatabase?) {
                banco?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME}(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tipo TEXT, detalhe TEXT, valor DOUBLE, data TEXT)")
            }

    override fun onUpgrade(banco: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        banco?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate( banco )
    }

    fun lancar( lancamento: Lancamento){
        val banco = this.writableDatabase

        val registro = ContentValues()
        registro.put("tipo", lancamento.tipo)
        registro.put("detalhe", lancamento.detalhe)
        registro.put("valor", lancamento.valor)
        registro.put("data", lancamento.data.toString())

        banco.insert(TABLE_NAME,null,registro)

    }

    fun verLancamentos() : Cursor {
        val banco = this.writableDatabase

        val registros = banco.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return registros
    }

    fun selectTodasTransacoes(): Cursor {
        val banco = this.readableDatabase
        return banco.rawQuery("SELECT tipo, valor FROM ${TABLE_NAME}", null)
    }

    companion object{
        public const val DATABASE_VERSION = 3
        public const val DATABASE_NAME = "dbfile.sqlite"
        public const val TABLE_NAME = "lancamento"
        public const val ID = 0
        public const val TIPO = 1
        public const val DETALHE = 2
        public const val VALOR = 3
        public const val DATA = 4

    }


  }