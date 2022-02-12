import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.FieldValue
import co.elastic.clients.elasticsearch._types.Refresh
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery
import co.elastic.clients.elasticsearch.core.GetRequest
import co.elastic.clients.elasticsearch.core.IndexRequest
import co.elastic.clients.elasticsearch.core.SearchRequest
import co.elastic.clients.elasticsearch.core.SearchResponse
import co.elastic.clients.elasticsearch.core.search.Hit
import co.elastic.clients.elasticsearch.indices.Alias
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.fasterxml.jackson.databind.DeserializationFeature
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient


fun main() {

    val restClient: RestClient = RestClient.builder(HttpHost("localhost", 9200)).build()
    val transport: ElasticsearchTransport = RestClientTransport(restClient, JacksonJsonpMapper().apply {
//        this.objectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    })
    val client: ElasticsearchClient = ElasticsearchClient(transport)


    val json = ("{\"age\":10,\"dateOfBirth\":1471466076564,"
            + "\"fullName\":\"John Doe\"}")

/*    client.indices().create { c ->
        c.index("people")
//            .aliases("foo", Alias.Builder().isWriteIndex(true).build())
    }*/

//    val query = Query.Builder()
//        .term {
//            it
//                .field("value")
//                .value { v -> v.stringValue("foo") }
//        }.build()
//
//    println(query.term().value().stringValue())

/*    client.index { b: IndexRequest.Builder<Any?> ->
        b
            .index("people")
            .id("myID") // test with url-unsafe string
            .document(AppData())
            .refresh(Refresh.True)
    }*/


    val search: SearchResponse<AppData> = client.search(
        { s: SearchRequest.Builder ->
            s
                .index("people")
                .query { q: Query.Builder ->
                    q
                        .wildcard {
                            it
                                .field("message")
                                .wildcard("*tes*")
                        }
                }
        },
        AppData::class.java
    )

    for (hit: Hit<AppData> in search.hits().hits()) {
        println(hit.source()?.intValue)
    }

//    val esData = client.get(
//        { b: GetRequest.Builder ->
//            b
//                .index("people")
//                .id("myID")
//        }, AppData::class.java
//    ).source()
//
//    println(esData?.foo)
}

data class AppData(
    val intValue: Int = 111,
    val message: String = "Test message",
    val foo: String = "message: foo new"
)