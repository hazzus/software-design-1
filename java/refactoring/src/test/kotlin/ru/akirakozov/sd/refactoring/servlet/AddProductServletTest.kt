package ru.akirakozov.sd.refactoring.servlet

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class AddProductServletTest : BaseTest() {
    private val servlet = AddProductServlet()
    @Test
    fun testSimple() {
        Mockito.`when`(mRequest.getParameter("name")).thenReturn("gucci glasses")
        Mockito.`when`(mRequest.getParameter("price")).thenReturn("1000000")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals("OK\n", result.toString())
    }

    @Test
    fun testNullName() {
        Mockito.`when`(mRequest.getParameter("name")).thenReturn(null)
        Mockito.`when`(mRequest.getParameter("price")).thenReturn("1000000")
        servlet.doGet(mRequest, mResponse)
        Assert.assertEquals("OK\n", result.toString())
    }

    @Test(expected = NumberFormatException::class)
    fun testNullPrice() {
        Mockito.`when`(mRequest.getParameter("name")).thenReturn("gucci glasses")
        Mockito.`when`(mRequest.getParameter("price")).thenReturn(null)
        servlet.doGet(mRequest, mResponse)
    }
}