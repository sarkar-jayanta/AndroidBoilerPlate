package com.boilerplate.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class AuthException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)