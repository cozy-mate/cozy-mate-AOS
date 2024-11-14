package umc.cozymate.data.model.response.feed

import com.google.gson.annotations.SerializedName
import umc.cozymate.data.model.entity.PostInfo

data class PostListResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: List<PostInfo>
)
