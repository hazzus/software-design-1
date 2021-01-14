package ru.akirakozov.sd.refactoring.servlet

import org.junit.Assert
import org.junit.Test

class GetProductsServletTest : BaseTest() {
    private val servlet = GetProductsServlet()

    @Test
    fun testEmpty() {
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals("<html><body>\n</body></html>\n", result.toString())
    }

    @Test
    fun testAll() {
        addSample()
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals(
            "<html><body>\ndiplom kt\t100000000</br>\ndiplom is\t10</br>\n</body></html>\n",
            result.toString()
        )
    }
}