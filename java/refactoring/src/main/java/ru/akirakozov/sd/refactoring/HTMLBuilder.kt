package ru.akirakozov.sd.refactoring

import ru.akirakozov.sd.refactoring.Product
import java.io.PrintWriter

class HTMLBuilder(private val writer: PrintWriter) {
    init {
        writer.println("<html><body>")
    }

    fun addHeader(text: String) {
        writer.println("<h1>$text</h1>")
    }

    fun addProduct(product: Product) {
        writer.println("${product.name}\t${product.price}</br>")
    }

    fun addLine(text: String) {
        writer.println(text)
    }

    fun addEnding() {
        writer.println("</body></html>")
    }

}