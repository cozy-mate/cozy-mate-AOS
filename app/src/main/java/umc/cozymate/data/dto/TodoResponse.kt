package umc.cozymate.data.dto

import com.google.gson.annotations.SerializedName
import umc.cozymate.data.entity.TodoItem


data class TodoResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: Result
){
    data class Result(
        @SerializedName("timePoint")
        val timePoint: String,

        @SerializedName("myTodoList")
        val myTodoList: List<TodoItem>,

        @SerializedName("mateTodoList")
        val mateTodoList: Map<String, List<TodoItem>>
    )
}
