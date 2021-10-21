package br.com.dio.coinconverter.data.repository

import android.os.RemoteException
import br.com.dio.coinconverter.data.database.AppDatabase
import br.com.dio.coinconverter.data.model.ErroResponse
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.data.services.AwesomeServices
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CoinRepositoryImplementer(

    appDatabase: AppDatabase,
    private val service: AwesomeServices

): CoinRepository {

    private val dao = appDatabase.exchangeDao()

    override suspend fun getExchangeValues(coin: String) = flow {
        try {
            val exchangeValue = service.exchangeValue(coin)
            val value = exchangeValue.values.first()
            emit(value)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val errorResponse= Gson().fromJson(json, ErroResponse::class.java)
            throw RemoteException(errorResponse.message)
        }

    }

    override suspend fun save(exchange: ExchangeResponseValue) {
        dao.save(exchange)
    }

    override fun list(): Flow<List<ExchangeResponseValue>> {
        return dao.findAll()
    }


}