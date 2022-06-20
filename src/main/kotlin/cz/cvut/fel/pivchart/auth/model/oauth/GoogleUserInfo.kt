package cz.cvut.fel.pivchart.auth.model.oauth

data class GoogleUserInfo(
    private val attributes: Map<String, Any>
) {
    val username: String
        get() = attributes["name"] as String

    val firstName: String
        get() = attributes["given_name"] as String

    val lastName: String
        get() = attributes["family_name"] as String

    val email: String
        get() = attributes["email"] as String

    val locale: String
        get() = attributes["locale"] as String
}