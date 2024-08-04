package umc.cozymate.data.repository

import umc.cozymate.data.api.RoomService
import umc.cozymate.data.model.RoomInfoRequest
import umc.cozymate.data.model.RoomInfoResponse
import umc.cozymate.util.NetworkResult
import umc.cozymate.util.handleApi
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val api: RoomService
) : RoomRepository {
    override suspend fun sendRoomCreateInfo(request: RoomInfoRequest): NetworkResult<RoomInfoResponse> {
        return handleApi({api.postRoomsCreate(request) }){response: RoomInfoResponse -> response}
    }
}