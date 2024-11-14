package umc.cozymate.data.repository.repository

import retrofit2.Response
import umc.cozymate.data.model.response.feed.FeedInfoResponse
import umc.cozymate.data.model.response.feed.PostListResponse

interface FeedRepository {
    suspend fun getFeedInfo(accessToken: String, roomId: Int): Response<FeedInfoResponse>
    suspend fun getPostList(accessToken: String, roomId: Int, page: Int): Response<PostListResponse>
}