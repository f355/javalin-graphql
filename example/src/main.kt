package javalin_graphql_example

import com.expediagroup.graphql.SchemaGeneratorConfig
import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.toSchema
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import org.f355.javalin_graphql.GraphQLController

data class Obj(val aField: String, val bField: Int)

class Query {
    fun selectObj(i: Int) = Obj("masse", i)
}

fun main(args: Array<String>) {
    val config = SchemaGeneratorConfig(listOf("org.f355"))
    val queries = listOf(TopLevelObject(Query()))
    val schema = toSchema(config = config, queries = queries)

    val app = Javalin.create().start(7000)

    app.routes {
        get("/", GraphQLController::playgroundHandler)
        path("/graphql") {
            get { ctx -> ctx.redirect("/") }
            post(GraphQLController.graphQLHandler(schema))
        }
    }
}
