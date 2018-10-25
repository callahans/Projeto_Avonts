/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inatel.projeto.control;

import br.inatel.projeto.model.MySqlConnect;
import br.inatel.projeto.view.CadastroFrame;
import br.inatel.projeto.view.ControleFrame;
import br.inatel.projeto.view.ControleFrame_User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.CallableStatement;

/**
 *
 * @author Daniel
 */
public class CadastrarAction {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null; 
    
    public void RealizaCadastro(int id, String nome, String usuario,
            String senha, int tipoConta, int setor) throws SQLException
    {
        // Abrir conexão com o BD e preparar chamada do Stored Procedure
        conn = MySqlConnect.ConnectDB();
        CallableStatement cStmt =
                conn.prepareCall("{call newUser(?, ?, ?, ?, ?, ?)}");
        try {
            cStmt.setInt(1, id);
            cStmt.setString(2, nome);
            cStmt.setString(3, usuario);
            cStmt.setString(4, senha);
            cStmt.setInt(5, tipoConta);
            cStmt.setInt(6, setor);
            
            // Executar Stored Procedure
            cStmt.execute();
            
            // Sucesso
            JOptionPane.showMessageDialog(null,
                    "Usuário cadastrado com sucesso!");
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            // Fechar CONN
               try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro: Conexão não pode ser fechada!");
            }
        }
        
    }
    
    public void excluirUsuario(int id)
    {
        // Abrir conexão com o BD e preparar chamada do Stored Procedure
        conn = MySqlConnect.ConnectDB();
        String Sql = "DELETE FROM system_user WHERE id = ?;";
        try {
            
            pst = conn.prepareStatement(Sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Usuário descadastrado com sucesso!");
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            // Fechar PST e CONN
               try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro: Conexão não pode ser fechada!");
            }
        }
        
    }

    
}

