package br.com.dio.coinconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias ExchangeResponse = HashMap<String, ExchangeResponseValue>

// Sem a tableName o nome da tabela seria o mesmo nome da classe
@Entity(tableName = "tb_search")
data class ExchangeResponseValue (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val code: String,
    val codein: String,
    val name: String,
    val bid: Double
)