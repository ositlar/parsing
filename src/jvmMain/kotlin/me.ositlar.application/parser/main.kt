package me.ositlar.application.parser

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy

fun main() {
    val reader: PdfReader = PdfReader("ИАТИТ.pdf")
    val strategy = SimpleTextExtractionStrategy()
    val text = PdfTextExtractor.getTextFromPage(reader, 1, strategy)
    reader.close()
}
