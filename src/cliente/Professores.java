package cliente;

public class Professores extends Usuarios{
    private String titulacao;

    public void setTitulacao(String titulacao){
        this.titulacao = titulacao;
    }

    public String getTitulacao(){
        return titulacao;
    }
}
