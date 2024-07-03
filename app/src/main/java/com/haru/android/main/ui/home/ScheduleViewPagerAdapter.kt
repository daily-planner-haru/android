package com.haru.android.main.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.haru.android.main.ui.home.schedule.DailyRoutineSchedulerFragment
import com.haru.android.main.ui.home.schedule.DailySchedulerFragment

class ScheduleViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        private const val FRAGMENT_SIZE: Int = 2;
    }

    override fun getItemCount(): Int {
        return FRAGMENT_SIZE
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DailySchedulerFragment()
            1 -> DailyRoutineSchedulerFragment()
            else -> DailySchedulerFragment()
        }
    }
}