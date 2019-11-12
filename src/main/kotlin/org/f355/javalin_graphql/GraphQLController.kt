package org.f355.javalin_graphql

import graphql.GraphQL
import graphql.execution.AsyncExecutionStrategy
import graphql.schema.GraphQLSchema
import io.javalin.http.Context
import org.f355.javalin_graphql.model.GraphQLRequest
import org.f355.javalin_graphql.model.toExecutionInput
import org.f355.javalin_graphql.model.toGraphQLResponse

object GraphQLController {
    public fun graphQLHandler(schema: GraphQLSchema): (Context) -> Unit {
        val graphQL = GraphQL.newGraphQL(schema)
            .queryExecutionStrategy(AsyncExecutionStrategy())
            .build()
        return { ctx ->
            val request = ctx.bodyAsClass(GraphQLRequest::class.java)
            val result = graphQL.execute(request.toExecutionInput())
            ctx.json(result.toGraphQLResponse())
        }
    }

    public fun playgroundHandler(ctx: Context) {
        val html = this::class.java.getResourceAsStream("/static/playground/index.html")
        ctx.contentType("text/html")
        ctx.result(html)
    }
}
