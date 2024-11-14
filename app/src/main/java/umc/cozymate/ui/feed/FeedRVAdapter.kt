package umc.cozymate.ui.feed

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.cozymate.R
import umc.cozymate.data.model.entity.PostInfo
import umc.cozymate.databinding.RvItemFeedBinding
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class FeedRVAdapter(
    private var items: List<PostInfo>
): RecyclerView.Adapter<FeedRVAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: RvItemFeedBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: PostInfo ){

            val dateFormat = SimpleDateFormat("yy-MM-dd")
            binding.tvFeedNickname.text = item.nickname
            binding.tvFeedText.text = item.content
            binding.tvFeedTime.text = setDate(item.createdAt)
            binding.tvFeedCommentCount.text = item.commentCount.toString()
            binding.ivFeedPeresona.setImageResource(initCharactor(item.persona))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemFeedBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun setDate(d : String): String {
        // 서버에서 받은 시간을 LocalDateTime 형식으로 파싱
        val parsedDate = try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            LocalDateTime.parse(d, formatter)
        } catch (e: Exception) {
            Log.d("DateParsing", "Invalid date format: $e")
            return ""
        }

        // 현재 시간 (로컬 시간대)
        val currentDate = LocalDateTime.now()

        // 시간 차이 계산
        val diff = Duration.between(parsedDate, currentDate)
        val diffHours = diff.toHours()
        val diffDays = diff.toDays()
        val diffMinutes = diff.toMinutes()
        val result = when {
            diffMinutes < 60 -> "${diffMinutes}분 전" // 1시간 이내는 분 단위로 표시
            diffHours < 24 -> "${diffHours}시간 전" // 24시간 이내
            diffDays < 3 -> "${diffDays}일 전" // 3일 이내
            else -> {
                val defaultFormat = DateTimeFormatter.ofPattern("yy-MM-dd", Locale.getDefault())
                    .withZone(ZoneId.systemDefault())
                defaultFormat.format(parsedDate) // 3일 이상
            }
        }

        return result
    }

    private fun initCharactor(persona : Int) : Int{
        return when (persona) {
            1 -> R.drawable.character_1
            2 -> R.drawable.character_2
            3 -> R.drawable.character_3
            4 -> R.drawable.character_4
            5 -> R.drawable.character_5
            6 -> R.drawable.character_6
            7 -> R.drawable.character_7
            8 -> R.drawable.character_8
            9 -> R.drawable.character_9
            10 -> R.drawable.character_10
            11 -> R.drawable.character_11
            12 -> R.drawable.character_12
            13 -> R.drawable.character_13
            14 -> R.drawable.character_14
            15 -> R.drawable.character_15
            16 -> R.drawable.character_16
            else -> R.drawable.character_1 // 기본 이미지 설정
        }
    }
}