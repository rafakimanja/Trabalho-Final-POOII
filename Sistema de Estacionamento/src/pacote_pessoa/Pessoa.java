package pacote_pessoa;

public class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    public Pessoa(int id, String nome, String cpf, String telefone){
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
    }

    public Pessoa(String nome, String cpf, String telefone){
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

            if(Character.isDigit(posicao_cpf))
            {
                cont++;
            }
        }

        if(cont==11 && cpf.length()==15)
        {
            this.cpf = cpf;
        }
        else
        {
            System.out.println("CPF inválido!");
        }

        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {

        if(telefone.indexOf('0') == 0)
        {
            if(telefone.indexOf('9') == 4)
            {
                this.telefone = telefone;
            }
            else
            {
                System.out.println("Telefone inválido!");
            }
        }
        else if(telefone.indexOf('9') != -1)
        {
            if(telefone.indexOf('9') == 3)
            {
                this.telefone = telefone;
            }
            else
            {
                System.out.println("Telefone inválido!");
            }
        }
    }

    @Override
    public String toString(){
        return "Cliente {" + "\n"
                + "Id="+this.getId() + "\n"
                + "Nome="+this.getNome() + "\n"
                + "CPF="+this.getCpf() + "\n"
                + "Telefone="+this.getTelefone() + "\n"
                + "}";
    }
}
a