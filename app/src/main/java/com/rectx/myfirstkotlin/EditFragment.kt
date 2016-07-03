package com.rectx.myfirstkotlin


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import java.util.*

/**
 * Created by thuan on 7/3/16.
 */
class EditFragment: Fragment() {
    val TODO_ID_KEY: String = "todo_id_key"
    val realm: Realm = Realm.getDefaultInstance()

    var todo: ToDo? = null
    companion object {
        fun newInstance(id: String): EditFragment {
            var args: Bundle = Bundle()
            args.putString("todo_id_key", id)
            var editFragment: EditFragment = newInstance()
            editFragment.arguments = args
            return editFragment
        }

        fun newInstance(): EditFragment {
            return EditFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            verticalLayout{
                padding =  dip(30)
                var title = editText {
                    id = R.id.todo_title
                    hintResource = R.string.title_hint
                }

                var desc = editText {
                    id = R.id.todo_desc
                    hintResource = R.string.description_hint
                }

                button {
                    id = R.id.todo_add
                    textResource = R.string.add_todo
                    onClick { view -> creatToDoFrom(title, desc) }
                }
            }
        }.view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(arguments != null && arguments.containsKey(TODO_ID_KEY)) {
            val todoId = arguments.getString(TODO_ID_KEY)
            todo = realm.where(ToDo::class.java).equalTo("id", todoId).findFirst()
            val todoTitle = find<EditText>(R.id.todo_title)
            todoTitle.setText(todo?.title)
            val todoDesc = find<EditText>(R.id.todo_desc)
            todoDesc.setText(todo?.description)
            val add = find<Button>(R.id.todo_add)
            add.setText(R.string.save)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun creatToDoFrom(title: EditText, desc: EditText) {
        realm.beginTransaction()
        var t = todo?: realm.createObject(ToDo::class.java)
        t.id = todo?.id?: UUID.randomUUID().toString()
        t.title = title.text.toString()
        realm.commitTransaction()

        activity.supportFragmentManager.popBackStack()
    }
}