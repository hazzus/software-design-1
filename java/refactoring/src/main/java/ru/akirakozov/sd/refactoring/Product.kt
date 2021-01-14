package ru.akirakozov.sd.refactoring

import java.sql.DriverManager
import java.sql.ResultSet

class Product(val name: String, val price: Long) {
    override fun toString(): String {
        return "name: $name; price: $price";
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Product) {
            return false
        }
        return name == other.name && price == other.price
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = (31 * result + price).toInt()
        return result
    }
}

class ProductDB {
    private fun buildProduct(rs: ResultSet): Product {
        val name = rs.getString("name")
        val price = rs.getLong("price")
        return Product(name, price)
    }

    private fun <R> executeSelect(query: String, mapper : (ResultSet) -> R): R {
        DriverManager.getConnection("jdbc:sqlite:test.db").use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery(query).use { rs ->
                    return mapper(rs)
                }
            }
        }
    }

    fun add(name: String?, price: Long) {
        DriverManager.getConnection("jdbc:sqlite:test.db").use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeUpdate("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"$name\", $price)")
            }
        }
    }

    private fun accumulate(rs: ResultSet): List<Product> {
        val result = arrayListOf<Product>()
        while (rs.next()) {
            result.add(buildProduct(rs))
        }
        return result
    }

    private fun getFoldResult(rs: ResultSet): Long {
        return rs.getLong(1)
    }

    fun getAll(): List<Product> {
        return executeSelect("SELECT * FROM PRODUCT") { accumulate(it) }
    }

    fun getMax(): List<Product> {
        return executeSelect("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1") { accumulate(it) }
    }

    fun getMin(): List<Product> {
        return executeSelect("SELECT * FROM PRODUCT ORDER BY PRICE ASC LIMIT 1") { accumulate(it) }
    }

    fun getCount(): Long {
        return executeSelect("SELECT COUNT(*) FROM PRODUCT") { getFoldResult(it) }
    }

    fun getSum(): Long {
        return executeSelect("SELECT SUM(PRICE) FROM PRODUCT") { getFoldResult(it) }
    }
}