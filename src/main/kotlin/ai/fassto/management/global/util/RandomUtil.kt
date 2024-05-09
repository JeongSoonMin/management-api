package ai.fassto.management.global.util

class RandomUtil {
    companion object {
        fun getRandomNumAlpha(length: Int) : String {
            val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            return List(length) { charset.random() }
                .joinToString("")
        }
    }
}