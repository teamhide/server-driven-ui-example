package com.teamhide.serverdrivenui.common.config.database

import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory
import org.springframework.transaction.reactive.TransactionSynchronizationManager
import reactor.core.publisher.Mono

class RoutingConnectionFactory : AbstractRoutingConnectionFactory() {
    override fun determineCurrentLookupKey(): Mono<Any> {
        return TransactionSynchronizationManager.forCurrentTransaction().map {
            if (it.isCurrentTransactionReadOnly) {
                ConnectionFactoryType.READER
            }
            ConnectionFactoryType.WRITER
        }
    }
}
