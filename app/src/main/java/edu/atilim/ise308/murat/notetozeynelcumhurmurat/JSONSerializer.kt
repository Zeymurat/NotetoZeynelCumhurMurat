package edu.atilim.ise308.murat.notetozeynelcumhurmurat

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener
import java.io.*
import java.lang.StringBuilder

class JSONSerializer(private val filename: String, private val context: Context) {
    @Throws(IOException::class, JSONException::class)
    fun save(noteList: List<Note>) {
        val jArray = JSONArray()
        for (note in noteList) {
            jArray.put(note.convertToJSON())
        }
        var writer: Writer? = null
        try {
            var outFile = context.openFileOutput(filename, Context.MODE_PRIVATE)
            writer = OutputStreamWriter(outFile)
            writer.write(jArray.toString())
        } finally {
            if (writer != null) {
                writer.close()
            }
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun load(): ArrayList<Note> {
        val noteList = ArrayList<Note>()
        val reader: BufferedReader? = null
        try {
            val infile = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(infile))
            val jsonString = StringBuilder()
            for (line in reader.readLine()) {
                jsonString.append(line)
            }
            val jsonArray = JSONTokener(jsonString.toString())
                .nextValue() as JSONArray
            for (i in 0 until jsonArray.length()) {
                noteList.add(Note(jsonArray.getJSONObject(i)))
            }
        } catch (e: FileNotFoundException) {
        } finally {
            reader!!.close()
        }
        return noteList
    }
}