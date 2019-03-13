package org.salt_inc.timetracker

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone.*

class Activity {

    companion object {

        fun getAllActivities(context: Context) : Array<String> {
            var file = File(context.filesDir, "activities")
            if (!file.exists())
                return emptyArray<String>()
            return file.readLines().toTypedArray()
        }

        fun addActivity(context: Context, name : String) : Boolean {
            if (name in getAllActivities(context))
                return false
            var file = File(context.filesDir,  "activities")
            if (!file.exists())
                file.createNewFile()
            file.appendText(name + "\n")
            return true
        }

        fun getCurrentActivity(context: Context) : String {
            var file = File(context.filesDir,  "history")
            if (!file.exists())
                return "<something undefined>"
            var line = file.readLines().last()
            return line.substring(0, line.lastIndexOf(' '))
        }

        fun setCurrentActivity(context: Context, name: String) {
            if (getCurrentActivity(context) == name)
                return
            var file = File(context.filesDir,  "history")
            if (!file.exists())
                file.createNewFile()
            var time   = Calendar.getInstance().time
            // I wanted ISO 8601, but Java / Android hates me
            file.appendText(name + " " + time.time.toString() + "\n")
        }

        fun copyHistory(location: String) {
            // TODO
        }
    }
}