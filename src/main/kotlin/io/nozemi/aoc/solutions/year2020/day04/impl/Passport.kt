package io.nozemi.aoc.solutions.year2020.day04.impl

data class Height(
    val value: Int,
    val unit: String
) {

    fun isValid(strictMode: Boolean = true): Boolean {
        if (strictMode) {
            if (unit == "cm" && value >= 150 && value <= 193) {
                return true
            }
            if (unit == "in" && value >= 59 && value <= 76) {
                return true
            }
        } else if (unit.isNotEmpty()) return true

        return false
    }
}

data class Passport(
    val birthYear: Int? = null,
    val issueYear: Int? = null,
    val expirationYear: Int? = null,
    val height: Height,
    val hairColor: String? = null,
    val eyeColor: String? = null,
    val passportId: String? = null,
    val countryId: String? = null
) {

    fun isValid(strictMode: Boolean = true): Boolean {
        this::class.java.declaredFields.forEach {
            if (it.name == "countryId") return@forEach
            if (it.get(this) == null) return false
            if (it.name == "height" && !(it.get(this) as Height).isValid(strictMode)) return false

            if (strictMode) {
                when (it.name) {
                    "birthYear" -> if (!birthYearRule(it.get(this) as Int)) return false
                    "issueYear" -> if (!issueYearRule(it.get(this) as Int)) return false
                    "expirationYear" -> if (!expirationYearRule(it.get(this) as Int)) return false
                    "hairColor" -> if (!hairColorRule(it.get(this) as String)) return false
                    "eyeColor" -> if (!eyeColorRule(it.get(this) as String)) return false
                    "passportId" -> if (!passportIdRule(it.get(this) as String)) return false
                }
            }
        }

        return true
    }

    private fun birthYearRule(year: Int?): Boolean = year != null && year >= 1920 && year <= 2002
    private fun issueYearRule(year: Int?): Boolean = year != null && year >= 2010 && year <= 2020
    private fun expirationYearRule(year: Int?): Boolean = year != null && year >= 2020 && year <= 2030
    private fun hairColorRule(hairColor: String?): Boolean = hairColor != null && hairColor.length == 7 && Regex("(#[a-f0-9]{6})").matches(hairColor)
    private fun eyeColorRule(eyeColor: String?): Boolean = eyeColor != null && eyeColor.length == 3 && Regex("(amb|blu|brn|gry|grn|hzl|oth)").matches(eyeColor)
    private fun passportIdRule(passportId: String?): Boolean = passportId != null && passportId.length == 9 && Regex("[\\d]{9}").matches(passportId)
}