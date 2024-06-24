package com.assembly.springseq.rest

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*

interface RestClient {

    @GET("/startGame")
    fun startGame() : Call<UUID>

    @GET("/sequence")
    fun getSequence(@Header("gameId") gameId : String) : Call<SequenceDTO>

    @POST("/isOk")
    fun isOk(@Header("gameId") gameId : String, @Body value: Int) : Call<ResultDTO>

}