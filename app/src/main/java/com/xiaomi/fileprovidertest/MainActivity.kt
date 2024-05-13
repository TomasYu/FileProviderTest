package com.xiaomi.fileprovidertest

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import com.xiaomi.fileprovidertest.ui.theme.FileProviderTestTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FileProviderTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        // 创建文件并分享
        val fileName = "test.txt"
        val fileContents = "Hello, this is a test file!"

        createFileAndShare(this, fileName, fileContents)




    }

    private fun createFileAndShare(context: Context, fileName: String, fileContents: String) {
        // 创建文件
        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use { stream ->
            stream.write(fileContents.toByteArray())
        }

        // 调用分享函数
        shareFile(context, file)
    }

    fun shareFile(context: Context, file: File) {
        val uri: Uri = FileProvider.getUriForFile(this, "com.example.myapp.fileprovider", file)

        val intent = Intent().apply {

            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//            val clipData = ClipData(
//                ClipDescription("Meshes", arrayOf(ClipDescription.MIMETYPE_TEXT_URILIST)),
//                ClipData.Item(uri)
//            )
            data = uri

//            setClipData(clipData)
        }
        intent.setClassName("com.xiaomi.fileproviderreceivetest","com.xiaomi.fileproviderreceivetest.MainActivity");

        startActivity(intent)


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "File Provider",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FileProviderTestTheme {
        Greeting("Android")
    }
}