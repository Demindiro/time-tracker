package org.salt_inc.timetracker

import android.app.ActionBar
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        var table = findViewById<LinearLayout>(R.id.table)
        var prevStart = Date(0);
        var end  : TextView? = null
        var time : TextView? = null
        var lines : List<String>
        try {
            lines = File(filesDir, "history").readLines()
        } catch(ex: FileNotFoundException) {
            return
        }
        var formatDate = SimpleDateFormat("dd/mm HH:mm")
        for (line in lines) {
            var datestr = line.substring(line.lastIndexOf(' ') + 1, line.length - 1)
            var date = Date(datestr.toLong())
            if (end != null && time != null) {
                end .text = formatDate.format(date)
                var delta = (date.time - prevStart.time) / 1000 / 60
                time.text = String.format("%02d", delta / 60) + ":" + String.format("%02d", delta % 60)
            }
            var row   = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL;
            var name  = TextView(this)
            var start = TextView(this)
            end       = TextView(this)
            time      = TextView(this)
            name .layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f)
            start.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f)
            end  .layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f)
            time .layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.1f)
            row.addView(name)
            row.addView(start)
            row.addView(end)
            row.addView(time)
            table.addView(row)
            start.text = formatDate.format(date)
            prevStart = date
            name.text = line.substring(0, line.lastIndexOf(' '))
        }
        end ?.text = "-"
        time?.text = "-"
    }
}
