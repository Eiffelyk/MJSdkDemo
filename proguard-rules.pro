# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Android\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-dontwarn com.dataenlighten.aimjsdk.demo.custom.ZoomImageView
-keep class com.dataenlighten.aimjsdk.demo.custom.ZoomImageView { *; }
#-libraryjars libs/Msc.jar
#-dontwarn com.chinaMobile.**
#-keep class com.chinaMobile.** { *; }
-dontwarn com.iflytek.**
-keep class com.iflytek.** { *; }
-keep class com.iflytek.cloud.thirdparty.** { *; }
-dontwarn sun.misc.**
-keep class sun.misc.** { *; }

#-libraryjars libs/Sunflower.jar
-dontwarn com.iflytek.sunflower.**
-keep class com.iflytek.sunflower.** { *; }
-keep class **.R$* {*;}
#-libraryjars libs/okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

#-libraryjars libs/okio
-dontwarn okio.**
-keep class okio.** { *; }

#gson
#如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }

#---------------------------------------------------------------------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
#-------------------------------------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5       #代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-dontusemixedcaseclassnames     #混合时不使用大小写混合，混合后的类名为小写
-dontskipnonpubliclibraryclasses    #指定不去忽略非公共库的类
-dontskipnonpubliclibraryclassmembers   #指定不去忽略非公共库的类
-dontshrink
-dontpreverify    #不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*  # 混淆时所采用的算法
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-dontwarn android.support.**
-keep class android.** {*;}
-dontwarn android.**
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

-keepnames class * extends android.view.View
-keep class * extends android.app.Fragment {
    public void setUserVisibleHint(boolean);
    public void onHiddenChanged(boolean);
    public void onResume();
    public void onPause();
}
-keep class android.support.v4.app.Fragment {
    public void setUserVisibleHint(boolean);
    public void onHiddenChanged(boolean);
    public void onResume();
    public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
    public void setUserVisibleHint(boolean);
    public void onHiddenChanged(boolean);
    public void onResume();
    public void onPause();
}

-keepclassmembers class * {public <init> (org.json.JSONObject);}
-keep public class com.mj.lossassessment.R$*{public static final int *;}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
  public <fields>;
  private <fields>;
  public <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {*;}
-keepclassmembers class * {void *(**On*Event);}

#忽略警告
-ignorewarning
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------