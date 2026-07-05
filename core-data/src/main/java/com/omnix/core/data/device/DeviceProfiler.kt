package com.omnix.core.data.device

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import com.omnix.core.model.DeviceProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Collects real device hardware information using Android platform APIs.
 * Gracefully handles emulator quirks and APIs that may return 0 or -1.
 */
@Singleton
class DeviceProfiler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun collect(): DeviceProfile {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)

        val totalRamMb = (memInfo.totalMem / MB).coerceAtLeast(1L)
        val availableRamMb = (memInfo.availMem / MB).coerceAtLeast(0L)

        val internalStat = StatFs(Environment.getDataDirectory().path)
        val totalStorageMb = (internalStat.totalBytes / MB).coerceAtLeast(1L)
        val freeStorageMb = (internalStat.availableBytes / MB).coerceAtLeast(0L)

        val cpuAbi = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown"
        val cpuCoreCount = Runtime.getRuntime().availableProcessors().coerceAtLeast(1)

        return DeviceProfile(
            androidVersion = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
            manufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() },
            model = Build.MODEL,
            cpuAbi = cpuAbi,
            cpuCoreCount = cpuCoreCount,
            totalRamMb = totalRamMb,
            availableRamMb = availableRamMb,
            totalStorageMb = totalStorageMb,
            freeStorageMb = freeStorageMb,
            hasNpuAcceleration = hasNnApiSupport(),
            hasGpuDelegateSupport = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        )
    }

    private fun hasNnApiSupport(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    companion object {
        private const val MB = 1_048_576L // 1024 * 1024
    }
}
