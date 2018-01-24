package com.yiguohan.easylib;

import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
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
import java.util.EventListener;
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
    public static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 3;
    public static final int TYPE_REPLACE_FRAGMENT = 0x01 << 4;
    public static final int TYPE_REMOVE_FRAGMENT = 0x01 << 5;
    public static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 6;

/*----------------------------------------添加 fragment--------------------------------------------*/

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
     * 添加 Fragment
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

    /**
     * 添加 Fragment
     *
     * @param fm          FragmentManager对象
     * @param target      待添加的Fragment List
     * @param containerId 布局Id
     * @param showIndex   要显示的 fragment 索引
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final List<Fragment> target,
                           @IdRes final int containerId,
                           final int showIndex) {
        add(fm, target.toArray(new Fragment[target.size()]), containerId, showIndex);
    }

    /**
     * 添加 Fragment
     *
     * @param fm          FragmentManager对象
     * @param target      待添加的Fragment数组
     * @param containerId 布局Id
     * @param showIndex   要显示的 fragment 索引
     */
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
     * 添加 Fragment
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
     * 添加 Fragment
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
     * 添加 Fragment
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
     * 添加 Fragment
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
     * 添加 Fragment
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
     * 添加 Fragment
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
/*--------------------------------------显示/隐藏 fragment-----------------------------------------*/

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
     * 先显示后隐藏 fragment
     *
     * @param showIndex 要显示的fragment索引
     * @param fragments 要隐藏的fragments
     */
    public static void showHide(final int showIndex,
                                @NonNull final List<Fragment> fragments) {
        showHide(fragments.get(showIndex), fragments);
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param show 要显示的fragment
     * @param hide 要隐藏的fragment
     */
    public static void showHide(@NonNull final Fragment show,
                                @NonNull final List<Fragment> hide) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != null);

        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_FRAGMENT, show, hide.toArray(new Fragment[hide.size()]));
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param showIndex 要显示的 fragment索引
     * @param fragments 要隐藏的 fragments
     */
    public static void showHide(final int showIndex,
                                @NonNull final Fragment... fragments) {
        showHide(fragments[showIndex], fragments);
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param show 要显示的 fragment
     * @param hide 要隐藏的 fragment
     */
    public static void showHide(@NonNull final Fragment show,
                                @NonNull final Fragment... hide) {
        for (Fragment fragment :
                hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param show 要显示的 fragment
     * @param hide 要隐藏的 fragment
     */
    public static void showHide(@NonNull final Fragment show,
                                @NonNull final Fragment hide) {
        putArgs(show, false);
        putArgs(hide, true);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }
/*---------------------------------------替换 fragment---------------------------------------------*/

    /**
     * 替换 fragment
     *
     * @param srcFragment  源 fragment
     * @param destFragment 目标 fragment
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment) {
        replace(srcFragment, destFragment, false);
    }

    /**
     * 替换 fragment
     *
     * @param srcFragment  源 fragment
     * @param destFragment 目标 fragment
     * @param isAddStack   是否入退回栈
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack);
    }

    /**
     * 替换 fragment
     *
     * @param srcFragment  源 fragment
     * @param destFragment 目标 fragment
     * @param enterAnim    进场动画
     * @param exitAnim     退场动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim);
    }

    /**
     * 替换 fragment
     *
     * @param srcFragment  源 fragment
     * @param destFragment 目标 fragment
     * @param isAddStack   是否入退回栈
     * @param enterAnim    进场动画
     * @param exitAnim     退场动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换 fragment
     *
     * @param srcFragment  源 fragment
     * @param destFragment 目标 fragment
     * @param enterAnim    进场动画
     * @param exitAnim     退场动画
     * @param popEnterAnim 返回键进场动画
     * @param popExitAnim  返回键退场动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes int enterAnim,
                               @AnimRes int exitAnim,
                               @AnimRes int popEnterAnim,
                               @AnimRes int popExitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换 fragment
     *
     * @param srcFragment  源 fragment
     * @param destFragment 目标 fragment
     * @param isAddStack   是否入退回栈
     * @param enterAnim    进场动画
     * @param exitAnim     退场动画
     * @param popEnterAnim 返回键进场动画
     * @param popExitAnim  返回键退场动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes int enterAnim,
                               @AnimRes int exitAnim,
                               @AnimRes int popEnterAnim,
                               @AnimRes int popExitAnim) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换 fragment
     *
     * @param srcFragment    源 fragment
     * @param destFragment   目标 fragment
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final View... sharedElements) {
        replace(srcFragment, destFragment, false, sharedElements);
    }

    /**
     * 替换 fragment
     *
     * @param srcFragment    源 fragment
     * @param destFragment   目标 fragment
     * @param isAddStack     是否入退回栈
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               final View... sharedElements) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack, sharedElements);
    }

    /**
     * 替换 fragment
     *
     * @param fm          FragmentManager对象
     * @param fragment    目标 fragment
     * @param containerId 容器ID
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId) {
        replace(fm, fragment, containerId, false);
    }

    /**
     * 替换 fragment
     *
     * @param fm          FragmentManager对象
     * @param fragment    目标 fragment
     * @param containerId 容器ID
     * @param isAddStack  是否入退回栈
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * 替换 fragment
     *
     * @param fm          FragmentManager对象
     * @param fragment    目标 fragment
     * @param containerId 容器ID
     * @param enterAnim   进场动画
     * @param exitAnim    退场动画
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换 fragment
     *
     * @param fm           FragmentManager对象
     * @param fragment     目标 fragment
     * @param containerId  容器ID
     * @param enterAnim    进场动画
     * @param exitAnim     退场动画
     * @param popEnterAnim 返回键进场动画
     * @param popExitAnim  返回键退场动画
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换 fragment
     *
     * @param fm           FragmentManager对象
     * @param fragment     目标 fragment
     * @param containerId  容器ID
     * @param isAddStack   是否入退回栈
     * @param enterAnim    进场动画
     * @param exitAnim     退场动画
     * @param popEnterAnim 返回键进场动画
     * @param popExitAnim  返回键退场动画
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * 替换 fragment
     *
     * @param fm             FragmentManager对象
     * @param fragment       目标 fragment
     * @param containerId    容器ID
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final View... sharedElements) {
        replace(fm, fragment, containerId, false, sharedElements);
    }

    /**
     * 替换 fragment
     *
     * @param fm             FragmentManager对象
     * @param fragment       目标 fragment
     * @param containerId    容器ID
     * @param isAddStack     是否入退回栈
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

/*---------------------------------------弹栈 fragment---------------------------------------------*/

    /**
     * 弹栈 FragmentManager 对象内所有 fragment
     *
     * @param fm
     */
    public static void pop(@NonNull final FragmentManager fm) {
        pop(fm, true);
    }

    /**
     * 是否立即弹栈 FragmentManager 对象内所有 fragment
     *
     * @param fm          FragmentManager 对象
     * @param isImmediate 是否立即出栈
     */
    public static void pop(@NonNull final FragmentManager fm,
                           final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate();
        } else {
            fm.popBackStack();
        }
    }

    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive) {
        popTo(fm, popClz, isInclusive, true);
    }

    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive,
                             final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate(popClz.getName(), isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        } else {
            fm.popBackStack(popClz.getName(), isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }
    }

    public static void popAll(@NonNull final FragmentManager fm) {
        popAll(fm, true);
    }

    public static void popAll(@NonNull final FragmentManager fm,
                              final boolean isImmediate) {
        while (fm.getBackStackEntryCount() > 0) {
            if (isImmediate) {
                fm.popBackStackImmediate();
            } else {
                fm.popBackStack();
            }
        }
    }

/*---------------------------------------移除 fragment---------------------------------------------*/

    public static void remove(@NonNull final Fragment remove) {
        operateNoAnim(remove.getFragmentManager(), TYPE_REMOVE_FRAGMENT, null, remove);
    }

    public static void removeTo(@NonNull final Fragment removeTo, final boolean isInclusive) {
        operateNoAnim(removeTo.getFragmentManager(), TYPE_REMOVE_TO_FRAGMENT,
                isInclusive ? removeTo : null, removeTo);
    }

    public static void removeAll(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        operateNoAnim(fm,
                TYPE_REMOVE_FRAGMENT,
                null,
                fragments.toArray(new Fragment[fragments.size()]));
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

    /**
     * 是否隐藏该fragment
     *
     * @param fragment 目标fragment
     * @param isHide   是否隐藏
     */
    private static void putArgs(final Fragment fragment, final boolean isHide) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    private static Args getArgs(final Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        return new Args(bundle.getInt(ARGS_ID, fragment.getId()),
                bundle.getBoolean(ARGS_IS_HIDE),
                bundle.getBoolean(ARGS_IS_ADD_STACK));
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
