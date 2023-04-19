package com.markusw.chatgptapp.data.network.services

import com.markusw.chatgptapp.core.utils.Resource
import com.markusw.chatgptapp.data.network.ChatGptApi
import com.markusw.chatgptapp.data.network.RequestBody
import com.markusw.chatgptapp.data.network.remote.responses.Message
import com.markusw.chatgptapp.data.network.remote.responses.PromptResponse
import javax.inject.Inject

class ChatGptService @Inject constructor(
    private val api: ChatGptApi
) {
    suspend fun getPromptResponse(prompts: List<Message>): Resource<PromptResponse> {
        return try {

            val body = RequestBody(messages = prompts)
            val call = api.getPromptResponse(body)
            val botResponse = call.execute()

            if (!botResponse.isSuccessful) {
                return Resource.Error("Connection error, check your internet connection and try again.")
            }

            Resource.Success(botResponse.body()!!)

            /*
            //For testing purposes, you can use the code below to simulate a response from the API.
            delay(2000)

            Resource.Success(
                data = PromptResponse(
                    listOf(
                        Choice(
                            finishReason = "stop",
                            index = 0,
                            message = Message(content = "ë")
                        )
                    )
                )
            )

             */
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }
}