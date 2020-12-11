package edu.atilim.ise308.murat.notetozeynelcumhurmurat

import android.content.Context
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.WithHint
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private var tempNote = Note()
    private var noteList: ArrayList<Note>? = null
    private var jsonSerializer: JSONSerializer? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null
    private var showDividers: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* !!!temporary code!!!
        val buttonShowNote = findViewById<Button>(R.id.button_show_note)
        buttonShowNote.setOnClickListener {
            val dialogShowNote = DialogShowNote()
            dialogShowNote.sendNoteSelected(tempNote)
            dialogShowNote.show(supportFragmentManager, "123")
        }*/
        val fabNewNote = findViewById<FloatingActionButton>(R.id.fab_new_note)
        fabNewNote.setOnClickListener {
            val dialogNewNote = DialogNewNote()
            dialogNewNote.show(supportFragmentManager, "124")
        }
        jsonSerializer = JSONSerializer("NoteToZeynelCumhurMurat",applicationContext)

        try {
            noteList = jsonSerializer!!.load()
        }catch (e: Exception){
            noteList = ArrayList()
            Log.e(TAG, "Error loading notes...")
        }

        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter = NoteAdapter(this, noteList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        if (showDividers) {
            recyclerView!!.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager.VERTICAL
                )
            )
        } else {
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val prefers = getSharedPreferences("Note to Zeynel Cumhur Murat", Context.MODE_PRIVATE)
        showDividers = prefers.getBoolean("dividers", true)
        if (showDividers) {
            recyclerView!!.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager.VERTICAL
                )
            )
        } else {
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }

    fun createNewNote(note: Note) {
        //tempNote = note
        noteList!!.add(note)
        noteList!!.notifyDataSetChanged()
    }

    fun showNote(noteToShow: Int) {
        val dialog = DialogShowNote()
        noteList?.get(noteToShow)?.let { dialog.sendNoteSelected(it) }
        dialog.show(supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val b = when (id) {
            R.id.settings -> {
                val intentToSettings = Intent(this, SettingActivity::class.java)
                startActivity(intentToSettings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return b
    }
    private fun saveNotes(){
        try {
            jsonSerializer!!.save(this.noteList!!)
        }catch (e:Exception){
            Log.e(TAG, "Error loading notes...")
        }
    }

    override fun onPause() {
        super.onPause()
        saveNotes()
    }
}