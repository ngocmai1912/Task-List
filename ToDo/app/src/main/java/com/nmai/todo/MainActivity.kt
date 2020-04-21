package com.nmai.todo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    lateinit var tabLayout : TabLayout
    lateinit var viewPager : ViewPager
    val adapterViewPager = ViewPagerAdapter(supportFragmentManager)
    private val fragmentManager = supportFragmentManager // quan ly manager
    private val fragHome = Fragment_home()
    private val fragImportant = Fragment_important()
    private val fragDone = Fragment_done()
    private val fragNewTask = NewTask()
    lateinit var taskDelete : Task
    val fragmentTransaction = fragmentManager.beginTransaction()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Data.db = DataTask(this, null)
        fragmentTransaction.add(R.id.main, fragNewTask)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        //
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                val fragment : FragmentInterface = adapterViewPager.instantiateItem(viewPager, position) as FragmentInterface
                fragment.fragmentBecameVisible()
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
        })
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

        //

        //add
        val dialogDelete = DialogDelete()
        fragHome.onClickDelete = {
            dialogDelete.taskDelete = it
            dialogDelete.show(fragmentManager, "DialogDelete")
        }
       // Data.loadListData()
    }

    fun setupViewPager(viewPager: ViewPager){ // set fragment cho viewpager
        adapterViewPager.addFragment(listOf(fragHome, fragImportant, fragDone))
        viewPager.adapter = adapterViewPager
    }


}
