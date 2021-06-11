package com.example.listapersonagem.dao;

import com.example.listapersonagem.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
    //Criação de variavel
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;

    //Salvar
    public void salva(Personagem personagemSalvo) {
        personagemSalvo.setId((contadorDeId)); //seta um ID para todos os personagens salvos
        personagens.add(personagemSalvo);
        contadorDeId++; //Adiciona 1 na variavel contadora
    }

    //Editar seleção
    public void edita(Personagem personagem) {
        Personagem personagemEscolhido = null;
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                personagemEscolhido = p;
            }
        }

        if (personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    private Personagem buscaPersonagemID(Personagem personagem) {
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    //Buscar
    public List<Personagem> todos() {
        return new ArrayList<>(personagens);
    }

    //Apagar
    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscaPersonagemID(personagem);

        if (personagemDevolvido != null) {
            personagens.remove(personagemDevolvido);
        }
    }
}
