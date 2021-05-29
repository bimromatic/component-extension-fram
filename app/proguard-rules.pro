# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#混淆时不生成大小写混合的类名
-dontusemixedcaseclassnames

#不跳过非公共的库的类
-dontskipnonpubliclibraryclasses

#混淆过程中记录日志
-verbose

#关闭预校验
-dontpreverify

#关闭优化
-dontoptimize

#保留注解
-keepattributes *Annotation*

#保留所有拥有本地方法的类名及本地方法名
-keepclasseswithmembernames class * {
    native <methods>;
}

#保留自定义View的get和set方法
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

#保留Activity中View及其子类入参的方法，如: onClick(android.view.View)
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#保留枚举
-keepclassmembers enum * {
    **[] $VALUES;
    public *;
}

#保留序列化的类
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

#保留R文件的静态成员
-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn android.support.**

-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}
