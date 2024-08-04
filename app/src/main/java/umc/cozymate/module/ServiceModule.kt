package umc.cozymate.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import umc.cozymate.data.api.RoomService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private inline fun <reified T> Retrofit.buildService(): T{
        return this.create(T::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): RoomService {
        return retrofit.buildService()
    }
}