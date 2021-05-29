package com.bimromatic.component.lib_base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/12/21
 * desc   : 资源文件加载工具
 * version: 1.0
 */
public class ResLoaderUtils {

//    /**
//     * 获取strings.xml资源文件字符串
//     *
//     * @param id 资源文件id
//     * @return 资源文件对应字符串
//     */
//    public static String getString(int id) {
//        return AppUtil.getContext().getResources().getString(id);
//    }
//
//    /**
//     * 获取strings.xml资源文件字符串数组
//     *
//     * @param id 资源文件id
//     * @return 资源文件对应字符串数组
//     */
//    public static String[] getStringArray(int id) {
//        return AppUtil.getContext().getResources().getStringArray(id);
//    }
//
//    /**
//     * 获取drawable资源文件图片
//     *
//     * @param id 资源文件id
//     * @return 资源文件对应图片
//     */
//    public static Drawable getDrawable(int id) {
//        return AppUtil.getContext().getResources().getDrawable(id);
//    }
//
//    /**
//     * 获取colors.xml资源文件颜色
//     *
//     * @param id 资源文件id
//     * @return 资源文件对应颜色值
//     */
//    public static int getColor(int id) {
//        return AppUtil.getContext().getResources().getColor(id);
//    }
//
//    /**
//     * 获取颜色的状态选择器
//     *
//     * @param id 资源文件id
//     * @return 资源文件对应颜色状态
//     */
//    public static ColorStateList getColorStateList(int id) {
//        return AppUtil.getContext().getResources().getColorStateList(id);
//    }
//
//    /**
//     * 获取dimens资源文件中具体像素值
//     *
//     * @param id 资源文件id
//     * @return 资源文件对应像素值
//     */
//    public static int getDimen(int id) {
//        return AppUtil.getContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
//    }
//
//    /**
//     * 加载布局文件
//     *
//     * @param id 布局文件id
//     * @return 布局view
//     */
//    public static View inflate(int id) {
//        return View.inflate(AppUtil.getContext(), id, null);
//    }
//
//    /**
//     * 生成CheckBox 或者 RadioButton 背景状态
//     *
//     * @param context
//     * @param checkedRes   选中
//     * @param unCheckedRes 未选中
//     * @param disableRes   不可用
//     * @return
//     */
//    public static Drawable generateCheckBoxBgDrawable(Context context, int checkedRes, int unCheckedRes, int disableRes) {
//        Drawable checkedDrawable = Drawable(context, checkedRes);
//        Drawable unCheckedDrawable = Drawable(context, unCheckedRes);
//        StateListDrawable stateListDrawable = new StateListDrawable();
//        int checked = android.R.attr.state_checked;
//        if (disableRes != 0) {
//            Drawable disAbleDrawable = Drawable(context, disableRes);
//            int enable = android.R.attr.state_enabled;
//            stateListDrawable.addState(new int[]{-enable}, disAbleDrawable);
//        }
//        stateListDrawable.addState(new int[]{-checked}, unCheckedDrawable);
//        stateListDrawable.addState(new int[]{checked}, checkedDrawable);
//        return stateListDrawable;
//    }
//
//    /**
//     * 注意调用时如果使用setBackground 需判断 api版本 16以下没有该方法 由资源drawable Id 使用代码构建点击效果
//     *
//     * @param normalRes  默认状态 非点击态
//     * @param pressedRes 点击态 选中 或者 具有焦点
//     * @param disableRes 不可用状态 没有可为0
//     * @return
//     */
//    public static Drawable generateClickBgDrawable(Context context, int normalRes, int pressedRes, int disableRes) {
//        Drawable normalDrawable = Drawable(context, normalRes);
//        Drawable pressedDrawable = Drawable(context, pressedRes);
//        StateListDrawable stateListDrawable = new StateListDrawable();
//        int pressed = android.R.attr.state_pressed;
//        int focused = android.R.attr.state_focused;
//        int selected = android.R.attr.state_selected;
//        if (disableRes != 0) {
//            Drawable disAbleDrawable = Drawable(context, disableRes);
//            int enable = android.R.attr.state_enabled;
//            stateListDrawable.addState(new int[]{-enable}, disAbleDrawable);
//        }
//        stateListDrawable.addState(new int[]{-focused, -pressed, -selected}, normalDrawable);
//        stateListDrawable.addState(new int[]{-pressed, selected}, pressedDrawable);
//        stateListDrawable.addState(new int[]{focused, -pressed, -selected}, pressedDrawable);
//        stateListDrawable.addState(new int[]{focused, -pressed, selected}, pressedDrawable);
//        stateListDrawable.addState(new int[]{pressed}, pressedDrawable);
//
//        return stateListDrawable;
//    }
//
//    /**
//     * 设置控件点击效果
//     *
//     * @param view
//     * @param context
//     * @param normalRes
//     * @param pressedRes
//     * @param disableRes
//     */
//    @SuppressWarnings("deprecation")
//    public static void setBackgroundDrawable(View view, Context context, int normalRes, int pressedRes, int disableRes) {
//        Drawable drawable = generateClickBgDrawable(context, normalRes, pressedRes, disableRes);
//        if (view instanceof ImageView) {
//            ((ImageView) view).setImageDrawable(drawable);
//        } else if (view instanceof ImageButton) {
//            ((ImageButton) view).setImageDrawable(drawable);
//        } else {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                view.setBackgroundDrawable(drawable);
//            } else {
//                view.setBackground(drawable);
//            }
//        }
//    }
//
//    /**
//     * 设置控件选中效果
//     *
//     * @param view
//     * @param context
//     * @param disableRes
//     */
//    @SuppressWarnings("deprecation")
//    public static void setChosedBgDrawable(View view, Context context, int checkedRes, int unCheckedRes, int disableRes) {
//        Drawable drawable = generateCheckBoxBgDrawable(context, checkedRes, unCheckedRes, disableRes);
//        if (view instanceof CheckBox) {
//            ((CheckBox) view).setButtonDrawable(drawable);
//        } else {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                view.setBackgroundDrawable(drawable);
//            } else {
//                view.setBackground(drawable);
//            }
//        }
//    }
//
//    public static Context getContext() {
//        return AppUtil.getContext();
//    }
//
//    public static Animation Animation(Context context, int id) {
//        return AnimationUtils.loadAnimation(context, id);
//    }
//
//    public static boolean Boolean(Context context, int id) {
//        return context.getResources().getBoolean(id);
//    }
//
//    public static int Color(Context context, int id) {
//        return context.getResources().getColor(id);
//    }
//
//    public static ColorStateList ColorStateList(Context context, int id) {
//        return context.getResources().getColorStateList(id);
//    }
//
//    public static float Dimension(Context context, int id) {
//        return context.getResources().getDimension(id);
//    }
//
//    public static int DimensionPixelOffset(Context context, int id) {
//        return context.getResources().getDimensionPixelOffset(id);
//    }
//
//    public static int DimensionPixelSize(Context context, int id) {
//        return context.getResources().getDimensionPixelSize(id);
//    }
//
//    @SuppressWarnings("deprecation")
//    public static Drawable Drawable(Context context, int id) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            return context.getResources().getDrawable(id);
//        } else {
//            return context.getResources().getDrawable(id, null);
//        }
//
//    }
//
//    public static int Integer(Context context, int id) {
//        return context.getResources().getInteger(id);
//    }
//
//    public static int[] IntArray(Context context, int id) {
//        return context.getResources().getIntArray(id);
//    }
//
//    public static Movie Movie(Context context, int id) {
//        return context.getResources().getMovie(id);
//    }
//
//    public static String String(Context context, int id) {
//        return context.getResources().getString(id);
//    }
//
//    public static String[] StringArray(Context context, int id) {
//        return context.getResources().getStringArray(id);
//    }
//
//    public static CharSequence Text(Context context, int id) {
//        return context.getResources().getText(id);
//    }
//
//    public static CharSequence[] TextArray(Context context, int id) {
//        return context.getResources().getTextArray(id);
//    }
//
//    public static XmlResourceParser Xml(Context context, int id) {
//        return context.getResources().getXml(id);
//    }

}
