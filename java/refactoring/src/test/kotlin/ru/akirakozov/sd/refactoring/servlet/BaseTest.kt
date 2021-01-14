package ru.akirakozov.sd.refactoring.servlet

import org.junit.Before
import org.mockito.Mockito
import java.io.PrintWriter
import java.io.StringWriter
import java.sql.DriverManager
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class BaseTest {
    protected val result = StringWriter()
    private val writer = PrintWriter(result)

    protected val mRequest: HttpServletRequest = Mockito.mock(HttpServletRequest::class.java)
    protected val mResponse: HttpServletResponse = Mockito.mock(HttpServletResponse::class.java)

    @Before
    fun setup() {
        val c = DriverManager.getConnection(URL)
        c.createStatement().use { stmt -> stmt.executeUpdate("DROP TABLE IF EXISTS PRODUCT") }
        c.createStatement().use { stmt ->
            val sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)"
            stmt.executeUpdate(sql)
        }
        Mockito.`when`(mResponse.writer).thenReturn(writer)
    }

    companion object {
        const val URL = "jdbc:sqlite:test.db"

        fun addSample() {
            val query = "INSERT INTO PRODUCT (NAME, PRICE) VALUES ('diplom kt', 100000000), ('diplom is', 10)"
            val conn = DriverManager.getConnection(URL)
            conn.createStatement().use { stmt -> stmt.executeUpdate(query) }
        }
    }
}