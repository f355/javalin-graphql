package org.f355.javalin_graphql.model

import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQLContext
import graphql.GraphQLError

data class GraphQLRequest(
    val query: String,
    val operationName: String? = null,
    val variables: Map<String, Any>? = null
)

fun GraphQLRequest.toExecutionInput(graphQLContext: Any? = null): ExecutionInput =
    ExecutionInput.newExecutionInput()
        .query(this.query)
        .operationName(this.operationName)
        .variables(this.variables ?: emptyMap())
        .context(graphQLContext ?: GraphQLContext.newContext().build())
        .build()

data class GraphQLResponse(
    val data: Any? = null,
    val errors: List<GraphQLError>? = null,
    val extensions: Map<Any, Any>? = null
)

fun ExecutionResult.toGraphQLResponse(): GraphQLResponse {
    // val filteredErrors = if (errors?.isNotEmpty() == true) {
    //     errors.map {
    //         when (it) {
    //             is SimpleKotlinGraphQLError -> it
    //             is Exception -> SimpleKotlinGraphQLError(exception = it, locations = it.locations, path = it.path, errorType = it.errorType)
    //             else -> it
    //         }
    //     }
    // } else null
    val filteredExtensions = if (extensions?.isNotEmpty() == true) extensions else null
    return GraphQLResponse(getData(), null, filteredExtensions)
}
