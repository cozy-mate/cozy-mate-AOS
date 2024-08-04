package umc.cozymate.data.repository

import umc.cozymate.data.model.RoomInfoRequest
import umc.cozymate.data.model.RoomInfoResponse
import umc.cozymate.util.NetworkResult

interface RoomRepository {
    suspend fun sendRoomCreateInfo(request: RoomInfoRequest): NetworkResult<RoomInfoResponse>
}