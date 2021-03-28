package com.appetiser.challenge.utils

/**
 * A useful concept from articles
 *
 * Creates a generic data class to cater status of data [success] , [error] , [loading]
 *
 * @param [status] the status [Status]
 * @param [data] a generic data to be carried
 * @param [message] a nullable string to be used on the [loading]
 *
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        /**
         * Provides a [Resource] with success [Status]
         *
         * @param data generic data to be added to the [Resource]
         *
         * @return [Resource] with success [Status] , generic [data]
         */
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        /**
         * Provides a [Resource] with error [Status]
         *
         * @param data generic data to be added to the [Resource]
         * @param msg to be added to the  [Resource]
         *
         * @return [Resource] with error [Status] , generic [data] and message
         */
        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        /**
         * Provides a [Resource] with loading [Status]
         *
         * @param data generic data to be added to the [Resource]
         *
         * @return [Resource] with loading [Status] , generic [data]
         */
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}