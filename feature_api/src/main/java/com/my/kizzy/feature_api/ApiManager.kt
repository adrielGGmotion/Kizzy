package com.my.kizzy.feature_api

import com.my.kizzy.common.preference.Prefs
import com.my.kizzy.domain.rpc.RpcService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kizzy.gateway.DiscordWebSocket
import kizzy.gateway.entities.presence.Presence
import java.util.*
import javax.inject.Inject

class ApiManager @Inject constructor(
    private val discordWebSocket: DiscordWebSocket,
    private val prefs: Prefs
) {
    private var server: NettyApplicationEngine? = null
    private val apiKey: String
        get() {
            var key = prefs.getString(API_KEY, null)
            if (key == null) {
                key = UUID.randomUUID().toString()
                prefs.putString(API_KEY, key)
            }
            return key
        }

    fun startServer() {
        if (server == null) {
            server = embeddedServer(Netty, port = 8080) {
                install(ContentNegotiation) {
                    json()
                }
                routing {
                    get("/") {
                        call.respondText("KizzyRPC API is running!")
                    }
                    authenticate {
                        post("/rpc") {
                            val presence = call.receive<Presence>()
                            discordWebSocket.sendActivity(presence)
                            call.respond(HttpStatusCode.OK)
                        }
                    }
                }
            }
            server?.start(wait = false)
        }
    }

    fun stopServer() {
        server?.stop(1000, 2000)
        server = null
    }

    private fun Route.authenticate(build: Route.() -> Unit) {
        install(createRouteScopedPlugin("ApiKeyAuth") {
            onCall { call ->
                val apiKeyHeader = call.request.header("X-Api-Key")
                if (apiKeyHeader != apiKey) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid API Key")
                }
            }
        }) {
            build()
        }
    }

    companion object {
        const val API_KEY = "api_key"
    }
}
