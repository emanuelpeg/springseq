package com.assembly.springseq.service

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import com.assembly.springseq.rest.RestClient
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

interface GameService {

    fun startGame() : UUID

    fun getSequence(gameId : String) : SequenceDTO

    fun isOk(gameId : String, value: Int) : ResultDTO

}

@Service
class GameServiceImpl : GameService {

    private val restClient : RestClient

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        restClient = retrofit.create(RestClient::class.java)
    }

    override fun startGame(): UUID {
        return restClient.startGame().execute().body()!!
    }

    override fun getSequence(gameId: String): SequenceDTO {
        return restClient.getSequence(gameId).execute().body()!!
    }

    override fun isOk(gameId: String, value: Int): ResultDTO {
        return restClient.isOk(gameId, value).execute().body()!!
    }
}