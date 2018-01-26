package com.yiguohan.easylib;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
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

    /**
     * 弹栈 FragmentManager 对象内的fragment 直到popClz为止
     *
     * @param fm          FragmentManager 对象
     * @param popClz      出栈 fragment 的类型
     * @param isInclusive 是否连popClz的这个Fragment一起弹栈
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive) {
        popTo(fm, popClz, isInclusive, true);
    }

    /**
     * 弹栈 FragmentManager 对象内的fragment 直到popClz为止
     *
     * @param fm          FragmentManager 对象
     * @param popClz      出栈 fragment 的类型
     * @param isInclusive 是否连popClz的这个Fragment一起弹栈
     * @param isImmediate 是否立即弹栈
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive,
                             final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate(popClz.getName(),
                    isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        } else {
            fm.popBackStack(popClz.getName(),
                    isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }
    }

    /**
     * 所有 FragmentManager 中的fragment弹栈
     *
     * @param fm FragmentManager 对象
     */
    public static void popAll(@NonNull final FragmentManager fm) {
        popAll(fm, true);
    }

    /**
     * 所有 FragmentManager 中的fragment弹栈
     *
     * @param fm          FragmentManager 对象
     * @param isImmediate 是否立即弹栈
     */
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

    /**
     * 移除 fragment
     *
     * @param remove 要移除的 fragment
     */
    public static void remove(@NonNull final Fragment remove) {
        operateNoAnim(remove.getFragmentManager(), TYPE_REMOVE_FRAGMENT, null, remove);
    }

    /**
     * 移除 FragmentManager 对象内的fragment
     *
     * @param removeTo    要移除到的fragment
     * @param isInclusive 是否把removeTo对应的fragment对象也一起移除
     */
    public static void removeTo(@NonNull final Fragment removeTo, final boolean isInclusive) {
        operateNoAnim(removeTo.getFragmentManager(), TYPE_REMOVE_TO_FRAGMENT,
                isInclusive ? removeTo : null, removeTo);
    }

    /**
     * 移除 FragmentManager 对象内的所有fragment
     *
     * @param fm
     */
    public static void removeAll(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        operateNoAnim(fm,
                TYPE_REMOVE_FRAGMENT,
                null,
                fragments.toArray(new Fragment[fragments.size()]));
    }

/*---------------------------------------获取 fragment---------------------------------------------*/

    /**
     * 获取顶部的 fragment(未加入退回栈)
     *
     * @param fm FragmentManager对象
     * @return 最后加入的 fragment
     */
    public static Fragment getTop(@NonNull final FragmentManager fm) {
        return getTopInStack(fm, false);
    }

    /**
     * 获取退回栈顶的 fragment
     *
     * @param fm FragmentManager对象
     * @return
     */
    public static Fragment getTopInStack(@NonNull final FragmentManager fm) {
        return getTopInStack(fm, true);
    }

    private static Fragment getTopInStack(@NonNull final FragmentManager fm,
                                          final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * 获取顶部可见 fragment(未加入退回栈)
     *
     * @param fm FragmentManager对象
     * @return 顶层可见 fragment
     */
    public static Fragment getTopShow(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, false);
    }

    /**
     * 获取退回栈顶部可见 fragment
     *
     * @param fm FragmentManager对象
     * @return 栈中顶层可见 fragment
     */
    public static Fragment getTopShowInStack(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, true);
    }

    private static Fragment getTopShowIsInStack(@NonNull final FragmentManager fm, final boolean isInstack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()) {
                if (isInstack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
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
     * 获取同级别退回栈中的 fragment
     *
     * @param fm FragmentManager对象
     * @return FragmentManager对象在退回栈中的 fragment
     */
    public static List<Fragment> getFragmentsInStack(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        List<Fragment> result = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(fragment);
            }
        }
        return result;
    }

    /**
     * 获取栈中所有的 fragment
     *
     * @param fm FragmentManager对象
     * @return 所有的fragment
     */
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm) {
        return getAllFragmentsInStack(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm,
                                                             final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(new FragmentNode(fragment,
                        getAllFragmentsInStack(fragment.getChildFragmentManager(),
                                new ArrayList<FragmentNode>())));
            }
        }
        return result;
    }

