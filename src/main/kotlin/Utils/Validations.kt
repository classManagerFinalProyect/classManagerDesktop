package Utils

import java.util.regex.Pattern

fun isAlphabetic(text: String) = Pattern.compile("^[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE).matcher(text).find()
fun isAlphanumeric(text: String) = Pattern.compile("^[a-zA-Z0-9 ]+$", Pattern.CASE_INSENSITIVE).matcher(text).find()
fun isDate(text: String) = Pattern.compile("^(([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2}))?\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
fun isTime(text: String) = Pattern.compile("^[0-9]{2}:[0-9]{2}$", Pattern.CASE_INSENSITIVE).matcher(text).find()
