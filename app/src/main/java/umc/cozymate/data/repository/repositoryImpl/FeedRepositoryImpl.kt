package umc.cozymate.data.repository.repositoryImpl

import retrofit2.Response
import umc.cozymate.data.api.FeedService
import umc.cozymate.data.model.response.feed.FeedInfoResponse
import umc.cozymate.data.model.response.feed.PostListResponse
import umc.cozymate.data.repository.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api : FeedService
): FeedRepository {
    override suspend fun getFeedInfo(
        accessToken: String,
        roomId: Int,
    ): Response<FeedInfoResponse> {
        return api.getFeedInfo(accessToken,roomId)
    }

    override suspend fun getPostList(
        accessToken: String,
        roomId: Int,
        page: Int
    ): Response<PostListResponse>{
        return api.getPostList(accessToken, roomId, page)
    }
}