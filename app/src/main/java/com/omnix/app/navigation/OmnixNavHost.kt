package com.omnix.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.omnix.feature.chat.ChatScreen
import com.omnix.feature.council.CouncilScreen
import com.omnix.feature.home.HomeScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object OmnixDestinations {
    const val HOME = "home"
    const val CHAT = "chat/{sessionId}"
    const val AI_COUNCIL = "ai_council/{sessionId}/{prompt}"

    fun chatRoute(sessionId: String) = "chat/$sessionId"

    fun aiCouncilRoute(sessionId: String, prompt: String): String {
        val encoded = URLEncoder.encode(prompt, StandardCharsets.UTF_8.toString())
        return "ai_council/$sessionId/$encoded"
    }
}

@Composable
fun OmnixNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = OmnixDestinations.HOME
    ) {
        composable(OmnixDestinations.HOME) {
            HomeScreen(
                onOpenChat = { sessionId ->
                    navController.navigate(OmnixDestinations.chatRoute(sessionId))
                }
            )
        }

        composable(
            route = OmnixDestinations.CHAT,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId").orEmpty()
            ChatScreen(
                onNavigateBack = { navController.popBackStack() },
                onOpenAiCouncil = { prompt ->
                    navController.navigate(OmnixDestinations.aiCouncilRoute(sessionId, prompt))
                }
            )
        }

        composable(
            route = OmnixDestinations.AI_COUNCIL,
            arguments = listOf(
                navArgument("sessionId") { type = NavType.StringType },
                navArgument("prompt") { type = NavType.StringType }
            )
        ) {
            CouncilScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
