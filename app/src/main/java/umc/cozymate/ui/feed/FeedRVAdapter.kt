package umc.cozymate.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.cozymate.databinding.RvItemFeedBinding


class FeedRVAdapter(
    private var items: List<String>
): RecyclerView.Adapter<FeedRVAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: RvItemFeedBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: String ){
            binding.tvFeedNickname.text = item
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
}