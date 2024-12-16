package main.gui.models.Pessoa;

public class Pessoa {
    private String nome;
    private String cpf;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        if(nome.length() != 0){
            this.nome = nome;
        }else{
            System.out.println("Campo vazio!");
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {

        int cont = 0;

        for (int i = 0; i < cpf.length(); i++)
        {
            char posicao_cpf = cpf.charAt(i);

            if(Character.isDigit(posicao_cpf)) cont++;

        }

        if(cont==11 && cpf.length()==14) this.cpf = cpf;

        else System.out.println("CPF inválido!");

    }

    @Override
    public String toString(){
        return "{ Nome="+this.getNome() + " | "+"CPF="+this.getCpf() + " }";
    }
}
