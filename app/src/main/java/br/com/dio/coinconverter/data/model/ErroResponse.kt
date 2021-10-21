package br.com.dio.coinconverter.data.model

data class ErroResponse (
    val status: Long,
    val code: String,
    val message: String
)