# Hilt
-keepclasseswithmembers class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper {
    public <init>(android.content.Context);
}

# Compose
-keepclasseswithmembers class androidx.compose.** { *; }
-keep class androidx.compose.ui.** { *; }

# DataStore
-keep class androidx.datastore.** { *; }

# 保留自定义类
-keep class com.selfie.light.data.** { *; }
-keep class com.selfie.light.domain.** { *; } 