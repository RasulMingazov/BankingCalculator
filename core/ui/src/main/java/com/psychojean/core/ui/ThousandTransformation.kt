package com.psychojean.core.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object ThousandTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val out = text.text.asThousand()

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {

                val rightOffset = text.lastIndex - offset
                val commasToTheRight = rightOffset / 3
                return out.lastIndex - rightOffset - commasToTheRight
            }

            override fun transformedToOriginal(offset: Int): Int {

                val totalCommas = ((text.length - 1) / 3).coerceAtLeast(0)
                val rightOffset = out.length - offset
                val commasToTheRight = rightOffset / 4
                return (offset - (totalCommas - commasToTheRight))
            }
        }
        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}

fun String.asThousand(separator: Char = ' '): String {
    val original = this
    return buildString {
        original.indices.forEach { position ->
            val realPosition = original.lastIndex - position
            val character = original[realPosition]
            insert(0, character)
            if (position != 0 && realPosition != 0 && position % 3 == 2) {
                insert(0, separator)
            }
        }
    }
}