package umc.cozymate.data.model.entity

import com.google.gson.annotations.SerializedName

data class PostInfo(
    @SerializedName("id")
    val id : Int,
    @SerializedName("writerId")
    val writerId : Int,
    @SerializedName("content")
    val content : String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("persona")
    val persona : Int,
    @SerializedName("createdAt")
    val createdAt : String,
    @SerializedName("imageList")
    val imageList : List<String>,
    @SerializedName("commentCount")
    val commentCount : Int
)
