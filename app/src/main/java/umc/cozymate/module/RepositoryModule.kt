package umc.cozymate.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import umc.cozymate.data.api.RoomService
import umc.cozymate.data.repository.RoomRepository
import umc.cozymate.data.repository.RoomRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providesRoomRepository(
        roomService: RoomService
    ): RoomRepository = RoomRepositoryImpl(roomService)
}