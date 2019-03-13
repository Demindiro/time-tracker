package org.salt_inc.timetracker

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private fun setMenu() {
        findViewById<Button>(R.id.view_history).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        findViewById<Button>(R.id.add_activity).setOnClickListener {
            val builder = let {
                AlertDialog.Builder(it)
            }
            val input = EditText(this)
            input.setSingleLine(true)
            builder.setView(input)
            builder.apply {
                setPositiveButton("Add", DialogInterface.OnClickListener { dialog, id ->
                    if (input.text.length > 0)
                        Activity.addActivity(this.context, input.text.toString())
                    setActivityViews()
                })
                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                })
            }
            builder.setTitle("Add new activity")
            builder.show()
        }
    }

    private fun setActivityViews() {
        var activitiesView = findViewById<LinearLayout>(R.id.activities)
        activitiesView.removeAllViews()
        for (activity in Activity.getAllActivities(this)) {
            var button = Button(this)
            button.text = activity
            button.setOnClickListener {
                Activity.setCurrentActivity(this, activity)
                setCurrentActivity()
            }
            activitiesView.addView(button)
        }
        var addButton = Button(this)
        addButton.text = "..."
        activitiesView.addView(addButton)

    }

    private fun setCurrentActivity() {
        findViewById<TextView>(R.id.currentActivity).text = Activity.getCurrentActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setMenu()
        setActivityViews()
        setCurrentActivity()
    }
}
