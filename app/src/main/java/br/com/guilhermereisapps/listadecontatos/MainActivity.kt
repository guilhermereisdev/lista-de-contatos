package br.com.guilhermereisapps.listadecontatos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.guilhermereisapps.listadecontatos.ContactDetail.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemContactListener {
    private val rvList: RecyclerView by lazy {
        findViewById(R.id.rv_list)
    }
    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        fetchListContacts()
        bindViews()
    }

    // cria um mock com dados simulando o retorno de uma API
    private fun fetchListContacts() {
        val list = arrayListOf(
            Contact(
                "Guilherme Reis",
                "(31)99898-4528",
                "guireis.png"
            ), Contact(
                "James Hetfield",
                "(37)95555-3566",
                "james.png"
            ), Contact(
                "Bruce Dickinson",
                "(37)99855-3333",
                "bruce.png"
            ), Contact(
                "Axl Rose",
                "(37)9877-6666",
                "bibiera.png"
            ), Contact(
                "Jorge Vercilo",
                "(37)98756-4444",
                "jorge.png"
            )
        )
        getInstanceSharedPreferences().edit() {
            // Gson transforma a lista em um Json (precisa ser importado no gradle)
            val json = Gson().toJson(list)
            putString("contacts", json)
            commit()
        }
    }

    private fun getInstanceSharedPreferences(): SharedPreferences {
        return getSharedPreferences(
            "br.com.guilhermereisapps.listadecontatos.PREFERENCES",
            Context.MODE_PRIVATE
        )
    }

    // cria a navigation drawer
    private fun initDrawer() {
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        // cria a toolbar
        setSupportActionBar(toolbar)

        // variável necessária para controlar se a drawer está aberta ou fechada
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    // preenche o adapter com os itens da lista
    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    // atualiza a lista
    private fun updateList() {
        val list = getListContacts()
        adapter.updateList(list)
    }

    // obtem a lista de contatos do sharedPreferences
    // transforma o Json de volta em uma lista
    private fun getListContacts(): List<Contact> {
        val list = getInstanceSharedPreferences().getString("contacts", "[]")
        val turnsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnsType)
    }

    // exibe mensagens na parte inferior da tela
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // inicia o menu que fica dentro da toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    // configura o que cada opção selecionada do menu irá fazer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_1 -> {
                showToast("Menu 1")
                return true
            }
            R.id.item_menu_2 -> {
                showToast("Menu 2")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // abre uma nova tela ao clicar sobre um dos contatos da lista,
    // enviando as informações junto
    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this, ContactDetail::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)

    }
}