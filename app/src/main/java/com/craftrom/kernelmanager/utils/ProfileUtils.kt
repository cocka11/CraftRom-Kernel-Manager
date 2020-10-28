package com.craftrom.kernelmanager.utils

import android.content.Context
import com.craftrom.kernelmanager.R

class ProfileUtils {

    companion object {

        @JvmStatic
        fun applyProfile(context: Context) {

            val sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
            val sharedPreferencesBoot = context!!.getSharedPreferences("update", Context.MODE_PRIVATE)
            when(sharedPreferences.getInt("current", 1)) {
                0 -> {
                    //Battery Profile here
                    //Little
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor", "conservative",  true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq", "614400",  true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq", "1363200", true, true)

                    //Big
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_governor", "conservative",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_min_freq", "633600",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_max_freq", "1401600",true, true)

                    //GPU
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/min_clock_mhz", "133",true, true)
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/max_clock_mhz", "510",true, true)
                    FileUtils.writeFile("/sys/module/adreno_idler/parameters/adreno_idler_active", "1",true, true)
                    FileUtils.writeFile("/sys/class/kgsl/kgsl-3d0/devfreq/adrenoboost", "0",true, true)

                    // Offline CPU6 and CPU7
                    if (sharedPreferencesBoot?.getBoolean("apply_super_battery", true) != false) {
                        FileUtils.writeFile("/sys/devices/system/cpu/cpu6/online", "0", true, true)
                        FileUtils.writeFile("/sys/devices/system/cpu/cpu7/online", "0", true, true)
                    } else {
                        if (sharedPreferencesBoot?.getBoolean("apply_super_battery", false) != true) {
                            FileUtils.writeFile("/sys/devices/system/cpu/cpu6/online", "1", true, true)
                            FileUtils.writeFile("/sys/devices/system/cpu/cpu7/online", "1", true, true)
                        }
                    }

                }
                1 -> {
                    //Chidori profile here
                    //Little
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor", "blu_schedutil",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq", "614400",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq", "1536000",true, true)

                    //Big
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_governor", "blu_schedutil",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_min_freq", "633600",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_max_freq", "1747200",true, true)


                    //GPU
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/min_clock_mhz", "133",true, true)
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/max_clock_mhz", "725",true, true)
                    FileUtils.writeFile("/sys/module/adreno_idler/parameters/adreno_idler_active", "0",true, true)
                    FileUtils.writeFile("/sys/class/kgsl/kgsl-3d0/devfreq/adrenoboost", "3",true, true)

                    FileUtils.writeFile("/sys/devices/system/cpu/cpu6/online", "1", true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu7/online", "1", true, true)
                }
                2 -> {
                    //Performance profile here
                    //Little
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor", "schedutil",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq", "614400",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq", "1804800",true, true)

                    //Big
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_governor", "schedutil",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_min_freq", "633600",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_max_freq", "1804800",true, true)

                    //GPU
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/min_clock_mhz", "133",true, true)
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/max_clock_mhz", "650",true, true)
                    FileUtils.writeFile("/sys/module/adreno_idler/parameters/adreno_idler_active", "0",true, true)
                    FileUtils.writeFile("/sys/class/kgsl/kgsl-3d0/devfreq/adrenoboost", "2",true, true)

                    FileUtils.writeFile("/sys/devices/system/cpu/cpu6/online", "1", true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu7/online", "1", true, true)
                }
                3 -> {
                    //Gaming profile here
                    //Little
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor", "performance",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq", "614400",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq", "1804800",true, true)

                    //Big
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_governor", "performance",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_min_freq", "633600",true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu4/cpufreq/scaling_max_freq", "1804800",true, true)

                    //GPU
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/min_clock_mhz", "133",true, true)
                    FileUtils.writeFile("/sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/max_clock_mhz", "650",true, true)
                    FileUtils.writeFile("/sys/module/adreno_idler/parameters/adreno_idler_active", "0",true, true)
                    FileUtils.writeFile("/sys/class/kgsl/kgsl-3d0/devfreq/adrenoboost", "3",true, true)

                    FileUtils.writeFile("/sys/devices/system/cpu/cpu6/online", "1", true, true)
                    FileUtils.writeFile("/sys/devices/system/cpu/cpu7/online", "1", true, true)
                }
                else -> {
                    //Custom, load from file maybe?
                }
            }

        }

    }

}
