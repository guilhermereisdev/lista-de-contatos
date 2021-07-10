package br.com.guilhermereisapps.listadecontatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ContactDetail : AppCompatActivity() {
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        initToolbar()
        getExtras()
        bindViews()
    }

    // cria a toolbar
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // habilita o botão de voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // faz o clique no botão de voltar da toolbar voltar para a tela anterior
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // recebe os dados enviados da tela anterior
    private fun getExtras() {
        contact = intent.getParcelableExtra(EXTRA_CONTACT)
    }

    // popula os campos da tela atual com os valores recebidos da tela anterior
    private fun bindViews() {
        findViewById<TextView>(R.id.tv_nome_contato).text = contact?.name
        findViewById<TextView>(R.id.tv_telefone_contato).text = contact?.phone
    }

    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
    }
}