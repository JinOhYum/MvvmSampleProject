package com.example.mvvmsampleproject.util

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

object LogUtil {

    private const val TAG = "LogUtil"
    private const val TAG_TITLE = "KT_LOG"

    private const val ERROR = 1
    private const val WARN = 3
    private const val DEBUG = 7

    private val newLine = System.getProperty("line.separator")

    private var filePath = ""
    private var fileName = ""
    private var fullPath = ""

    private var useFileLog = false

    private val logLevel = 0

    const val LOG = true

    private fun nullToEmpty(src: String?): String = src ?: ""

    fun d(tag: String, msg: String) {
        if ((logLevel and DEBUG) < DEBUG) return
        if (tag.isEmpty() || msg.isEmpty()) return

        if (false) {
            Log.d(nullToEmpty(tag), msg)
        }
    }

    fun d(tag: String, fmt: String, vararg args: Any) {
        if ((logLevel and DEBUG) < DEBUG) return

        val msg = try {
            if (args.isEmpty()) fmt else String.format(fmt, *args)
        } catch (e: RuntimeException) {
            e(tag, e.localizedMessage)
            ""
        } catch (e: Exception) {
            e(tag, e.localizedMessage)
            ""
        }

        d(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if ((logLevel and WARN) < WARN) return
        if (tag.isEmpty() || msg.isEmpty()) return

        if (false) {
            Log.w(nullToEmpty(tag), msg)
        }
    }

    fun w(tag: String, fmt: String, vararg args: Any) {
        if ((logLevel and WARN) < WARN) return

        val msg = try {
            if (args.isEmpty()) fmt else String.format(fmt, *args)
        } catch (e: RuntimeException) {
            e(tag, e.localizedMessage)
            ""
        } catch (e: Exception) {
            e(tag, e.localizedMessage)
            ""
        }

        w(tag, msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        if ((logLevel and ERROR) < ERROR) return
        if (tag.isEmpty() || msg.isEmpty()) return

        if (false) {
            Log.e(nullToEmpty(tag), msg)
        }
    }

    fun e(tag: String, fmt: String, vararg args: Any) {
        if ((logLevel and ERROR) < ERROR) return

        val msg = try {
            if (args.isEmpty()) fmt else String.format(fmt, *args)
        } catch (e: RuntimeException) {
            e(tag, e.localizedMessage)
            ""
        } catch (e: Exception) {
            e(tag, e.localizedMessage)
            ""
        }

        e(tag, msg)
    }

    fun getLogPath(): String = fullPath

    fun setLogFilePath(strPath: String?, strFileName: String?) {
        i("setLogFilePath=$strPath///$strFileName")
        filePath = ""
        fileName = ""

        if (!strPath.isNullOrBlank()) {
            filePath = if (strPath.endsWith("/")) strPath else "$strPath/"
        }

        fileName = strFileName?.let {
            val idx = it.lastIndexOf(".")
            when {
                idx > 0 && idx < it.length - 1 -> it
                idx == it.length - 1 -> "$it.txt"
                else -> {
                    val formatter = SimpleDateFormat("yyyyMMdd")
                    "${formatter.format(Date())}.txt"
                }
            }
        } ?: run {
            val formatter = SimpleDateFormat("yyyyMMdd")
            "${formatter.format(Date())}.txt"
        }

        if (filePath.isEmpty()) {
            filePath = "${Environment.getExternalStorageDirectory().absolutePath}/voiceye/"
        }

        fullPath = "$filePath$fileName"
    }

    fun setUseFileLog(bUse: Boolean) {
        useFileLog = bUse
    }

    fun writeLog(msg: String, nLevel: Int) {
        if (!useFileLog) return
        if (fullPath.isEmpty()) setLogFilePath("", "")

        val formatter1 = SimpleDateFormat("yyyyMMdd")
        val formatter2 = SimpleDateFormat("HH:mm:ss")
        val stTime = formatter2.format(Date())

        val stackTrace = Throwable().stackTrace
        val appendMsg = if (stackTrace.isNotEmpty()) {
            val elt = stackTrace[1]
            "${elt.fileName}: ${elt.className}.${elt.methodName}(line ${elt.lineNumber}): "
        } else ""

        val strLevel = when (nLevel) {
            ERROR -> "ERROR"
            WARN -> "WARN"
            DEBUG -> "DEBUG"
            else -> "DEBUG"
        }

        val bufLogMsg = StringBuilder().apply {
            append("[$strLevel] ")
            append("[${stTime}] ")
            append(appendMsg)
            append(msg)
        }

        val fLogPath = File(filePath)
        if (!fLogPath.exists()) {
            fLogPath.mkdirs()
        }

        try {
            FileWriter(fullPath, true).use {
                it.write(bufLogMsg.toString())
                it.write(newLine)
            }
        } catch (e: IOException) {
            e(TAG, e.localizedMessage)
        }
    }

    fun d() {
        if (LOG) {
            val e = Exception()
            val callerElement = e.stackTrace[1]
            val msg = "${callerElement.fileName} ${callerElement.lineNumber}] "
            Log.d(TAG_TITLE, msg)
        }
    }

    fun d(msg: String) {
        if (LOG) {
            val e = Exception()
            val callerElement = e.stackTrace[1]
            val formattedMsg = "${callerElement.fileName} ${callerElement.lineNumber}] $msg"
            Log.d(TAG_TITLE, formattedMsg)
        }
    }

    fun v(msg: String) {
        if (LOG) {
            val stackTrace = Thread.currentThread().stackTrace
            Log.v(getSimpleClassName(stackTrace[3].className), generateMessage(stackTrace[3], msg))
        }
    }

    fun w(msg: String) {
        if (LOG) {
            val stackTrace = Thread.currentThread().stackTrace
            Log.w(getSimpleClassName(stackTrace[3].className), generateMessage(stackTrace[3], msg))
        }
    }

    fun w(msg: String, t: Throwable) {
        if (LOG) {
            val stackTrace = Thread.currentThread().stackTrace
            Log.w(getSimpleClassName(stackTrace[3].className), generateMessage(stackTrace[3], msg), t)
        }
    }

    fun w(msg: String, e: Exception) {
        if (LOG) {
            val stackTrace = Thread.currentThread().stackTrace
            Log.w(getSimpleClassName(stackTrace[3].className), generateMessage(stackTrace[3], msg), e)
        }
    }
    @JvmStatic
    fun e(msg: String) {
        if (LOG) {
            val e = Exception()
            val callerElement = e.stackTrace[1]
            val formattedMsg = "${callerElement.fileName} ${callerElement.lineNumber}] $msg"
            Log.e(TAG_TITLE, formattedMsg)
        }
    }

    fun e(msg: String, e: Exception) {
        if (LOG) {
            val stackTrace = Thread.currentThread().stackTrace
            Log.e(getSimpleClassName(stackTrace[3].className), generateMessage(stackTrace[3], msg), e)
        }
    }

    fun e(e: Exception) {
        Log.e(TAG, e.localizedMessage ?: "")
    }

    @JvmStatic
    fun i(msg: String) {
        line()
        if (LOG) {
            val e = Exception()
            val callerElement = e.stackTrace[1]
            val formattedMsg = "${callerElement.fileName} ${callerElement.lineNumber}] $msg"
            Log.i(TAG_TITLE, formattedMsg)
        }
    }

    fun line() {
        if (LOG) {
            Log.i(TAG, "====================================================================================================")
        }
    }

    private fun getSimpleClassName(className: String): String {
        val idx = className.lastIndexOf(".")
        return if (idx >= 0) className.substring(idx + 1) else className
    }

    private fun generateMessage(ste: StackTraceElement, msg: String?): String {
        val sb = StringBuilder().apply {
            append(ste.methodName)
            append("[${ste.lineNumber}]")
            if (msg != null) append(msg)
        }
        return sb.toString()
    }

    private fun getFullMsg(msg: String?): String {
        val trace = Thread.currentThread().stackTrace[4]
        val className = trace.className.substringAfterLast(".")
        val methodName = trace.methodName
        return "${className}.${methodName} : ${msg ?: ""}"
    }
}
