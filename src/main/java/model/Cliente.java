package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String cpfCnpj;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false)
    private Long telefone;

    @OneToMany(mappedBy = "cliente")
    private List<Processo> processos;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCpfCnpj() { return cpfCnpj; }

    public void setCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ não pode ser vazio");
        }
        String cpfCnpjLimpo = cpfCnpj.replaceAll("[^\\d]", ""); //remove todos os caracteres que não são dígitos
        if (!(cpfCnpjLimpo.length() == 11 || cpfCnpjLimpo.length() == 14)) {
            throw new IllegalArgumentException("CPF/CNPJ inválido. Deve conter 11 (CPF) ou 14 (CNPJ) dígitos numéricos");
        }
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public Long getTelefone() { return telefone; }

    public void setTelefone(Long telefone) {
        if (telefone == null) {
            throw new IllegalArgumentException("Telefone não pode ser nulo");
        }
        if (telefone <= 0) {
            throw new IllegalArgumentException("Telefone deve ser um número positivo");
        }
        this.telefone = telefone;
    }

    public List<Processo> getProcessos() { return processos; }

    public void setProcessos(List<Processo> processos) { this.processos = processos; }
}
