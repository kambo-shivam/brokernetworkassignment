package com.app.bn.data.remote

/**
 * wrapper class to hold api response
 * */
sealed class UseCaseResult<out T : Any?, out R : String?> {
    //success
    class Success<out T : Any?, out R : String?>(val data: T, val message: String?) :
        UseCaseResult<T, R>()
    //failure
    class Failure<out T : Any?, out R : String?>(val data: T, val message: String?) :
        UseCaseResult<T, R>()
    //Exception/Error
    class Error(val exception: Throwable) : UseCaseResult<Nothing, Nothing>()

}