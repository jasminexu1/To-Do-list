
import aws.sdk.kotlin.services.cognitoidentityprovider.CognitoIdentityProviderClient
import aws.sdk.kotlin.services.cognitoidentityprovider.model.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.collections.set

class userHandler {

//    Username and email must be the same for now

    val clientIdVal = "7tal51qvgv26m942o6su5ujljj"
    var filters: ArrayList<String> = arrayListOf("red", "yellow", "blue", "green", "untagged")
    var priorities: ArrayList<String> = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    suspend fun signUpUser(userNameVal: String, passwordVal: String) {
        try {
            signUp(userNameVal, passwordVal, userNameVal)
        } catch (e: Error){
            println(e)
        }
    }

    suspend fun signUp(userNameVal: String?, passwordVal: String?, emailVal: String?) {

        val userAttrs = AttributeType {
            name = "email"
            value = emailVal
        }

        val userAttrsList = mutableListOf<AttributeType>()
        userAttrsList.add(userAttrs)

        val signUpRequest = SignUpRequest {
            username = userNameVal
            userAttributes = userAttrsList
            clientId = clientIdVal
            password = passwordVal
        }

        CognitoIdentityProviderClient { region = "us-east-2" }.use { identityProviderClient ->
            identityProviderClient.signUp(signUpRequest)
        }
    }
    suspend fun confirmSignUp(codeVal: String?, userNameVal: String?) {

        val signUpRequest = ConfirmSignUpRequest {
            username = userNameVal
            clientId = clientIdVal
            confirmationCode = codeVal
        }

        CognitoIdentityProviderClient { region = "us-east-2" }.use { identityProviderClient ->
            identityProviderClient.confirmSignUp(signUpRequest)
        }

        if (userNameVal != null) {
            settings.putString("username", userNameVal)
        }
        syncToDB()
    }

    suspend fun loginUser(userNameVal: String, passwordVal: String) : Boolean {
        return try {
            runBlocking {
                launch {
                    initiateAuth(userNameVal, passwordVal)
                    val jsonConverted = settings.getStringOrNull("username")?.let { db.getUser(it) }
                    if (jsonConverted != null) {
                        syncFromDB(jsonConverted.Items[0].Archive,
                            jsonConverted.Items[0].Tasks,
                        jsonConverted.Items[0].CustomTags,
                        jsonConverted.Items[0].CustomPriorities)
                    }
                }
                settings.putString("username", userNameVal)
                settings.putBoolean("loggedInState", true)
            }
            true
        } catch (e: NotAuthorizedException){
            println("Username or password incorrect")
            false
        }
    }

    suspend fun initiateAuth(userNameVal: String, passwordVal: String): InitiateAuthResponse {

        val authParas = mutableMapOf <String, String>()
        authParas["USERNAME"] = userNameVal
        authParas["PASSWORD"] = passwordVal

        val authRequest = InitiateAuthRequest {
            clientId = clientIdVal
            authParameters = authParas
            authFlow = AuthFlowType.UserPasswordAuth
        }

        CognitoIdentityProviderClient { region = "us-east-2" }.use { identityProviderClient ->
            val response = identityProviderClient.initiateAuth(authRequest)
            return response
        }
    }

    suspend fun syncToDB(){
        val archive = archivedb.allTisks()
        val tasks = daodb.allTisks()
        val newUser = Items(
            uuid = settings.getStringOrNull("username"),
            Archive = archive,
            CustomTags = filters,
            CustomPriorities = priorities,
            Tasks = tasks
        )
        db.putUser(newUser)
    }

    suspend fun syncFromDB(
        archive: ArrayList<Tisk>,
        tasks: ArrayList<Tisk>,
        customTags: ArrayList<String>,
        customPriorities: ArrayList<String>
    ){

//        createUser()
        archivedb.deleteAll()
        daodb.deleteAll()
        println(daodb.allTisks())
        println(archivedb.allTisks())

        archive.forEach {
            archivedb.addTisk(it)
        }
        tasks.forEach {
            daodb.addTisk(it)
        }
        filters = customTags
        priorities = customPriorities
    }
}