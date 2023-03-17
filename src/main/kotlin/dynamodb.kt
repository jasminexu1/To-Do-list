
import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class dynamodb {

    val client = HttpClient.newBuilder().build()
    val gson = Gson()

//    fun getArchive(): notUserItems?{
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/archive"))
//            .GET()
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//        val responseParsed = gson.fromJson(response.body(), notUserItems::class.java)
//        //println(responseParsed)
//        return responseParsed
//    }


    fun getUser(user: String): jsonConverter? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/user?user=$user"))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

//        println(response.statusCode())
//        println(response.body())

        val responseParsed = gson.fromJson(response.body(), jsonConverter::class.java)
        //println(responseParsed)
        return responseParsed
    }

    fun putUser(user: Items): Int {

        val jsonString = gson.toJson(user)
//        println("JSON: $jsonString")
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/user"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return response.statusCode() //200
//        println(response.statusCode())
        //println(response.body())

    }



//    fun getItemsDueDate(duedate: String): notUserItems? {
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/items/duedate?duedate=$duedate"))
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
////        println(response.statusCode())
////        println(response.body())
//
//        val responseParsed = gson.fromJson(response.body(), notUserItems::class.java)
//        //println(responseParsed)
//        return responseParsed
//    }

//
//    fun getItemsSortedPriority(duedate: String): Items? {
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/items/sortedpriority?duedate=$duedate"))
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
////        println(response.statusCode())
////        println(response.body())
//
//        val responseParsed = gson.fromJson(response.body(), Items::class.java)
//        //println(responseParsed)
//        return responseParsed
//    }

//    fun getItemsTag(tag: String): notUserItems? {
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/items/tag?tag=$tag"))
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
////        println(response.statusCode())
////        println(response.body())
//
//        val responseParsed = gson.fromJson(response.body(), notUserItems::class.java)
//        //println(responseParsed)
//        return responseParsed
//    }

//    fun getItemsPriority(priority: String): notUserItems? {
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/items/priority?priority=$priority"))
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
////        println(response.statusCode())
////        println(response.body())
//
//        val responseParsed = gson.fromJson(response.body(), notUserItems::class.java)
//        //println(responseParsed)
//        return responseParsed
//    }
//
//    fun putItem(tisk: Tisk): Int {
//
//        val jsonString = gson.toJson(tisk)
////        println("JSON: $jsonString")
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/items"))
//            .header("Content-Type", "application/json")
//            .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        return response.statusCode() //200
////        println(response.statusCode())
////        println(response.body())
//
//    }



//    fun putArchive(tisk: Tisk): Int {
//
//        val jsonString = gson.toJson(tisk)
////        println("JSON: $jsonString")
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/archive"))
//            .header("Content-Type", "application/json")
//            .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        return response.statusCode() //200
////        println(response.statusCode())
////        println(response.body())
//
//    }

//    fun deleteItemsUuid(uuid: String): Int {
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/items/$uuid"))
//            .DELETE()
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        return response.statusCode() //200
//        //println(response.statusCode())
////        println(response.body())
//
//    }

//    fun deleteArchiveUuid(uuid: String): Int {
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://2dii1712yk.execute-api.us-east-2.amazonaws.com/archive/$uuid"))
//            .DELETE()
//            .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        return response.statusCode() //200
//        //println(response.statusCode())
////        println(response.body())
//
//    }
}