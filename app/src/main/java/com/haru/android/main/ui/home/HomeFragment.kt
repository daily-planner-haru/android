package com.haru.android.main.ui.home

import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.haru.android.R
import com.haru.android.databinding.FragmentHomeBinding
import com.haru.android.databinding.MainScheduleAdderBinding
import com.haru.android.ui.horizontal_date_picker.DatePickerListener
import com.haru.android.ui.horizontal_date_picker.HorizontalPicker
import org.joda.time.DateTime
import java.util.Calendar
import kotlin.math.abs


class HomeFragment : Fragment(), DatePickerListener, AdderListener {

    private var _binding: FragmentHomeBinding? = null
    private var _picker: HorizontalPicker? = null
    private var _selectedDate: DateTime? = DateTime()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val picker get() = _picker!!
    private val selectedDate get() = _selectedDate!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val todayBtn = binding.todayBtn
        // find the picker
        _picker = binding.mainDatePicker
        var listener = object : DatePickerListener {
            override fun onDateSelected(dateSelected: DateTime?) {
                Log.i("date picker","clicked me!")
            }
        }
        picker
            .setListener(listener)
            .setDays(20)
            .setOffset(10)
            .setDateSelectedColor(Color.DKGRAY)
            .setDateSelectedTextColor(Color.WHITE)
            .setMonthAndYearTextColor(Color.DKGRAY)
            .setTodayButtonTextColor(getColor(resources, R.color.colorPrimary, null))
            .setTodayDateTextColor(getColor(resources, R.color.colorPrimary,  null))
            .setTodayDateBackgroundColor(Color.GRAY)
            .setUnselectedDayTextColor(Color.DKGRAY)
            .setDayOfWeekTextColor(Color.DKGRAY)
            .setUnselectedDayTextColor(getColor(resources, R.color.textColor , null))
            .showTodayButton(false)
            .init();

        picker.setDate(DateTime())
        todayBtn.setOnClickListener {
            picker.setDate(DateTime())
        }

        // initialize it and attach a listener
        picker
            .setListener(this)
            .init()
        // initialize scheduler view
        val viewPager = binding.mainScheduleViewPager
        viewPager.adapter = ScheduleViewPagerAdapter(this)
        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        viewPager.setPageTransformer { page, position ->
             page.rotationY = position * -30f // Adjust the angle here as needed
            page.translationX = - pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
             page.alpha = 0.25f + (1 - abs(position))
        }
        viewPager.offscreenPageLimit = 1


        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewPager.addItemDecoration(itemDecoration)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDateSelected(dateSelected: DateTime?) {
        Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString())
        _selectedDate = dateSelected
    }
    class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

    }
    override fun onClickAddSchedule() {
        Log.i("date picker","clicked me!")
        val adderLayout = binding.adderLayout
        if(adderLayout.isEmpty()) {
            val binding = MainScheduleAdderBinding.inflate(LayoutInflater.from(context))
            val root = binding.root
            binding.adderDate.text = String.format("%s-%s", selectedDate.monthOfYear().asString, selectedDate.dayOfMonth().asString)
            // hour
            val calendar = Calendar.getInstance()

            // registry btn
            binding.registryBtn.setOnClickListener { onRegistrySchedule() }
            adderLayout.addView(root)
        }
    }

    override fun onRegistrySchedule() {
        val adderLayout = binding.adderLayout
        if(adderLayout.isNotEmpty()) {
            Toast.makeText(context, "registry schedule", Toast.LENGTH_LONG)
            adderLayout.removeAllViews()
        }
    }
}