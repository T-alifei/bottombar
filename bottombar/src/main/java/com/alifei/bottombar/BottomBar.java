package com.alifei.bottombar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewParent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * 继承CommonTabLayout
 */
public class BottomBar extends CommonTabLayout {
    private int[] unSelectedIcon; //未选中图标
    private int[] selectedIcon; //选中图标
    private String[] title; //设置标题
    private boolean isSetUnSelectedIcon = false;
    private boolean isSetSelectedIcon = false;
    private boolean isSetTitle = false;
    private boolean isShowContent = false;
    private FragmentManager supportFragmentManager = null;
    private int containerViewId;
    private ArrayList<Fragment> fragments = null;
    private int length = 0;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //默认字体颜色
        this.setTextSelectColor(Color.BLACK);
        this.setTextUnselectColor(Color.BLACK);
    }

    /**
     * @param parameter 设置标题
     */
    public BottomBar setTitle(String... parameter) {
        isSetTitle = true;
        this.title = parameter;
        return this;
    }

    /**
     * @param textSelectColor   文字选择颜色
     * @param textUnSelectColor 文字取消选择颜色
     * @param parameter         设置标题
     * @return 返回当前对象
     */
    public BottomBar setTitle(int textSelectColor, int textUnSelectColor, String... parameter) {
        this.setTextSelectColor(textSelectColor);
        this.setTextUnselectColor(textUnSelectColor);
        this.title = parameter;
        isSetTitle = true;
        return this;
    }

    /**
     * @param parameter 设置未选中的图标
     */
    public BottomBar setUnSelectedIcon(int... parameter) {
        isSetUnSelectedIcon = true;
        this.unSelectedIcon = parameter;
        return this;
    }

    /**
     * 可以设置未选中图标的宽和高
     *
     * @param parameter 设置未选中的图标
     */
    public BottomBar setUnSelectedIcon(float iconWidth, float iconHeight, int... parameter) {
        if (!isSetSelectedIcon) { //没有设置过选中图片
            this.setIconHeight(iconHeight);
            this.setIconWidth(iconWidth);
        }

        this.unSelectedIcon = parameter;
        isSetUnSelectedIcon = true;
        return this;
    }

    /**
     * @param parameter 设置选中的图标
     */
    public BottomBar setSelectedIcon(int... parameter) {
        isSetSelectedIcon = true;
        this.selectedIcon = parameter;
        return this;
    }

    /**
     * @param parameter 设置选中的图标
     */
    public BottomBar setSelectedIcon(float iconWidth, float iconHeight, int... parameter) {
        if (!isSetUnSelectedIcon) { //没有设置过未选中图片
            this.setIconHeight(iconHeight);
            this.setIconWidth(iconWidth);
        }
        this.selectedIcon = parameter;
        isSetSelectedIcon = true;
        return this;
    }

    /**
     * 显示
     */
    public void showContent() {
        //填充数据
        this.setTabData(getShowContent());
    }

    /**
     * @param supportFragmentManager 支持片段管理器
     * @param containerViewId        容器查看ID
     * @param fragments              碎片
     */
    public void showContent(FragmentManager supportFragmentManager, int containerViewId, ArrayList<Fragment> fragments) {
        showContent();
        this.supportFragmentManager = supportFragmentManager;
        this.containerViewId = containerViewId;
        this.fragments = fragments;
        this.isShowContent = true;

        //默认下标为0
        setSwitchFragmentIndex(0);
    }

    /**
     * 设置 选中fragment的下标
     *
     * @param index 下标
     */
    private void setSwitchFragmentIndex(int index) {
        if (length != fragments.size() || length == 0) {
            throw new IllegalArgumentException("Fragments are different in length  ......");
        }
        if (index >= fragments.size() || index < 0) {
            throw new IllegalArgumentException("index < fragments.size() && index > 0 ......");
        }
        switchFragment(fragments.get(index));
    }

    //实现
    public void showContent(FragmentActivity fa, int containerViewId, ArrayList<Fragment> fragments) {
        this.setTabData(getShowContent(), fa, containerViewId, fragments);
    }


    @Override
    public void setCurrentTab(int currentTab) {
        super.setCurrentTab(currentTab);
        if (isShowContent) {
            setSwitchFragmentIndex(currentTab);
        }
    }

    /**
     * @return 设置要显示的数据
     */
    private ArrayList<CustomTabEntity> getShowContent() {
        ArrayList<CustomTabEntity> tabEntityList = new ArrayList<>();

        //显示图片与文本
        if (isSetTitle && isSetSelectedIcon && isSetUnSelectedIcon) {
            //进行长度判断
            if (title.length == selectedIcon.length && selectedIcon.length == unSelectedIcon.length) {
                for (int i = 0; i < title.length; i++) {
                    tabEntityList.add(new TabEntity(title[i], selectedIcon[i], unSelectedIcon[i]));
                }
                length = title.length; //长度
                return tabEntityList;
            } else {
                throw new IllegalArgumentException("title、selectedIcon and unSelectedIcon  are different in length......");
            }
        }

        //只显示图片
        if (isSetSelectedIcon && isSetUnSelectedIcon) {
            //进行长度判断
            if (selectedIcon.length == unSelectedIcon.length) {
                for (int i = 0; i < selectedIcon.length; i++) {
                    tabEntityList.add(new TabEntity(selectedIcon[i], unSelectedIcon[i]));
                }
                length = selectedIcon.length; //长度
                return tabEntityList;
            } else {
                throw new IllegalArgumentException("selectedIcon and unSelectedIcon  are different in length......");
            }
        }

        //只显示为选中的图片
        if (isSetUnSelectedIcon) {
            for (int value : unSelectedIcon) {
                tabEntityList.add(new TabEntity(value));
            }
            length = unSelectedIcon.length; //长度
            return tabEntityList;
        }

        //只显示文本
        if (isSetTitle) {
            for (String str : title) {
                tabEntityList.add(new TabEntity(str));
            }
            length = title.length; //长度
            return tabEntityList;
        }

        length = 0;
        return tabEntityList;
    }

//    /**
//     * @return 返回boolean 值
//     */
//    private boolean isSetIconAll() {
//        if (isSetSelectedIcon && isSetUnSelectedIcon) { //是否全部设置
//            //数组长度是否相同
//            return selectedIcon.length == unSelectedIcon.length;
//        } else if (isSetSelectedIcon || isSetUnSelectedIcon) {
//            throw new IllegalArgumentException("selectedIcon or unSelectedIcon of null......");
//        }
//        return false;
//    }


    private Fragment currentFragment = null;

    /**
     * @param targetFragment 要切换的fragment
     */
    private void switchFragment(Fragment targetFragment) {
        ViewParent parent = this.getParent();
        Log.i("1111", "switchFragment: " + parent.getClass().toString());

        if (supportFragmentManager == null) {
            return;
        }
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();

        if (!targetFragment.isAdded()) {
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }

            transaction.add(containerViewId, targetFragment, targetFragment.getClass().getName()).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(targetFragment).commitAllowingStateLoss();
        }

        currentFragment = targetFragment;
    }


    /**
     * 赋值
     */
    private class TabEntity implements CustomTabEntity {
        private String title = null;
        private int selectedIcon = 0;
        private int unSelectedIcon = 0;

        TabEntity(String title) {
            this.title = title;
        }

        TabEntity(int unSelectedIcon) {
            this.unSelectedIcon = unSelectedIcon;
        }

        TabEntity(int selectedIcon, int unSelectedIcon) {
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }
}
