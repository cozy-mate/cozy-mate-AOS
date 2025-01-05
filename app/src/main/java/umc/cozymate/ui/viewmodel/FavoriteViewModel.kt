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
import umc.cozymate.data.model.response.favorites.GetFavoritesMembersResponse
import umc.cozymate.data.model.response.favorites.GetFavoritesRoomsResponse
import umc.cozymate.data.repository.repository.FavoritesRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: FavoritesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val TAG = this.javaClass.simpleName
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    fun getToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    // 내가 찜한 방 목록
    private val _isLoading1 = MutableLiveData(false)
    val isLoading1: LiveData<Boolean> get() = _isLoading1
    private val _getFavoritesRoomsResponse = MutableLiveData<GetFavoritesRoomsResponse>()
    val getFavoritesRoomsResponse: LiveData<GetFavoritesRoomsResponse> get() = _getFavoritesRoomsResponse
    suspend fun getFavoriteRoomList() {
        _isLoading1.value = true
        val token = getToken()
        try {
            val response = repo.getFavoritesRooms(token!!)
            if (response.isSuccessful) {
                Log.d(TAG, "getFavoritesRooms 응답 성공 : ${response.body()}")
                _getFavoritesRoomsResponse.value = response.body()
            } else {
                Log.d(TAG, "getFavoritesRooms 응답 에러 : ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "getFavoritesRooms api 요청 실패: ${e}")
        } finally {
            _isLoading1.value = false
        }
    }

    // 내가 찜한 룸메이트 목록
    private val _isLoading2 = MutableLiveData(false)
    val isLoading2: LiveData<Boolean> get() = _isLoading2
    private val _getFavoritesMembersResponse = MutableLiveData<GetFavoritesMembersResponse>()
    val getFavoritesMembersResponse: LiveData<GetFavoritesMembersResponse> get() = _getFavoritesMembersResponse
    suspend fun getFavoriteRoommateList() {
        _isLoading2.value = true
        val token = getToken()
        try {
            val response = repo.getFavoritesMembers(token!!)
            if (response.isSuccessful) {
                Log.d(TAG, "getFavoritesRooms 응답 성공 : ${response.body()}")
                _getFavoritesMembersResponse.value = response.body()
            } else {
                Log.d(TAG, "getFavoritesRooms 응답 에러 : ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "getFavoritesRooms api 요청 실패: ${e}")
        } finally {
            _isLoading2.value = false
        }
    }

    fun sendFavoriteRoom(roomId: Int) {
        val token = getToken()!!
        Log.d(TAG, "방 찜하기 : ${roomId}")
        viewModelScope.launch {
            val response = repo.sendFavoritesRooms(token, roomId)
            if (response.body()!!.isSuccess) {
                Log.d(TAG, "방 찜하기 성공 : ${response.body()!!.result}")
            } else {
                Log.d(TAG, "방 짬하기 실패 : ${response.body()}")
            }
        }
    }

    fun sendFavoriteMember(memberId: Int) {
        val token = getToken()!!
        Log.d(TAG, "맴버 찜하기 : ${memberId}")
        viewModelScope.launch {
            val response = repo.sendFavoritesMember(token, memberId)
            if (response.body()!!.isSuccess) {
                Log.d(TAG, "멤버 찜하기 성공 : ${response.body()!!.result}")
            } else {
                Log.d(TAG, "멤버 찜하기 실패 : ${response.body()!!.result}")
            }
        }
    }

    fun deleteFavoriteRoomMember(id: Int) {
        val token = getToken()!!
        Log.d(TAG, "찜하기 취소 : ${id}")
        viewModelScope.launch {
            try {
                val response = repo.deleteFavoritesRoomMember(token, id)
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    Log.d(TAG, "찜하기 취소 성공 : ${response.body()?.result}")
                } else {
                    Log.e(TAG, "API 응답 실패: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "API 호출 중 예외 발생: $e")
            }
        }
    }
}
