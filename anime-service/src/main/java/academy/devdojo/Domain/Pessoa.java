package academy.devdojo.Domain;

public class Pessoa {

    private String name;
    private int idade;
    private String email;

    public Pessoa(String name, int idade, String email) {
        this.name = name;
        this.idade = idade;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "name='" + name + '\'' +
                ", idade=" + idade +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final class PessoaBuilder {
        private String name;
        private int idade;
        private String email;

        private PessoaBuilder() {
        }

        public static PessoaBuilder aPessoa() {
            return new PessoaBuilder();
        }

        public PessoaBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PessoaBuilder withIdade(int idade) {
            this.idade = idade;
            return this;
        }

        public PessoaBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Pessoa build() {
            return new Pessoa(name, idade, email);
        }
    }
}