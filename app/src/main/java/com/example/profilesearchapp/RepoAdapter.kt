package com.example.profilesearchapp
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profilesearchapp.databinding.ItemRepoBinding
import com.example.profilesearchapp.local.GHRepo

class RepoAdapter(
    private var repoList: List<GHRepo>,
    private val onItemClick: (GHRepo) -> Unit
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    inner class RepoViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.binding.apply {
            tvRepoId.text = "ID: ${repo.id}"
            tvRepoName.text = "Name: ${repo.name}"
            tvUserName.text = "Owner: ${repo.owner.login}"
            root.setOnClickListener { onItemClick(repo) }
        }
    }

    override fun getItemCount(): Int = repoList.size

    fun updateData(newList: List<GHRepo>) {
        repoList = newList
        notifyDataSetChanged()
    }
}
