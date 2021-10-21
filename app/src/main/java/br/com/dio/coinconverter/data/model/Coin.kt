package br.com.dio.coinconverter.data.model

import java.util.*

enum class Coin (val locale: Locale) {
    BRL(Locale("pt", "BR")),
    USD(Locale.US),
    BTC(Locale.US),
    LTC(Locale.US);

    companion object {
        fun getByName(name: String) = values().find { it.name == name } ?: BRL
    }
}