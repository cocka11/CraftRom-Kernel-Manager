package com.craftrom.kernelmanager.utils

import android.util.Log
import androidx.annotation.Nullable
import com.craftrom.kernelmanager.utils.root.RootFile
import com.craftrom.kernelmanager.utils.root.RootUtils
import com.topjohnwu.superuser.Shell
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.ReadableByteChannel
import java.security.MessageDigest

class FileUtils {

    companion object {

        /**
         * Created by Lineage OS
         */
        private const val TAG = "FileUtils"
        @JvmStatic
        @JvmOverloads
        @Throws(IOException::class)
        fun copyFile(sourceFile: File, destFile: File, progressCallBack: ProgressCallBack? = null) {
            try {
                FileInputStream(sourceFile).channel.use { sourceChannel ->
                    FileOutputStream(destFile).channel.use { destChannel ->
                        if (progressCallBack != null) {
                            val readableByteChannel: ReadableByteChannel = CallbackByteChannel(
                                sourceChannel,
                                sourceFile.length(), progressCallBack
                            )
                            destChannel.transferFrom(readableByteChannel, 0, sourceChannel.size())
                        } else {
                            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size())
                        }
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "Could not copy file", e)
                if (destFile.exists()) {
                    destFile.delete()
                }
                throw e
            }
        }

        interface ProgressCallBack {
            fun update(progress: Int)
        }

        private class CallbackByteChannel(
            private val mReadableByteChannel: ReadableByteChannel, private val mSize: Long,
            private val mCallback: ProgressCallBack
        ) : ReadableByteChannel {
            private var mSizeRead: Long = 0
            private var mProgress = 0
            @Throws(IOException::class)
            override fun close() {
                mReadableByteChannel.close()
            }

            override fun isOpen(): Boolean {
                return mReadableByteChannel.isOpen
            }

            @Throws(IOException::class)
            override fun read(bb: ByteBuffer): Int {
                var read: Int
                if (mReadableByteChannel.read(bb).also { read = it } > 0) {
                    mSizeRead += read.toLong()
                    val progress = if (mSize > 0) Math.round(mSizeRead * 100f / mSize) else -1
                    if (mProgress != progress) {
                        mCallback.update(progress)
                        mProgress = progress
                    }
                }
                return read
            }
        }

        /**
         * END Created by Lineage OS
         */

        fun writeFile(path: String, text: String?, append: Boolean, asRoot: Boolean) {
            if (asRoot) {
                RootFile(path).write(text, append)
                return
            }
            var writer: FileWriter? = null
            try {
                writer = FileWriter(path, append)
                writer.write(text)
                writer.flush()
            } catch (e: IOException) {
                Log.e("FileUtils", "Failed to write $path")
            } finally {
                try {
                    writer?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fun readFile(file: String?): String? {
            return readFile(file, true)
        }

        fun readFile(file: String?, root: Boolean): String? {
            if (root) {
                return RootFile(file).readFile()
            }
            var buf: BufferedReader? = null
            try {
                buf = BufferedReader(FileReader(file))
                val stringBuilder = StringBuilder()
                var line: String?
                while (buf.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }
                return stringBuilder.toString().trim { it <= ' ' }
            } catch (ignored: IOException) {
            } finally {
                try {
                    buf?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }

        fun existFile(file: String?): Boolean {
            return existFile(file, true)
        }

        fun existFile(file: String?, root: Boolean): Boolean {
            return if (!root) File(file).exists() else RootFile(file).exists()
        }

        fun create(text: String?, path: String?) {
            try {
                val logFile = File(path)
                logFile.createNewFile()
                val fOut = FileOutputStream(logFile)
                val myOutWriter = OutputStreamWriter(fOut)
                myOutWriter.append(text)
                myOutWriter.close()
                fOut.close()
            } catch (ignored: java.lang.Exception) {
            }
        }

        fun append(text: String, path: String) {
            RootUtils.runCommand("echo '$text' >> $path")
        }

        fun delete(path: String) {
            if (FileUtils.existFile(path)) {
                RootUtils.runCommand("rm -r $path")
            }
        }




        @JvmStatic
        fun setFilePermissions(): Shell.Job {
            return Shell.su(
                "chmod 0666 /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor",
                "chmod 0666 /sys/devices/system/cpu/cpu4/cpufreq/scaling_governor",
                "chmod 0666 /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq",
                "chmod 0666 /sys/devices/system/cpu/cpu4/cpufreq/scaling_max_freq",
                "chmod 0666 /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq",
                "chmod 0666 /sys/devices/system/cpu/cpu4/cpufreq/scaling_min_freq",
                "chmod 0666 /sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/max_clock_mhz",
                "chmod 0666 /sys/devices/platform/soc/1c00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/min_clock_mhz",
                "chmod 0666 /sys/class/kgsl/kgsl-3d0/devfreq/adrenoboost",
                "chmod 0666 /sys/module/adreno_idler/parameters/adreno_idler_active",
            )
        }

        @JvmStatic
        fun calcHash(file: File, type: String): String? {
            return try {
                val buffer = ByteArray(8192)
                var count: Int
                val digest = MessageDigest.getInstance(type)
                val bis = BufferedInputStream(FileInputStream(file))
                while (bis.read(buffer).also { count = it } > 0) {
                    digest.update(buffer, 0, count)
                }
                bis.close()

                val hash = digest.digest()
                val hexString = StringBuffer()
                for (i in hash.indices) {
                    val hex = Integer.toHexString(0xff and hash[i].toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                }

                hexString.toString()
            } catch (e: Exception) {
                Log.e("Hash Generation", "Failed to generate Hash", e)
                null
            }
        }

        fun removeSuffix(@Nullable s: String?, @Nullable suffix: String?): String? {
            return if (s != null && suffix != null && s.endsWith(suffix)) {
                s.substring(0, s.length - suffix.length)
            } else s
        }

    }
}