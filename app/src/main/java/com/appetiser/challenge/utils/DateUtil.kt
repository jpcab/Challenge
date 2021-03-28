package com.appetiser.challenge.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jp Cabrera on 3/27/2021.
 *
 * Contain functions that caters Date manipulations
 */
class DateUtil {

    companion object {

        /**
         * Converts milliseconds to Date
         *
         * @return the converted milliseconds to Date
         * @param millisDate milliseconds to be converted to Date
         */
        fun convertMillisToDate(millisDate: Long): Date? {
            if (millisDate == 0L) {
                return null
            }
            return Date(System.currentTimeMillis())
        }

        /**
         * Changes Date format
         *
         * @param date contains the date to be formatted to string
         * @param dateFormat the string format to be used
         * @return the formatted date based on the [dateFormat]
         */
        fun changeDateFormat(date: Date?, dateFormat: String): String {
            if (date == null) {
                return ""
            }

            val simpleDateFormat = SimpleDateFormat(dateFormat)
            return simpleDateFormat.format(date)
        }

        /**
         * Updates the format on a date based on string
         *
         * @param dateString string data to be updated
         *
         * @return a formatted string value with specified date format
         */
        fun dateFormatUpdate(dateString: String?): String {

            if (dateString == null) {
                return ""
            }

            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            try {
                val date: Date = format.parse(dateString)
                val format = SimpleDateFormat("MMM dd, yyyy")
                return format.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}