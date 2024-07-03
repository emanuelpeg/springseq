package com.assembly.springseq.service

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import com.assembly.springseq.rest.RestClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.util.UUID

interface GameService {

    fun startGame() : UUID

    fun getSequence(gameId : String) : SequenceDTO

    fun isOk(gameId : String, value: Int) : ResultDTO

    fun getPoints(gameId: String) : Int
}

@Service
class GameServiceImpl : GameService {

    val log = LoggerFactory.getLogger(GameServiceImpl::class.java)

    @Value("\${game.servers}")
    private lateinit var baseUrls : String

    @Value("\${game.serverSeparator}")
    private var serverSeparator : Char = '|'

    @Value("\${game.retentive}")
    private var retentive : Int = 0

    private var restClient : RestClient? = null

    fun getRestClient() : RestClient {
        var countRetentive = -1
        while (restClient == null && countRetentive < retentive) {
            val urls = baseUrls.split(serverSeparator)

            for (baseUrl in urls) {
                try {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    restClient = retrofit.create(RestClient::class.java)
                    restClient!!.check().execute()
                    break
                } catch (ex : Exception) {
                    log.error(ex.message, ex)
                    restClient = null
                }
            }

            countRetentive++
        }

        if (restClient == null) {
            throw ConnectException("Could not connect to the server")
        }

        return restClient!!
    }

    fun <T> callRestApi(fx : () -> T) : T {
        var result : T? = null
        var countRetentive = -1
        while (result == null) {
            try {
                result = fx()
            } catch (e: ConnectException) {
                this.restClient = null
                result = null
                if (countRetentive >= retentive) throw e
            }
            countRetentive++
        }

        return result
    }

    override fun startGame(): UUID {
        return callRestApi { getRestClient().startGame().execute().body()!! }
    }

    override fun getSequence(gameId: String): SequenceDTO {
        return callRestApi { getRestClient().getSequence(gameId).execute().body()!! }
    }

    override fun isOk(gameId: String, value: Int): ResultDTO {
        return callRestApi { getRestClient().isOk(gameId, value).execute().body()!! }
    }

    override fun getPoints(gameId: String): Int {
        return callRestApi { getRestClient().point(gameId).execute().body()!! }
    }
}