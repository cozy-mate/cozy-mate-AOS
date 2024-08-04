package umc.cozymate.ui.roommate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import umc.cozymate.data.model.RoomInfoRequest
import umc.cozymate.data.repository.RoomRepositoryImpl
import umc.cozymate.util.onSuccess
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepositoryImpl
): ViewModel() {

    private val _name = MutableStateFlow<String>("")
    val name = _name.asStateFlow()

    fun sendRoomInfo(request: RoomInfoRequest){
        viewModelScope.launch(Dispatchers.IO) {
            repository.sendRoomCreateInfo(request).onSuccess { response ->
                Log.d("RoomViewModel", "성공!")
            }
        }
    }
}