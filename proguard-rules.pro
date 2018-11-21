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
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
#把代码以及所使用到的各种第三方库代码统统移动到同一个包下
-repackageclasses
-obfuscationdictionary proguard-dictionary.txt
-classobfuscationdictionary proguard-dictionary.txt
-packageobfuscationdictionary proguard-dictionary.txt
#mj_sdk_core
-dontwarn com.mj.sdk.core.callback.**
-keep class com.mj.sdk.core.callback.** { *; }
-dontwarn com.mj.sdk.core.model.**
-keep class com.mj.sdk.core.model.** { *; }
-keep class com.mj.sdk.core.sdk.ExceptionCode{ *; }
-keep class com.mj.sdk.core.sdk.SDK_Core{ *; }

#mj_sdk_function_character
-keep class com.mj.sdk.function_character.SDK_Function_Character { *; }
-keep class com.mj.sdk.function_character.SDK_Function_Character$* { *; }
-keep class com.mj.sdk.function_character.SDK_Function_Character_Listener { *; }

#mj_sdk_function_image
-keep class com.mj.sdk.function_image.SDK_Function_Image { *; }
-keep class com.mj.sdk.function_image.PartThumbnailListener { *; }
-keep class com.mj.sdk.function_image.PartEPCListener { *; }

#mj_sdk_function_keyboard

#mj_sdk_function_planb
-keep class com.mj.sdk.function_planb.SDK_Function_PlanB { *; }
-keep class com.mj.sdk.function_planb.SDK_Function_PlanB_Listener { *; }
#mj_sdk_function_oe
-keep class com.mj.sdk.function_oe.SDK_Function_OE { *; }
-keep class com.mj.sdk.function_oe.SDK_Function_OE_Listener { *; }
#mj_sdk_function_querybydraw
-keep class com.mj.sdk.function_querybydraw.PartRelevanceListener { *; }
-keep class com.mj.sdk.function_querybydraw.SDK_Function_QueryByDraw { *; }
-keep class com.mj.sdk.function_querybydraw.DrawPartView { public *; }
-keep class com.mj.sdk.function_querybydraw.SDK_Function_QueryByDraw_Listener { *; }
-keep class com.mj.sdk.function_querybydraw.SDK_Function_QueryByDraw_State_Listener { *; }
-keep class com.mj.sdk.function_querybydraw.SDK_Function_QueryByRecommend_Listener { *; }

#mj_sdk_function_speech
-keep class com.mj.sdk.function_speech.SDK_Function_Speech { *; }
-keep class com.mj.sdk.function_speech.SpeechListener { *; }

#mj_sdk_function_vin
-keep class com.mj.sdk.function_vin.SDK_Function_Vin { *; }
-keep class com.mj.sdk.function_vin.SDK_Function_Vin_Listener { *; }
-keep class com.mj.sdk.function_vin.VINOptionBean { *; }
-keep class com.mj.sdk.function_vin.VINResponseBean { *; }

#mj_sdk
-keep class com.mj.sdk.bean.** { *; }
-keep class com.mj.sdk.Exception.** { *; }
-keep class com.mj.sdk.callback.** { *; }
-keep class com.mj.sdk.model.SdkInstanceManager { *; }
-keep class com.mj.sdk.service.MJSdkService { *; }
-keep class com.mj.sdk.service.IMJSdkService { *; }
-keep class com.mj.sdk.view.DrawManager { *; }
-keep class com.mj.sdk.view.DrawPartView { *; }
-keep class com.mj.sdk.view.OnDrawQueryListener { *; }
-keep class com.mj.sdk.speech.**{ *; }

# base module
-keep class com.mj.base.**{ *; }
-keep class com.mj.network.**{ *; }




#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#growingio
-keep class com.growingio.android.sdk.** {*;}
-dontwarn com.growingio.android.sdk.**

#-libraryjars libs/oss
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

#-libraryjars libs/Msc.jar
-dontwarn com.chinaMobile.**
-keep class com.chinaMobile.** { *; }
-dontwarn com.iflytek.**
-keep class com.iflytek.** { *; }
-keep class com.iflytek.cloud.thirdparty.** { *; }
-dontwarn sun.misc.**
-keep class sun.misc.** { *; }

#-libraryjars libs/Sunflower.jar
-dontwarn com.iflytek.sunflower.**
-keep class com.iflytek.sunflower.** { *; }
-keep class **.R$* {*;}

#-libraryjars libs/glide
-dontwarn jp.wasabeef.glide.transformations.**
-keep class jp.wasabeef.glide.transformations.** { *; }

#-libraryjars libs/okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

#-libraryjars libs/okio
-dontwarn okio.**
-keep class okio.** { *; }

#-加载动画
-dontwarn com.mj.library.animator.**
-keep class com.mj.library.animator.** { *; }


-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------


#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
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
#---------------------------------------------------------------------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder