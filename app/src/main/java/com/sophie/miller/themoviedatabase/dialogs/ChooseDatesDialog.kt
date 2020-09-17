package com.sophie.miller.themoviedatabase.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.sophie.miller.themoviedatabase.MainActivity
import com.sophie.miller.themoviedatabase.databinding.DialogSetDateBinding
import com.sophie.miller.themoviedatabase.utils.Logger
import java.time.Month
import java.time.Year
import java.util.*

class ChooseDatesDialog(val activity: MainActivity) : Dialog(activity) {

    private lateinit var binding: DialogSetDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSetDateBinding.inflate(activity.layoutInflater)
        setContentView(binding.root)
        setCancelable(true)
        getWindow()?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );
        binding.dialogSetDateButton.setOnClickListener {
            //todo if I had more time I would check for different months and leap year max 14 days apart and valid range
            var startDay = getAndValidate(binding.dialogSetDateStartDay, 31)
            var startMonth = getAndValidate(binding.dialogSetDateStartMonth, 12)
            var startYear = getAndValidate(binding.dialogSetDateStartYear, Calendar.getInstance().get(Calendar.YEAR))
            var endDay = getAndValidate(binding.dialogSetDateEndDay, 12)
            var endMonth = getAndValidate(binding.dialogSetDateEndMonth, 31)
            var endYear = getAndValidate(binding.dialogSetDateEndYear, Calendar.getInstance().get(Calendar.YEAR))

            activity.setFiler(
                formDate(startDay, startMonth, startYear),
                formDate(endDay, endMonth, endYear)
            )
            dismiss()
        }
    }

    /**
     * validate input
     * return 0 if not valid or entered
     * else return value
     */
    private fun getAndValidate(editText: EditText, maxValue: Int): Int {
        Logger.testLog(Calendar.getInstance().get(Calendar.YEAR).toString())
        var finalValue = 0
        val startDayInput = editText.text.toString()
        if (startDayInput != "") {
            val i = startDayInput.toIntOrNull()
            if (i != null) {
                finalValue = i
            } else {
                return 0
            }
            if (finalValue < 1 || finalValue > maxValue) {
                Toast.makeText(activity, "Start date is not valid.", Toast.LENGTH_SHORT).show()
                return 0
            } else {
                return finalValue
            }
        } else {
            return 0
        }

    }

    /**
     * creates date valid for URL post
     * if some value is not entered substitutes with lowest possible
     */
    private fun formDate(day: Int, month: Int, year: Int): String {
        var date = ""
        if (day < 1 && month < 1 && year < 1) {
            return ""
        } else {
            if (year < 1900) {
                date = "1900-"
            } else {
                date = "${year}-"
            }
            if (month < 1) {
                date = "${date}1-"
            } else {
                date = "${date}${month}-"
            }
            if (day < 1) {
                date = "${date}1"
            } else {
                date = "${date}${day}"
            }
            return date
        }

    }
}