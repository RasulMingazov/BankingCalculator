package com.psychojean.core

fun String.asThousand(
    separator: Char = ' ',
    vararg decimalSeparators: Char = charArrayOf('.', ',')
): String {
    val original = this
    return buildString {
        original.indices.forEach { position ->
            val realPosition = original.lastIndex - position
            val character = original[realPosition]
            insert(0, character)
            if (position != 0 &&
                realPosition != 0 &&
                position % 3 == 2 &&
                !decimalSeparators.any { it == character }
            )
                insert(0, separator)
        }
    }
}