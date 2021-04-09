package com.example.seekersgroup.model

sealed class SGResult<out Success, out Failure>

data class Success<out Success>(val value: Success) : SGResult<Success, Nothing>()
data class Failure<out Failure>(val reason: Failure) : SGResult<Nothing, Failure>()