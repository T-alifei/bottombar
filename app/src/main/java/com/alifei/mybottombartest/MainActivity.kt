package com.alifei.mybottombartest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBottomBar.setSelectedIcon(
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round
        )

        mBottomBar.setUnSelectedIcon(
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
        )
        mBottomBar.setTitle("标签1", "标签2", "标签3", "标签4", "标签5")

        val arrayList = ArrayList<Fragment>()
        arrayList.add(HomeFragment1())
        arrayList.add(HomeFragment2())
        arrayList.add(HomeFragment3())
        arrayList.add(HomeFragment1())
        arrayList.add(HomeFragment2())

        mBottomBar.showContent(supportFragmentManager, R.id.fl_main, arrayList)
        mBottomBar.currentTab = 2
//        mBottomBar.showContent(this, R.id.fl_main, arrayList)
        mBottomBar.showDot(2)
    }
}
