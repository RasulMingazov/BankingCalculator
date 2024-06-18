package com.psychojean.core.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PlaceholderTransformation(private val placeholder: String) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        if (text.isNotEmpty()) return TransformedText(text, OffsetMapping.Identity)
        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = 0
            override fun transformedToOriginal(offset: Int): Int = 0
        }
        return TransformedText(AnnotatedString(placeholder), numberOffsetTranslator)
    }
}
