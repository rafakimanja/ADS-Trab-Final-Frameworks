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
    public void setNome(String nome) { this.nome = nome; }
    public String getNumeroOAB() { return numeroOAB; }
    public void setNumeroOAB(String numeroOAB) { this.numeroOAB = numeroOAB; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public List<Processo> getProcessos() { return processos; }
    public void setProcessos(List<Processo> processos) { this.processos = processos; }
}