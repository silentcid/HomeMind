package com.silentcid.homemind.core.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultRetrofitAdapterFactory : CallAdapter.Factory() {

    private companion object {
        const val FIRST_INDEX = 0
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // Must be Call<*>
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }

        val outerType = getParameterUpperBound(FIRST_INDEX, returnType)

        // Must be Call<Result<*>> (outerType should be Result<*>)
        if (outerType !is ParameterizedType || getRawType(outerType) != Result::class.java) {
            return null
        }

        // Extract the actual inner type: Result<T> → T
        val innerType = getParameterUpperBound(FIRST_INDEX, outerType)

        // ✅ Safe cast now: the generic <T> is preserved in ResultCallAdapter<T>
        return ResultCallAdapter<Any>(innerType)
    }
}
