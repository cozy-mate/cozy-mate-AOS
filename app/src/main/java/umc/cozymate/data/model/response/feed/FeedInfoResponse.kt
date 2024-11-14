package umc.cozymate.data.model.response.feed

import com.google.gson.annotations.SerializedName

data class FeedInfoResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: Result
){
    inner class Result(
        @SerializedName("name")
        val name : String,
        @SerializedName("description")
        val diescription : String
    )
}
