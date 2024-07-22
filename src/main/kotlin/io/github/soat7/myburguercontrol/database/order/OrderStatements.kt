package io.github.soat7.myburguercontrol.database.order

import io.github.soat7.myburguercontrol.business.enum.OrderStatus

object OrderStatements {

    private const val TABLE_NAME = "\"order\""

    val SELECT_ORDERS = """
        "SELECT o FROM $TABLE_NAME o " +
           "WHERE o.status != '${OrderStatus.FINISHED}' "
           "ORDER BY " +
           "CASE WHEN o.status = '${OrderStatus.READY}' THEN 1 " +
           "     WHEN o.status = '${OrderStatus.IN_PROGRESS}' THEN 2 " +
           "     WHEN o.status = '${OrderStatus.RECEIVED}' THEN 3 " +
           "     ELSE 4 END, " +
           "o.created_at ASC"
    """.trimIndent()
}
