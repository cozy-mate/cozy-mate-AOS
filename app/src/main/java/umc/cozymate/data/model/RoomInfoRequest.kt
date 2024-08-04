package umc.cozymate.data.model

data class RoomInfoRequest(
    val creatorId: Int,
    val maxMateNum: Int,
    val name: String,
    val profileImage: Int
)