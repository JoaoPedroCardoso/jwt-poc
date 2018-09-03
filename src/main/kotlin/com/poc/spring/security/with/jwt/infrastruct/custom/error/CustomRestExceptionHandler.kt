package com.poc.spring.security.with.jwt.infrastruct.custom.error

import com.poc.spring.security.with.jwt.infrastruct.exceptions.ForbiddenException
import com.poc.spring.security.with.jwt.infrastruct.exceptions.InvalidTokenException
import com.poc.spring.security.with.jwt.infrastruct.exceptions.UnauthorizedException
import com.poc.spring.security.with.jwt.infrastruct.extension.objectToJson
import javassist.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.ArrayList
import javax.validation.ConstraintViolationException


/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@ControllerAdvice
class CustomRestExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ArrayList<String>()
        for (error in ex.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return handleExceptionInternal(
            ex, apiError, headers, apiError.status!!, request
        )
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val error = ex.parameterName + " parameter is missing"

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
        ex: ConstraintViolationException, request:WebRequest):ResponseEntity<Any> {
        val errors = ArrayList<String>()
        for (violation in ex.constraintViolations)
        {
            errors.add(
                violation.rootBeanClass.name + " " +
                        violation.propertyPath + ": " + violation.message
            )
        }

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!)

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException, request: WebRequest
    ): ResponseEntity<Any> {
        val error = ex.name + " should be of type " + ex.requiredType?.name

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val error = "No handler found for " + ex.httpMethod + " " + ex.requestURL

        val apiError = ApiError(HttpStatus.NOT_FOUND, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val builder = StringBuilder()
        builder.append(ex.method)
        builder.append(
            " method is not supported for this request. Supported methods are "
        )
        ex.supportedHttpMethods?.forEach { t -> builder.append(t.toString() + " ") }

        val apiError = ApiError(
            HttpStatus.METHOD_NOT_ALLOWED,
            ex.localizedMessage, builder.toString()
        )
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val builder = StringBuilder()
        builder.append(ex.contentType)
        builder.append(" media type is not supported. Supported media types are ")
        ex.supportedMediaTypes.forEach { t -> builder.append(t.toString() + ", ") }

        val apiError = ApiError(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            ex.localizedMessage, builder.substring(0, builder.length - 2)
        )
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    @ExceptionHandler( Exception::class )
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage, listOf("error occurred", ex.toString()  )
        )
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    @ExceptionHandler( NotFoundException::class )
    fun handleNotFoundException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.NOT_FOUND, ex.localizedMessage, listOf("you tried to look for something that does not exist.",
                if(request.parameterMap.isNotEmpty())"check your request parameters: " + request.parameterMap.objectToJson
                    ()else "check the send values on path variable.")
        )
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    @ExceptionHandler( ForbiddenException::class )
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.FORBIDDEN, ex.localizedMessage, listOf("access forbidden")
        )
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    @ExceptionHandler( UnauthorizedException::class )
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.UNAUTHORIZED, ex.localizedMessage, listOf("access unauthorized")
        )
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }

    @ExceptionHandler( InvalidTokenException::class )
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleInvalidTokenException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.FORBIDDEN, ex.localizedMessage, listOf("access forbidden", ex.toString())
        )
        return ResponseEntity(
            apiError, HttpHeaders(), apiError.status!!
        )
    }
}
