package com.jhughes.sandboxapp.remote.pureRetrofit

class ApiResult<out D, out T>(val data: D?, val error : T?)