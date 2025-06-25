package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Advogado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroOAB;

    @Column(nullable = false, length = 255)
    private String especialidade;

    @ManyToMany(mappedBy = "advogados")
    private List<Processo> processos;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public String getNumeroOAB() { return numeroOAB; }

    public void setNumeroOAB(String numeroOAB) {
        if (numeroOAB == null || !numeroOAB.matches("^[A-Z]{2}\\d{6}$")) {
            throw new IllegalArgumentException("Número OAB inválido. Deve seguir o padrão UF999999.");
        }
        this.numeroOAB = numeroOAB;
    }

    public String getEspecialidade() { return especialidade; }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade não pode ser vazia");
        }
        this.especialidade = especialidade;
    }

    public List<Processo> getProcessos() { return processos; }

    public void setProcessos(List<Processo> processos) { this.processos = processos; }
}
