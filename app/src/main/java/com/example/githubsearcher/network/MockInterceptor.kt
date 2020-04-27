package com.example.githubsearcher.network

import android.content.Context
import com.example.githubsearcher.GithubApplication
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI

class MockInterceptor : Interceptor {

    private val context: Context = GithubApplication.INSTANCE.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        //Dumy Progress Time
        try {
            Thread.sleep(1500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        var response = chain.proceed(chain.request())

        // Get Request URI.
        val uri = chain.request().url.toUri()

        val method = chain.request().method
        Logger.d("--> Request url: [$method]  $uri")

        val fileName: String = getFileName(chain)

        val responseFileName: String? = getFirstFileNameExist(fileName, uri)

        responseFileName ?: run {
            for (file in fileName) {
                Logger.e("File not exist: " + getFilePath(uri, fileName))
            }
            response = chain.proceed(chain.request())
        }

        responseFileName?.run {
            Logger.d("responseFileName : $this")
            val tempFileName: String = getFilePath(uri, this)
            val tempFileNameArr = tempFileName.split("/")
            val assetFilePath = tempFileName.substring(tempFileNameArr[0].length + 1)
            Logger.d("Read data from file: $tempFileName")
            try {
                val inputStream: InputStream = context.assets.open(assetFilePath)
                val bufferedReader =
                    BufferedReader(InputStreamReader(inputStream))
                val responseStringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    responseStringBuilder.append(line).append('\n')
                }
                Logger.d("Response: $responseStringBuilder")

                response = Response.Builder()
                    .code(200)
                    .message(responseStringBuilder.toString())
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(
                        responseStringBuilder.toString().toByteArray()
                            .toResponseBody("application/json; charset=utf-8".toMediaType())
                    )
                    .addHeader("content-type", "application/json")
                    .build()
            } catch (e: IOException) {
                Logger.e(e.message.toString())
            }
        }
        return response
    }

    private fun getFirstFileNameExist(
        inputFileNames: String,
        uri: URI
    ): String? {
        var mockDataPath = uri.path
        mockDataPath = mockDataPath.substring(1, mockDataPath.lastIndexOf('/'))

        Logger.d("Scan files in inputFileNames : $inputFileNames")
        Logger.d("Scan files in data path : $mockDataPath")

        //List all files in folder
        val files: Array<String> = context.getAssets().list(mockDataPath) as Array<String>
        for (file in files) {
            if (inputFileNames == file) {
                Logger.d("Scan files return fileName : $inputFileNames")
                return inputFileNames
            }
        }
        return null
    }

    private fun getFileName(chain: Interceptor.Chain): String {
        return chain.request().url.pathSegments[chain.request().url.pathSegments.size - 1]
    }

    private fun getFilePath(uri: URI, fileName: String): String {
        val path = if (uri.path.lastIndexOf('/') != uri.path.length - 1) {
            uri.path.substring(0, uri.path.lastIndexOf('/') + 1)
        } else {
            uri.path
        }
        val filePath = StringBuilder(uri.host).append(path).append(fileName)
        return filePath.toString()
    }
}