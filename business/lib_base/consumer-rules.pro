-dontwarn com.kingja.loadsir.**
-keep class com.kingja.loadsir.** {*;}
# ViewBinding #因封装使用了反射，所以混淆规则需将反射调用方法禁止混淆
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static * inflate(android.view.LayoutInflater);
}

#NetworkMonitor
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.kongqw.network.monitor.interfaces.NetworkMonitor <methods>;
}
-keep class com.kongqw.network.monitor.** { *; }



#eventbus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
