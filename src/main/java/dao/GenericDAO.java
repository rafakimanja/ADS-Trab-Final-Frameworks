package dao;

public interface GenericDAO<T> {
    void salvar(T entidade);
    T buscarPorId(Long id);
    void atualizar(T entidade);
    void remover(Long id);
}