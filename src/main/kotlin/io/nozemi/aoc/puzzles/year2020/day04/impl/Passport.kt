package io.nozemi.aoc.puzzles.year2020.day04.impl

import com.github.michaelbull.logging.InlineLogger

private val logger = InlineLogger()

class Passport(
    val birthYear: Int?,
    val issueYear: Int?,
    val expirationYear: Int?,
    val height: String?,
    val hairColor: String?,
    val eyeColor: String?,
    val passportId: String?,
    val countryId: Int?,

    var optionalFields: Array<FieldDescription> = arrayOf(
        FieldDescription.COUNTRY_ID
    ),
    private val ignoredFields: Array<String> = arrayOf(
        "optionalFields"
    )
) {

    fun isValid(): Boolean {
        this.javaClass.declaredFields.forEach {
            if (ignoredFields.contains(it.name)) return@forEach
            if (!optionalFields.contains(FieldDescription.findByFieldName(it.name))
                && (it.get(this) == null || it.get(this).toString().isBlank())
            ) {
                return false
            }
        }

        return true
    }

    override fun toString(): String {
        return "Passport (valid=${isValid()}, birthYear=$birthYear, issueYear=$issueYear, expirationYear=$expirationYear, height=$height, hairColor=$hairColor, eyeColor=$eyeColor, passportId=$passportId, countryId=$countryId)"
    }
}

enum class FieldDescription(
    val shortName: String,
    val fieldName: String,
    val regexMatcher: Pair<String, Int>,
    val regexMatcherStrict: Pair<String, Int>
) {
    BIRTH_YEAR("byr", "birthYear", Pair("byr:([^\\s]+)", 1), Pair("(byr:(19[2-9][\\d]|200[012]))", 2)),
    ISSUE_YEAR("iyr", "issueYear", Pair("iyr:([^\\s]+)", 1), Pair("(iyr:((201[\\d])|(2020)))", 2)),
    EXPIRATION_YEAR("eyr", "expirationYear", Pair("eyr:([^\\s]+)", 1), Pair("(eyr:((202[\\d])|(2030)))", 2)),
    HEIGHT("hgt", "height", Pair("hgt:([^\\s]+)", 1), Pair("(hgt:((1[5-8][0-9]|19[0-3])cm)|((59|6[0-9]|7[0-6])in))", 2)),
    HAIR_COLOR("hcl", "hairColor", Pair("hcl:([^\\s]+)", 1), Pair("(hcl:(#[0-9a-fA-F]{6}))", 2)),
    EYE_COLOR("ecl", "eyeColor", Pair("ecl:([^\\s]+)", 1), Pair("(ecl:(amb|blu|brn|gry|grn|hzl|oth))", 2)),
    PASSPORT_ID("pid", "passportId", Pair("pid:([^\\s]+)", 1), Pair("pid:([\\d]{9}(?!(\\d|\\w)+))", 1)),
    COUNTRY_ID("cid", "countryId", Pair("cid:([^\\s]+)", 1), Pair("cid:(\\d+)", 1));

    companion object {
        fun findByFieldName(fieldName: String): FieldDescription? {
            return values().firstOrNull { it.fieldName == fieldName }
        }
    }
}

fun String.parseToPassport(strictMode: Boolean = false): Passport {
    var birthYear: Int? = null
    var issueYear: Int? = null
    var expirationYear: Int? = null
    var height: String? = null
    var hairColor: String? = null
    var eyeColor: String? = null
    var passportId: String? = null
    var countryId: Int? = null

    logger.debug { "Parsing: $this" }

    FieldDescription.values().forEach {
        val group: Int
        val regex = if (strictMode) {
            group = it.regexMatcherStrict.second
            Regex(it.regexMatcherStrict.first)
        } else {
            group = it.regexMatcher.second
            Regex(it.regexMatcher.first)
        }

        val results = regex.find(this)?.groupValues
        logger.debug { "$it: $results" }

        if (results != null && results.isNotEmpty()) {
            when (it) {
                FieldDescription.BIRTH_YEAR -> birthYear = results[group].toInt()
                FieldDescription.ISSUE_YEAR -> issueYear = results[group].toInt()
                FieldDescription.EXPIRATION_YEAR -> expirationYear = results[group].toInt()
                FieldDescription.HEIGHT -> height = results[group]
                FieldDescription.HAIR_COLOR -> hairColor = results[group]
                FieldDescription.EYE_COLOR -> eyeColor = results[group]
                FieldDescription.PASSPORT_ID -> passportId = results[group]
                FieldDescription.COUNTRY_ID -> countryId = results[group].toInt()
            }
        }
    }

    return Passport(birthYear, issueYear, expirationYear, height, hairColor, eyeColor, passportId, countryId)
}