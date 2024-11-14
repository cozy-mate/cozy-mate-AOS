package umc.cozymate.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import umc.cozymate.data.model.response.feed.FeedInfoResponse
import umc.cozymate.data.model.response.feed.PostListResponse

interface FeedService {

    // 피드 정보 조회
    @GET("/feed/{roomId}")
    suspend fun getFeedInfo(
        @Header("Authorization") accessToken: String,
        @Path("roomId") roomId : Int
    ): Response<FeedInfoResponse>

    // 피드 게시글 불러오기
    @GET("/post/{roomId}")
    suspend fun getPostList(
        @Header("Authorization") accessToken: String,
        @Path("roomId") roomId : Int,
        @Query("page") page : Int
    ) : Response<PostListResponse>


}