/*-------------------------------------------其它-------------------------------------------------*/

    /**
     * 查找 fragment
     *
     * @param fm      FragmentManager对象
     * @param findClz 要查找的 fragment 类型
     * @return 查找到的 fragment
     */
    public static Fragment findFragment(@NonNull final FragmentManager fm,
                                        final Class<? extends Fragment> findClz) {
        return fm.findFragmentByTag(findClz.getName());
    }

    /**
     * 处理 fragment 返回键
     * 如果 fragment 实现了 OnBackClickListener 接口，返回{@code true}: 表示已消费回退键事件，反之则没消费
     *
     * @param fragment
     * @return 是否消费回退事件
     */
    public static boolean dispatchBackPress(@NonNull final Fragment fragment) {
        return fragment.isResumed()
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackClick();
    }

    /**
     * 处理 fragment 回退键
     * <p>如果 fragment 实现了 OnBackClickListener 接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p>
     * <p>具体示例见 FragmentActivity</p>
     *
     * @param fm fragment 管理器
     * @return 是否消费回退事件
     */
    public static boolean dispatchBackPress(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        if (fragments == null || fragments.isEmpty()) {
            return false;
        }
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置背景色
     *
     * @param fragment fragment
     * @param color    背景色
     */
    public static void setBackgroundColor(@NonNull final Fragment fragment,
                                          @ColorInt final int color) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * 设置背景资源
     *
     * @param fragment fragment
     * @param resId    资源id
     */
    public static void setBackgroundResource(@NonNull final Fragment fragment,
                                             @DrawableRes final int resId) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * 设置背景
     *
     * @param fragment   fragment
     * @param background 背景
     */
    public static void setBackground(@NonNull final Fragment fragment, final Drawable background) {
        ViewCompat.setBackground(fragment.getView(), background);
    }

    /**
     * 获取类名
     *
     * @param fragment fragment
     * @return 类名
     */
    public static String getSimpleName(final Fragment fragment) {
        return fragment == null ? "null" : fragment.getClass().getSimpleName();
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
            case TYPE_HIDE_FRAGMENT:
                for (Fragment fragment : dests) {
                    fragmentTransaction.hide(fragment);
                }
                break;
            case TYPE_SHOW_FRAGMENT:
                for (Fragment fragment : dests) {
                    fragmentTransaction.show(fragment);
                }
                break;
            case TYPE_SHOW_HIDE_FRAGMENT:
                fragmentTransaction.show(src);
                for (Fragment fragment : dests) {
                    if (fragment != src) {
                        fragmentTransaction.hide(fragment);
                    }
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                name = dests[0].getClass().getName();
                args = dests[0].getArguments();
                fragmentTransaction.replace(args.getInt(ARGS_ID), dests[0], name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) {
                    fragmentTransaction.addToBackStack(name);
                }
                break;
            case TYPE_REMOVE_FRAGMENT:
                for (Fragment fragment : dests) {
                    if (fragment != src) {
                        fragmentTransaction.remove(fragment);
                    }
                }
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                for (int i = dests.length - 1; i >= 0; --i) {
                    Fragment fragment = dests[i];
                    if (fragment == dests[0]) {
                        if (src != null) {
                            fragmentTransaction.remove(fragment);
                        }
                        break;
                    }
                    fragmentTransaction.remove(fragment);
                }
                break;
            default:
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

    public interface OnBackClickListener {
        boolean onBackClick();
    }

    public static class FragmentNode {
        Fragment fragment;
        List<FragmentNode> next;

        public FragmentNode(Fragment fragment, List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }

        @Override
        public String toString() {
            return fragment.getClass().getSimpleName() + "->" + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
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
