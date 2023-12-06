package com.capstone.pesonapusaka.ui.onboarding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.databinding.ViewpagerSlideItemContainerBinding

class OnboardingAdapter: RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    private var onBoardingItems: MutableList<OnBoardingItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ViewpagerSlideItemContainerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OnboardingViewHolder(binding)
    }

    override fun getItemCount(): Int = onBoardingItems.size

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        with(holder) {
            with(onBoardingItems[position]) {
                binding.tvOnboarding.text = this.title
                binding.ivOnboarding.setImageResource(this.img)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewItem(data: List<OnBoardingItem>) {
        onBoardingItems.clear()
        onBoardingItems.addAll(data)
        notifyDataSetChanged()
    }

    inner class OnboardingViewHolder(val binding: ViewpagerSlideItemContainerBinding): RecyclerView.ViewHolder(binding.root)
}