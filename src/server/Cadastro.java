package server;


import cliente.Aluno;
import cliente.Professores;

import java.util.ArrayList;
import java.util.List;

public class Cadastro {
    private List<Professores> listaProf = new ArrayList<Professores>();
    private List<Aluno> listaAluno = new ArrayList<Aluno>();

    public List<Professores> getListaProf() {
        return listaProf;
    }

    public List<Aluno> getListaAluno() {
        return listaAluno;
    }

    public Professores buscarProfessor(String login) {
        for(Professores professores : listaProf) {
            if(professores.getLogin().equals(login)) {
                return professores;
            }
        }
        return null;
    }

    public boolean cadastrarProfessor(Professores professor) {
        if(buscarProfessor(professor.getLogin())==null) {
            listaProf.add(professor);
            return true;
        }
        return false;
    }

    public Aluno buscarAluno(String login) {
        for(Aluno aluno : listaAluno) {
            if(aluno.getLogin().equals(login)) {
                return aluno;
            }
        }
        return null;
    }

    public boolean cadastrarAluno(Aluno aluno) {
        if(buscarAluno(aluno.getLogin())==null) {
            listaAluno.add(aluno);
            return true;
        }
        return false;
    }

}
