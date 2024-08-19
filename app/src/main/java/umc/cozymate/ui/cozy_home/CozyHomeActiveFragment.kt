package umc.cozymate.ui.cozy_home

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.cozymate.R
import umc.cozymate.databinding.FragmentCozyhomeActiveBinding
import umc.cozymate.ui.cozy_home.adapter.AchievementsAdapter

@AndroidEntryPoint
class CozyHomeActiveFragment : Fragment() {

    private lateinit var binding: FragmentCozyhomeActiveBinding
    private val viewModel: CozyHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cozyhome_active, container, false)

        with(binding){
            binding.viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.fetchRoomIdIfNeeded()

        val savedRoomId = viewModel.getSavedRoomId()

        if (savedRoomId == 0) {
            // SharedPreferences에 방 ID가 저장되어 있지 않다면 getRoomId 호출
            viewModel.getRoomId()
        } else {
            // 방 ID가 이미 저장되어 있다면 roomId에 값을 설정
            viewModel.setRoomId(savedRoomId)
        }

        viewModel.roomId.observe(viewLifecycleOwner) { id ->
            if (id != null && id != 0) {
                // 방 ID가 null이 아니면 방 정보를 가져옴
                observeViewModel()
            }
        }

        initAchievmentList()
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel() {
        observeName()
        observeProfile()
        observeInviteCode()
    }

    private fun observeName() {
        viewModel.roomName.observe(viewLifecycleOwner, Observer { name ->
            if (name != null) {
                binding.tvWhoseRoom.text = name
            }
        })

        binding.ivChar.setImageResource(R.drawable.character_0)

    }

    private fun observeInviteCode() {
        viewModel.inviteCode.observe(viewLifecycleOwner, Observer { code ->
            if (code != null) {
                binding.btnCopyInviteCode.text = code
            }
        })
    }

    private fun observeProfile() {
        viewModel.profileImage.observe(viewLifecycleOwner, Observer { img ->
            if (img != null) {
                when (img) {
                    1 -> binding.ivChar.setImageResource(R.drawable.character_1)
                    2 -> binding.ivChar.setImageResource(R.drawable.character_2)
                    3 -> binding.ivChar.setImageResource(R.drawable.character_3)
                    4 -> binding.ivChar.setImageResource(R.drawable.character_4)
                    5 -> binding.ivChar.setImageResource(R.drawable.character_5)
                    6 -> binding.ivChar.setImageResource(R.drawable.character_6)
                    7 -> binding.ivChar.setImageResource(R.drawable.character_7)
                    8 -> binding.ivChar.setImageResource(R.drawable.character_8)
                    9 -> binding.ivChar.setImageResource(R.drawable.character_9)
                    10 -> binding.ivChar.setImageResource(R.drawable.character_10)
                    11 -> binding.ivChar.setImageResource(R.drawable.character_11)
                    12 -> binding.ivChar.setImageResource(R.drawable.character_12)
                    13 -> binding.ivChar.setImageResource(R.drawable.character_13)
                    14 -> binding.ivChar.setImageResource(R.drawable.character_14)
                    15 -> binding.ivChar.setImageResource(R.drawable.character_15)
                    else -> binding.ivChar.setImageResource(R.drawable.character_0) // 기본 이미지 설정
                }
            }
        })
    }


    private fun initAchievmentList() {
        val adapter = AchievementsAdapter(viewModel.achievements.value!!)
        viewModel.loadAchievements()

        binding.rvAcheivement.adapter = adapter
        binding.rvAcheivement.layoutManager = LinearLayoutManager(requireContext())
        viewModel.achievements.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }

    private fun initView() {

        val tvWhoseRoom = binding.tvWhoseRoom
        val spannableString = SpannableString(tvWhoseRoom.text)

        // 버튼 내 텍스트 스타일 변경
        spannableString.setSpan(
            TextAppearanceSpan(requireContext(), R.style.TextAppearance_App_18sp_SemiBold),
            4,
            tvWhoseRoom.text.length-7,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val color = ContextCompat.getColor(requireContext(), R.color.main_blue)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            4,
            tvWhoseRoom.text.length-7,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvWhoseRoom.text = spannableString
    }
}