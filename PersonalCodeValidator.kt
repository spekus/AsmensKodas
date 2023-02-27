package lt.asmenskodas

class PersonalCodeValidator() {
    fun isValid(personalCode: String?): Boolean {
        return if (personalCode == null) {
            false
        } else if (!isDigitsOnly(personalCode)) {
            false
        } else {
            validatePersonalCode(personalCode)
        }
    }

    private fun isDigitsOnly(value: String): Boolean {
        return value.matches(Regex("^[0-9]*"))
    }

    private fun validatePersonalCode(personalCode: String): Boolean {
        if (personalCode.length != 11) {
            return false
        }

        val personalCodeDigits = personalCode.map { it.toString().toInt() }
        val personalCodePrefix = personalCodeDigits.take(10)
        val personalCodeSuffix = personalCodeDigits.last()

        var controlNumber =
            calculatePersonalCodeControlNumber(prefix = personalCodePrefix, factor = 1)
        if (controlNumber == 10) {
            controlNumber =
                calculatePersonalCodeControlNumber(prefix = personalCodePrefix, factor = 3)
        }

        return if (controlNumber == 10) {
            personalCodeSuffix == 0
        } else {
            personalCodeSuffix == controlNumber
        }
    }

    private fun calculatePersonalCodeControlNumber(prefix: List<Int>, factor: Int): Int {
        var factor = factor
        var sum = 0
        for (index in prefix.indices) {
            var mod = (index + factor) % 10
            if (mod == 0) {
                mod += 1
                factor += 1
            }
            sum += prefix[index] * mod
        }
        return sum % 11
    }
}