package br.inatel.dao;

import br.inatel.model.Marca;
import br.inatel.model.MarcaPatrocinadorInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    // ========== CRUD BÁSICO ========== //

    /**
     * Insere uma nova marca no banco de dados (com ID automático)
     * @param m Objeto Marca a ser inserido
     * @return true se a operação foi bem-sucedida
     */
    public boolean insertMarca(Marca m) {
        String sql = "INSERT INTO Marca (nome) VALUES (?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, m.getNome());
            p.executeUpdate();
            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) m.setIdMarca(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Marca: " + e.getMessage());
            return false;
        }
    }

    /**
     * Atualiza o nome de uma marca existente
     * @param id ID da marca a ser atualizada
     * @param nome Novo nome para a marca
     * @return true se a operação foi bem-sucedida
     */
    public boolean updateNome(int id, String nome) {
        String sql = "UPDATE Marca SET nome = ? WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, nome);
            p.setInt(2, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro update Marca: " + e.getMessage());
            return false;
        }
    }

    // ========== EXCLUSÃO AVANÇADA ========== //

    /**
     * Deleta uma marca e todas as suas dependências em cascata
     * @param id ID da marca a ser deletada
     * @param forcarExclusao Se true, remove todas as dependências
     * @return true se a operação foi bem-sucedida
     */
    public boolean deleteMarca(int id, boolean forcarExclusao) {
        if (!forcarExclusao) {
            return deleteMarca(id); // Chama a versão simples
        }

        String deleteCarroPilotoSql = "DELETE FROM carro_piloto WHERE id_carro IN (SELECT id_carro FROM carro WHERE id_marca = ?)";
        String deleteCarrosSql = "DELETE FROM carro WHERE id_marca = ?";
        String deleteMarcaSql = "DELETE FROM Marca WHERE id_marca = ?";

        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement deleteCarroPiloto = c.prepareStatement(deleteCarroPilotoSql);
             PreparedStatement deleteCarros = c.prepareStatement(deleteCarrosSql);
             PreparedStatement deleteMarca = c.prepareStatement(deleteMarcaSql)) {

            c.setAutoCommit(false); // Inicia transação

            // 1. Exclui registros de carro_piloto vinculados
            deleteCarroPiloto.setInt(1, id);
            deleteCarroPiloto.executeUpdate();

            // 2. Exclui os carros vinculados
            deleteCarros.setInt(1, id);
            deleteCarros.executeUpdate();

            // 3. Finalmente exclui a marca
            deleteMarca.setInt(1, id);
            int affectedRows = deleteMarca.executeUpdate();

            c.commit(); // Confirma a transação
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Erro delete Marca: " + e.getMessage());
            try {
                Connection c = ConnectionDAO.getConnection();
                if (c != null) c.rollback();
            } catch (SQLException ex) {
                System.out.println("Erro no rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                Connection c = ConnectionDAO.getConnection();
                if (c != null) c.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    /**
     * Versão simples de exclusão (não remove dependências)
     * @param id ID da marca a ser deletada
     * @return true se a operação foi bem-sucedida
     */
    public boolean deleteMarca(int id) {
        String sql = "DELETE FROM Marca WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro delete Marca: " + e.getMessage());
            return false;
        }
    }

    // ========== CONSULTAS ========== //

    /**
     * Busca uma marca pelo ID
     * @param id ID da marca
     * @return Objeto Marca ou null se não encontrado
     */
    public Marca findById(int id) {
        String sql = "SELECT id_marca, nome FROM Marca WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Marca m = new Marca(rs.getString("nome"));
                    m.setIdMarca(rs.getInt("id_marca"));
                    return m;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro find Marca: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todas as marcas cadastradas
     * @return Lista de objetos Marca
     */
    public List<Marca> listAll() {
        String sql = "SELECT id_marca, nome FROM Marca";
        List<Marca> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Marca m = new Marca(rs.getString("nome"));
                m.setIdMarca(rs.getInt("id_marca"));
                L.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro list Marca: " + e.getMessage());
        }
        return L;
    }

    /**
     * Lista marcas com seus patrocinadores (JOIN)
     * @return Lista de objetos MarcaPatrocinadorInfo
     */
    public List<MarcaPatrocinadorInfo> listMarcasComPatrocinador() {
        String sql =
                "SELECT m.nome AS marcaNome, p.nome AS patrocinadorNome " +
                        "FROM Marca m " +
                        "JOIN Marca_Patrocinador mp ON m.id_marca = mp.id_marca " +
                        "JOIN Patrocinador p ON p.id_patrocinador = mp.id_patrocinador";
        List<MarcaPatrocinadorInfo> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                L.add(new MarcaPatrocinadorInfo(
                        rs.getString("marcaNome"),
                        rs.getString("patrocinadorNome")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro JOIN Marca-Patrocinador: " + e.getMessage());
        }
        return L;
    }

    // ========== MÉTODOS ADICIONAIS (MELHORIAS) ========== //

    /**
     * Verifica se existem carros vinculados à marca
     * @param idMarca ID da marca
     * @return true se existem carros vinculados
     */
    public boolean verificarCarrosVinculados(int idMarca) {
        String sql = "SELECT COUNT(*) FROM carro WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idMarca);
            ResultSet rs = p.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar carros: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica todas as dependências em cascata
     * @param idMarca ID da marca
     * @return true se existem dependências
     */
    public boolean verificarDependenciasCascata(int idMarca) {
        String sqlCarros = "SELECT COUNT(*) FROM carro WHERE id_marca = ?";
        String sqlCarroPiloto = "SELECT COUNT(*) FROM carro_piloto WHERE id_carro IN (SELECT id_carro FROM carro WHERE id_marca = ?)";

        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement pCarros = c.prepareStatement(sqlCarros);
             PreparedStatement pCarroPiloto = c.prepareStatement(sqlCarroPiloto)) {

            pCarros.setInt(1, idMarca);
            pCarroPiloto.setInt(1, idMarca);

            ResultSet rsCarros = pCarros.executeQuery();
            ResultSet rsCarroPiloto = pCarroPiloto.executeQuery();

            boolean temCarros = rsCarros.next() && rsCarros.getInt(1) > 0;
            boolean temCarroPiloto = rsCarroPiloto.next() && rsCarroPiloto.getInt(1) > 0;

            return temCarros || temCarroPiloto;

        } catch (SQLException e) {
            System.out.println("Erro ao verificar dependências: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reutiliza um ID de marca existente (deleta e recria com mesmo ID)
     * @param idMarca ID a ser reutilizado
     * @param novoNome Novo nome para a marca
     * @return true se a operação foi bem-sucedida
     */
    public boolean reutilizarIdMarca(int idMarca, String novoNome) {
        String sqlDelete = "DELETE FROM Marca WHERE id_marca = ?";
        String sqlInsert = "INSERT INTO Marca (id_marca, nome) VALUES (?, ?)";

        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement delete = c.prepareStatement(sqlDelete);
             PreparedStatement insert = c.prepareStatement(sqlInsert)) {

            c.setAutoCommit(false);

            // 1. Deleta a marca existente (se houver)
            delete.setInt(1, idMarca);
            delete.executeUpdate();

            // 2. Insere a nova marca com o mesmo ID
            insert.setInt(1, idMarca);
            insert.setString(2, novoNome);
            insert.executeUpdate();

            c.commit();
            return true;

        } catch (SQLException e) {
            try {
                Connection c = ConnectionDAO.getConnection();
                if (c != null) c.rollback();
            } catch (SQLException ex) {
                System.out.println("Erro no rollback: " + ex.getMessage());
            }
            System.out.println("Erro ao reutilizar ID: " + e.getMessage());
            return false;
        } finally {
            try {
                Connection c = ConnectionDAO.getConnection();
                if (c != null) c.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica se um ID está disponível para uso
     * @param id ID a ser verificado
     * @return true se o ID não está em uso
     */
    public boolean idDisponivel(int id) {
        String sql = "SELECT COUNT(*) FROM Marca WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            return rs.next() && rs.getInt(1) == 0;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar ID: " + e.getMessage());
            return false;
        }
    }
}