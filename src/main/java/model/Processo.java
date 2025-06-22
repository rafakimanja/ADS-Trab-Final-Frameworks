package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String numeroProcesso;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, length = 50)
    private String vara;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "processo_advogado",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "advogado_id")
    )
    private List<Advogado> advogados;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeroProcesso() { return numeroProcesso; }
    public void setNumeroProcesso(String numeroProcesso) { this.numeroProcesso = numeroProcesso; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getVara() { return vara; }
    public void setVara(String vara) { this.vara = vara; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<Advogado> getAdvogados() { return advogados; }
    public void setAdvogados(List<Advogado> advogados) { this.advogados = advogados; }
}