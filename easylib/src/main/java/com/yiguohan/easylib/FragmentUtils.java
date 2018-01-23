package com.yiguohan.easylib;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import java.util.Collections;
import java.util.List;

/**
 * Fragment相关工具类
 * Created by yiguohan on 2017/11/20.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class FragmentUtils {

    public static final String ARGS_ID = "args_id";
    public static final String ARGS_IS_HIDE = "args_hide";
    public static final String ARGS_IS_ADD_STACK = "args_is_add_stack";
    public static final int TYPE_ADD_FRAGMENT = 0x01;
    public static final int TYPE_SHOW_FRAGMENT = 0x01 << 1;
    public static final int TYPE_HIDE_FRAGMENT = 0x01 << 2;

    /**
     * 添加 Fragment 3 参数重载
     *
     * @param fm          FragmentManager对象
     * @param target      待添加的Fragment
     * @param containerId 布局Id
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId) {
        add(fm, target, containerId, false);
    }

    /**
     * 添加 Fragment 4 参数重载
     *
     * @param fm          FragmentManager对象
     * @param target      待添加的Fragment
     * @param containerId 布局Id
     * @param isHide      是否隐藏
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId,
                           final boolean isHide) {
        add(fm, target, containerId, isHide, false);
    }

    /**
     * 添加 Fragment 4 参数重载
     *
     * @param fm            FragmentManager对象
     * @param target        待添加的Fragment
     * @param containerId   布局Id
     * @param shareElements 共享元素
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId,
                           @NonNull final View... shareElements) {
        add(fm, target, containerId, false, shareElements);
    }

    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final List<Fragment> target,
                           @IdRes final int containerId,
                           final int showIndex) {
        add(fm, target.toArray(new Fragment[target.size()]), containerId, showIndex);
    }

    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment[] target,
                           @IdRes final int containerId,
                           final int showIndex) {
        for (int i = 0, len = target.length; i < len; ++i) {
            putArgs(target[i], new Args(containerId, showIndex != i, false));
        }
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, target);
    }

    /**
     * 添加 Fragment 5 参数重载
     *
     * @param fm             FragmentManager对象
     * @param target         待添加的Fragment
     * @param containerId    布局Id
     * @param isAddStack     是否入回退栈
     * @param shareLelements 共享元素
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @NonNull final View... shareLelements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(target, new Args(containerId, false, isAddStack));
        addSharedElement(ft, shareLelements);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, target);
    }

    /**
     * 添加 Fragment 5 参数重载
     *
     * @param fm          FragmentManager对象
     * @param target      待添加的Fragment
     * @param containerId 布局Id
     * @param isHide      是否隐藏
     * @param isAddStack  是否入回退栈
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId,
                           final boolean isHide,
                           final boolean isAddStack) {
        putArgs(target, new Args(containerId, isHide, isAddStack));
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, target);
    }

    /**
     * 添加 Fragment 5 参数重载
     *
     * @param fm          FragmentManager对象
     * @param target      待添加的Fragment
     * @param containerId 布局Id
     * @param enterAnim   进场动画Id
     * @param exitAnim    退场动画Id
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, target, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 添加 Fragment 6 参数重载
     *
     * @param fm          FragmentManager对象
     * @param target      待添加的Fragment
     * @param containerId 布局Id
     * @param isAddStack  是否入回退栈
     * @param enterAnim   进场动画Id
     * @param exitAnim    退场动画Id
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, target, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 添加 Fragment 7 参数重载
     *
     * @param fm           FragmentManager对象
     * @param target       待添加的Fragment
     * @param containerId  布局Id
     * @param enterAnim    进场动画Id
     * @param exitAnim     退场动画Id
     * @param popEnterAnim 以退回键呼出的进场动画
     * @param popExitAnim  以退回键呼出的退场动画
     */
    public static void add(@NonNull FragmentManager fm,
                           @NonNull Fragment target,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        add(fm, target, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 添加 Fragment 8 参数重载
     *
     * @param fm           FragmentManager对象
     * @param target       待添加的Fragment
     * @param containerId  布局Id
     * @param isAddStack   是否入回退栈
     * @param enterAnim    进场动画Id
     * @param exitAnim     退场动画Id
     * @param popEnterAnim 以退回键呼出的进场动画
     * @param popExitAnim  以退回键呼出的退场动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment target,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(target, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, target);
    }

    /**
     * 显示 fragment
     *
     * @param show 要显示的fragment
     */
    public static void show(@NonNull final Fragment show) {
        putArgs(show, false);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_FRAGMENT, null, show);
    }

    /**
     * 显示 fragment
     *
     * @param fm FragmentManager对象
     */
    public static void show(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment show : fragments) {
            putArgs(show, false);
        }
        operateNoAnim(fm, TYPE_SHOW_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    /**
     * 隐藏 fragment
     *
     * @param hide 要隐藏的fragment
     */
    public static void hide(@NonNull final Fragment hide) {
        putArgs(hide, true);
        operateNoAnim(hide.getFragmentManager(), TYPE_HIDE_FRAGMENT, null, hide);
    }

    /**
     * 隐藏 fragment
     *
     * @param fm FragmentManager对象
     */
    public static void hide(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment hide : fragments) {
            putArgs(hide, true);
        }
        operateNoAnim(fm, TYPE_HIDE_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    /**
     * 获取同级别的 fragment
     *
     * @param fm FragmentManager对象
     * @return FragmentManager对象中的fragment
     */
    public static List<Fragment> getFragments(@NonNull final FragmentManager fm) {
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.isEmpty()) {
            return Collections.emptyList();
        }
        return fragments;
    }

    /**
     * Fragment操作事务方法
     *
     * @param type                操作类型
     * @param fragmentManager     FragmentManager对象
     * @param fragmentTransaction FragmentTransaction对象
     * @param src                 源Fragment
     * @param dests               目标Fragment
     */
    private static void operate(final int type, @NonNull FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, Fragment src, Fragment... dests) {
        if (src != null && src.isRemoving()) {
            Log.e("FragmentUtils", src.getClass().getName() + "is removing");
            return;
        }
        String name;
        Bundle args;
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                for (Fragment fragment : dests) {
                    name = fragment.getClass().getName();
                    args = fragment.getArguments();
                    Fragment fragmentByTag = fragmentManager.findFragmentByTag(name);
                    if (fragment != null && fragmentByTag.isAdded()) {
                        fragmentTransaction.remove(fragmentByTag);
                    }
                    fragmentTransaction.add(args.getInt(ARGS_ID), fragment, name);
                    if (args.getBoolean(ARGS_IS_HIDE)) {
                        fragmentTransaction.hide(fragmentByTag);
                    }
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) {
                        fragmentTransaction.addToBackStack(name);
                    }
                }
                break;

        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 无动画过渡的操作事务方法
     *
     * @param fm
     * @param type
     * @param src
     * @param dest
     */
    private static void operateNoAnim(final FragmentManager fm,
                                      final int type,
                                      final Fragment src,
                                      Fragment... dest) {
        FragmentTransaction ft = fm.beginTransaction();
        operate(type, fm, ft, src, dest);
    }

    /**
     * 添加进场退场动画
     *
     * @param ft       FragmentTransaction对象
     * @param enter    进场动画
     * @param exit     退场动画
     * @param popEnter 通过按下返回键的进场动画
     * @param popExit  通过按下返回键的退场动画
     */
    private static void addAnim(FragmentTransaction ft, int enter, int exit, int popEnter, int popExit) {
        ft.setCustomAnimations(enter, exit, popEnter, popExit);
    }

    /**
     * 添加共享元素
     *
     * @param ft    FragmentTransaction对象
     * @param views 共享元素
     */
    private static void addSharedElement(final FragmentTransaction ft,
                                         final View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : views) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        }
    }

    private static void putArgs(final Fragment fragment, final Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    private static void putArgs(final Fragment fragment, final boolean isHide) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    /**
     * 参数内部类Args
     */
    private static class Args {
        int id;
        boolean isHide;
        boolean isAddStack;

        public Args(int id, boolean isHide, boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }
}
