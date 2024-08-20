package umc.cozymate.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import umc.cozymate.R
import umc.cozymate.databinding.ActivityMainBinding
import umc.cozymate.firebase.FCMService
import umc.cozymate.ui.cozy_home.CozyHomeActiveFragment
import umc.cozymate.ui.cozy_home.CozyHomeDefaultFragment
import umc.cozymate.ui.cozy_home.CozyHomeViewModel
import umc.cozymate.ui.feed.FeedFragment
import umc.cozymate.ui.my_page.MyPageFragment
import umc.cozymate.ui.role_rule.RoleAndRuleFragment
import umc.cozymate.ui.roommate.RoommateFragment
import umc.cozymate.ui.roommate.RoommateMakeCrewableFragment
import umc.cozymate.ui.roommate.RoommateOnboardingFragment
import umc.cozymate.util.navigationHeight
import umc.cozymate.util.setStatusBarTransparent

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val homeViewModel: CozyHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigationView()

        initScreen()

        // 현재 참여 중인 방이 있다면, CozyHomeActiveFragment로 이동
        homeViewModel.getRoomId()
        homeViewModel.roomId.observe(this) { roomId ->
            if (roomId != 0) {
                loadActiveFragment()
            } else {
                loadDefaultFragment()
            }
        }

        // Check and fetch RoomId if needed
        //homeViewModel.fetchRoomIdIfNeeded()

        // 앱 초기 실행 시 홈화면으로 설정
//        if (savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemId = R.id.fragment_home
//
//
//        }
        if (savedInstanceState == null) {
            val navigateTo = intent.getStringExtra("navigate_to")
            if (navigateTo == "RoommateOnboarding") {
                // RoommateOnboardingFragment로 이동
                switchToRoommateOnboardingFragment()
            }
            else if (navigateTo == "RoommateMakeCrewable") {
                switchToRoommateMakeCrewableFragment()
            }
            else {
                // 기본 홈 화면 설정
                binding.bottomNavigationView.selectedItemId = R.id.fragment_home
            }
        }

        FCMService().getFirebaseToken()
        // 알림 확인을 위해 작성, 추후 삭제 요망
    }

    private fun initScreen() {
        this.setStatusBarTransparent()
        binding.main.setPadding(0, 0, 0, this.navigationHeight())
    }

    // [코지홈 비활성화] 로드
    fun loadDefaultFragment() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, CozyHomeDefaultFragment())
            .addToBackStack(null)
            .commit()
    }

    // [코지홈 활성화] 로드
    fun loadActiveFragment() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, CozyHomeActiveFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun switchToRoommateOnboardingFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, RoommateOnboardingFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun switchToRoommateMakeCrewableFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, RoommateMakeCrewableFragment())
            .commit()
    }


    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    if (homeViewModel.roomId.value != 0) {
                        loadActiveFragment()
                    } else {
                        loadDefaultFragment()
                    }

                    true
                }

                R.id.fragment_feed -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, FeedFragment()).commit()
                    true
                }

                R.id.fragment_role_and_rule -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, RoleAndRuleFragment()).commit()
                    true
                }

                R.id.fragment_rommate -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, RoommateFragment()).commit()
                    true
                }

                R.id.fragment_mypage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, MyPageFragment()).commit()
                    true
                }

                else -> false
            }
        }
    }
}
