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
import java.util.*

class MockInterceptor : Interceptor {

    private val FILE_EXTENSION = ".json"
    private val CONTENT_TYPE = "application/json"
    private val context: Context = GithubApplication.INSTANCE.applicationContext
    val MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

    override fun intercept(chain: Interceptor.Chain): Response {

        //Dumy Progress Time
        try {
            Thread.sleep(1500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val listSuggestionFileName: MutableList<String> = ArrayList()
        val method = chain.request().method.toLowerCase()

        var response = chain.proceed(chain.request())

        // Get Request URI.
        val uri = chain.request().url.toUri()
        Logger.d("--> Request url: [" + method.toUpperCase() + "]" + uri.toString())

        val defaultFileName: String = getFileName(chain)

        listSuggestionFileName.add(defaultFileName)

        val responseFileName: String? = getFirstFileNameExist(listSuggestionFileName, uri)
        Logger.d("responseFileName : $responseFileName")
        if (responseFileName != null) {
            val fileName: String = getFilePath(uri, responseFileName)
            val fileNameArr = fileName.split("/")
            val assetFilePath = fileName.substring(fileNameArr[0].length + 1)
            Logger.d("Read data from file: $fileName")
            try {
                val inputStream: InputStream = context.getAssets().open(assetFilePath)
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
                            .toResponseBody(MEDIA_TYPE)
                    )
                    .addHeader("content-type", CONTENT_TYPE)
                    .build()
            } catch (e: IOException) {
                //Logger.e(e.message)
            }
        } else {
            for (file in listSuggestionFileName) {
                Logger.e("File not exist: " + getFilePath(uri, file))
            }
            response = chain.proceed(chain.request())
        }

        Logger.d("<-- END [" + method.toUpperCase() + "]" + uri.toString())
        return response!!
    }

    private fun upCaseFirstLetter(str: String): String? {
        return str.substring(0, 1).toUpperCase() + str.substring(1)
    }

    @Throws(IOException::class)
    private fun getFirstFileNameExist(
        inputFileNames: List<String>,
        uri: URI
    ): String? {
        var mockDataPath = uri.path
        mockDataPath = mockDataPath.substring(1, mockDataPath.lastIndexOf('/'))

        Logger.d("Scan files in url host : ${uri.host} ")
        Logger.d("Scan files in url path : ${uri.path} ")
        Logger.d("Scan files in data path : $mockDataPath")
        Logger.d("Scan files in inputFileNames : $inputFileNames")

        //List all files in folder
        val files: Array<String> = context.getAssets().list(mockDataPath) as Array<String>
        for (fileName in inputFileNames) {
            if (fileName != null) {
                for (file in files) {
                    if (fileName == file) {
                        Logger.d("Scan files return fileName : $fileName")
                        return fileName
                    }
                }
            }
        }
        return null
    }

    private fun getFileName(chain: Interceptor.Chain): String {
        val fileName =
            chain.request().url.pathSegments[chain.request().url.pathSegments.size - 1]
        return if (fileName.isEmpty()) "index" + FILE_EXTENSION else fileName
    }

    private fun getFilePath(uri: URI, fileName: String): String {
        val path = if (uri.path.lastIndexOf('/') != uri.path.length - 1) {
            uri.path.substring(0, uri.path.lastIndexOf('/') + 1)
        } else {
            uri.path
        }
        return uri.host + path + fileName
    }


}