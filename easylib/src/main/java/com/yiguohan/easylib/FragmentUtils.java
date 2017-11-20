package com.yiguohan.easylib;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

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
    public static final int TYPE_REPLACE_FRAGMENT = 0x01 << 1;

    /**
     * 添加Fragment
     *
     * @param fm           FragmentManager对象
     * @param dest         待添加的Fragment
     * @param containerId  布局Id
     * @param enterAnim    进场动画Id
     * @param exitAnim     退场动画Id
     * @param popEnterAnim 以退回键呼出的进场动画
     * @param popExitAnim  以退回键呼出的退场动画
     */
    public static void add(@NonNull FragmentManager fm, @NonNull Fragment dest, @IdRes final int containerId, @IdRes final int enterAnim, @IdRes final int exitAnim, @IdRes final int popEnterAnim, @IdRes final int popExitAnim) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        putArgs(dest,new Args(containerId,false,false));
        addAnim(fragmentTransaction, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, fm, fragmentTransaction, null, dest);
    }

    /**
     * Fragment操作业务方法
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
