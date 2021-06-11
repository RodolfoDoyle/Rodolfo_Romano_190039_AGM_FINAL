package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    //Variaveis estaticas
    private static final String TITLE_APPBAR_EDITA_PERSONAGEM = "Editar personagem";
    private static final String TITLE_APPBAR_NOVO_PERSONAGEM = "Novo personagem";

    //Variaveis
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private EditText campoRg;
    private EditText campoCep;
    private EditText campoGenero;

    //Criação do DAO
    private final PersonagemDAO dao = new PersonagemDAO();

    //Crição do Personagem
    private Personagem personagem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Salvar
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        //verificando o botao
        if (itemId == R.id.activity_formulario_personagem_menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        //campo de edição
        inicializacaoCampos();
        //Botão salvar
        configuraBotao();
        carregaPersonagem();
    }

    private void carregaPersonagem() {
        //salvando o intent na variavel dados
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            //Nome do titulo
            setTitle(TITLE_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        } else {
            //Nome do titulo
            setTitle(TITLE_APPBAR_NOVO_PERSONAGEM);
            //Criação do personagem
            personagem = new Personagem();
        }
    }

    private void preencheCampos() {
        //Coloca as variaveis nos campos
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        campoTelefone.setText(personagem.getTelefone());
        campoEndereco.setText(personagem.getEndereco());
        campoRg.setText(personagem.getRg());
        campoCep.setText(personagem.getCep());
        campoGenero.setText(personagem.getGenero());
    }

    private void configuraBotao() {
        //Função do botão Salvar
        Button botaoSalvar = findViewById(R.id.button_salvar);
        //Listener do botão salvar
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Salva o formulario
                finalizaFormulario();
            }
        });
    }

    private void finalizaFormulario() {
        preenchePersonagem();
        //Verifica o ID
        if (personagem.IdValido()) {
            //Editar
            dao.edita(personagem);
        } else {
            //Salvar
            dao.salva(personagem);
        }
        //Fechar
        finish();
    }

    private void inicializacaoCampos() {
        //Achando as referencias e adicionando a variavel
        campoNome = findViewById(R.id.editText_nome);
        campoAltura = findViewById(R.id.editText_altura);
        campoNascimento = findViewById(R.id.editText_nascimento);
        campoTelefone = findViewById(R.id.editText_telefone);
        campoEndereco = findViewById(R.id.editText_endereco);
        campoRg = findViewById(R.id.editText_rg);
        campoCep = findViewById(R.id.editText_cep);
        campoGenero = findViewById(R.id.editText_genero);

        //Mascara de Nascimento
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
        //Mascara de CEP
        SimpleMaskFormatter smfCep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtwCep = new MaskTextWatcher(campoCep, smfCep);
        campoCep.addTextChangedListener(mtwCep);
        //Mascara de Telefone
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoTelefone, smfTelefone);
        campoTelefone.addTextChangedListener(mtwTelefone);
        //Mascara de RG
        SimpleMaskFormatter smfRG = new SimpleMaskFormatter("NN.NNN.NNN-N");
        MaskTextWatcher mtwRG = new MaskTextWatcher(campoRg, smfRG);
        campoRg.addTextChangedListener(mtwRG);
        //Mascara de Altura
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);


    }

    private void preenchePersonagem() {
        //Nome do campo = nome da variavel
        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String endereco = campoEndereco.getText().toString();
        String rg = campoRg.getText().toString();
        String cep = campoCep.getText().toString();
        String genero = campoGenero.getText().toString();

        //Itens das strings
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setTelefone(telefone);
        personagem.setEndereco(endereco);
        personagem.setRg(rg);
        personagem.setCep(cep);
        personagem.setGenero(genero);
    }
}