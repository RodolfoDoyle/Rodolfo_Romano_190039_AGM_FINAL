package com.example.listapersonagem.ui.activities;


//Referencias
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;
public class ListaPersonagemActivity extends AppCompatActivity {


    //Variaveis
    public static final String TITLE_APPBAR = "Agenda Pessoal";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        //Nome
        setTitle(TITLE_APPBAR);
        //Botão FAB
        botaoFAB();
        configuraLista();
    }

    @Override
    protected void onResume() {
        super.onResume();

        atualizaPersonagem();
    }

    private void atualizaPersonagem() {
        //Limpar lista
        adapter.clear();
        //Lista com as infos do DAO
        adapter.addAll(dao.todos());
    }

    private void remove(Personagem personagem) {
        //Remove personagem do DAO
        dao.remove(personagem);
        //Remove personagem da lista
        adapter.remove(personagem);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Remover item ao segurar
        getMenuInflater().inflate(R.menu.activity_lista_personagens_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Dialogo para apagar item selecionado
        configuraMenu(item);
        return super.onContextItemSelected(item);
    }

    private void configuraMenu(@NonNull MenuItem item) {
        //ID do menu
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_personagem_menu_remover) {
            //Alerta ao remover
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Contato")
                    .setMessage("Tem certeza que deseja remover?")
                    //Confirmar remover
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            //remove
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Nao", null)
                    .show();
        }
    }

    private void configuraLista() {
        //Referencia
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        //Add personagem a lista
        listaDePersonagem(listaDePersonagens);
        configuraItemPerClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }


    private void configuraItemPerClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Overide para seleção de personagem
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Posição da lista aonde foi selecionado
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(position);
                //Trocar para activity
                abreFormularioModoEditar(personagemEscolhido);
            }
        });
    }

    //Modo edição
    private void abreFormularioModoEditar(Personagem personagem) {
        //Intent para o formulario
        Intent vaiParaFormulario = new Intent(this, FormularioPersonagemActivity.class);
        //Trazer infos
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagem);
        //Vai para FormularioPersonagemActivity
        startActivity(vaiParaFormulario);
    }

    private void listaDePersonagem(ListView listaDePersonagens) {
        //adapter com ref da lista
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }

    //configurando o FloatingActionButton(FAB)
    private void botaoFAB() {
        //FAB
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);
        //Função do botão FAB
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Troca para FormularioPersonagemActivity
                abreFormularioSalvar();
            }
        });
    }

    private void abreFormularioSalvar() {
        //troca para ormularioPersonagemActivity
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }
}


