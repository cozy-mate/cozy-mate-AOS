package umc.cozymate.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import umc.cozymate.data.model.RoomInfoRequest
import umc.cozymate.data.model.RoomInfoResponse

interface RoomService {

    @POST("/rooms/create")
    suspend fun postRoomsCreate(@Body request: RoomInfoRequest): Response<RoomInfoResponse>
}