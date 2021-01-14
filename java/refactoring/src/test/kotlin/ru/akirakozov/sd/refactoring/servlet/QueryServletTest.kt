package ru.akirakozov.sd.refactoring.servlet

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class QueryServletTest : BaseTest() {
    private val servlet = QueryServlet()

    @Test
    fun testMaxEmpty() {
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("max")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals("<html><body>\n<h1>Product with max price: </h1>\n</body></html>\n", result.toString())
    }

    @Test
    fun testMax() {
        addSample()
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("max")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals(
            "<html><body>\n<h1>Product with max price: </h1>\ndiplom kt\t100000000</br>\n</body></html>\n",
            result.toString()
        )
    }

    @Test
    fun testMinEmpty() {
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("min")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals("<html><body>\n<h1>Product with min price: </h1>\n</body></html>\n", result.toString())
    }

    @Test
    fun testMin() {
        addSample()
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("min")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals(
            "<html><body>\n<h1>Product with min price: </h1>\ndiplom is\t10</br>\n</body></html>\n",
            result.toString()
        )
    }

    @Test
    fun testSumEmpty() {
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("sum")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals("<html><body>\nSummary price: \n0\n</body></html>\n", result.toString())
    }

    @Test
    fun testSum() {
        addSample()
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("sum")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals(
            "<html><body>\nSummary price: \n100000010\n</body></html>\n",
            result.toString()
        )
    }

    @Test
    fun testCountEmpty() {
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("count")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals("<html><body>\nNumber of products: \n0\n</body></html>\n", result.toString())
    }

    @Test
    fun testCount() {
        addSample()
        Mockito.`when`(mRequest.getParameter("command")).thenReturn("count")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals(
            "<html><body>\nNumber of products: \n2\n</body></html>\n",
            result.toString()
        )
    }
}