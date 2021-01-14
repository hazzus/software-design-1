package ru.akirakozov.sd.refactoring

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.servlet.BaseTest
import java.lang.Math.pow
import java.sql.DriverManager
import kotlin.math.pow

class ProductDBTest {
    private val db = ProductDB()

    @Before
    fun setup() {
        val c = DriverManager.getConnection(BaseTest.URL)
        c.createStatement().use { stmt -> stmt.executeUpdate("DROP TABLE IF EXISTS PRODUCT") }
        c.createStatement().use { stmt ->
            val sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)"
            stmt.executeUpdate(sql)
        }
    }

    @Test
    fun testSimple() {
        db.add("hello", 32)
        Assert.assertEquals(arrayListOf(Product("hello", 32)), db.getAll())
    }

    @Test
    fun testMore() {
        val exp = arrayListOf<Product>()
        for (i in 1..20) {
            val price = (-2).toDouble().pow(i.toDouble()).toLong()
            db.add(i.toString(), price)
            exp.add(Product(i.toString(), price))
        }
        Assert.assertEquals(exp, db.getAll())
        Assert.assertEquals(1.shl(20), db.getMax()[0].price)
        Assert.assertEquals(-(1.shl(19)), db.getMin()[0].price)
        Assert.assertEquals(20, db.getCount())
        Assert.assertEquals(699050, db.getSum())
    }
}