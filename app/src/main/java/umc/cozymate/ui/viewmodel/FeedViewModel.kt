package umc.cozymate.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import umc.cozymate.data.model.response.feed.FeedInfoResponse
import umc.cozymate.data.model.response.feed.PostListResponse
import umc.cozymate.data.repository.repository.FeedRepository
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    val repository: FeedRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _getFeedInfoResponse = MutableLiveData<Response<FeedInfoResponse>>()
    val getFeedInfoResponse : LiveData<Response<FeedInfoResponse>> get() = _getFeedInfoResponse

    private val _getPostListResponse = MutableLiveData<Response<PostListResponse>>()
    val getPostListResponse : LiveData<Response<PostListResponse>> get() = _getPostListResponse

    fun getToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun getPostList(roomId : Int, page : Int){
        viewModelScope.launch {
            val token = getToken()
            try {
                val response = repository.getPostList(token!!, roomId, page)
                if(response.isSuccessful){
                    Log.d(TAG,"응답 성공: ${response.body()!!.result}")
                    _getPostListResponse.postValue(response)
                }
                else Log.d(TAG,"응답 실패: ${response.body()!!.result}")
            }catch (e : Exception){
                Log.d(TAG,"api 요청 실패: ${e}")
            }
        }
    }

    fun getFeedInfo(roomId: Int){
        viewModelScope.launch {
            val token = getToken()
            try {
                val response = repository.getFeedInfo(token!!, roomId)
                if(response.isSuccessful){
                    Log.d(TAG,"응답 성공: ${response.body()!!.result}")
                    _getFeedInfoResponse.postValue(response)
                }
                else Log.d(TAG,"응답 실패: ${response.body()!!.result}")
            }catch (e : Exception){
                Log.d(TAG,"api 요청 실패: ${e}")
            }
        }
    }
}