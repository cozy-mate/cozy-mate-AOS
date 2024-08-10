package umc.cozymate

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import umc.cozymate.util.NetworkConnectionChecker


@HiltAndroidApp
class CozyMateApplication: Application(), DefaultLifecycleObserver {
    override fun onCreate() {
        super<Application>.onCreate()

        // Kakao Sdk 초기화
        KakaoSdk.init(this, "a51292da8bc69b2160b0bb97ec00b2ad")

        context = applicationContext
        networkConnectionChecker = NetworkConnectionChecker(context)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "cozymate",
                "Cozymate",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // kakao SDK 초기화
        KakaoSdk.init(this, "a51292da8bc69b2160b0bb97ec00b2ad")
    }

    override fun onStop(owner: LifecycleOwner){
        networkConnectionChecker.unregister()
        super.onStop(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        networkConnectionChecker.register()
        super.onStart(owner)
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getString(@StringRes stringResId: Int): String{
            return context.getString(stringResId)
        }

        private lateinit var networkConnectionChecker: NetworkConnectionChecker
        fun isOnline() = networkConnectionChecker.isOnline()

        private lateinit var instance: CozyMateApplication
        fun applicationContext(): Context{
            return instance.applicationContext
        }
    }
}