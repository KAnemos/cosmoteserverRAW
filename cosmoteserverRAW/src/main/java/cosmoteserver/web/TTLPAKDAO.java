/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static cosmoteserver.web.DataAccessObject.getConnection;

/**
 *
 * @author koutote
 */
public class TTLPAKDAO extends DataAccessObject {
	private static STM32DAO instance = new STM32DAO();
	public static STM32DAO getInstance() {
		return instance;
	}
        private TTLPAK read(ResultSet rs) throws SQLException{
		Long id = new Long(rs.getLong("id"));
		String TTLP_id = rs.getString("TTLP_id");
		String AK_id = rs.getString("AK_id");
        String AK_label = rs.getString("AK_label");
        String AK_xlong = rs.getString("AK_xlong");
        String AK_ylat = rs.getString("AK_ylat");
        String mdfsessionactive=rs.getString("mdfsessionactive");
                
		TTLPAK ttlpak = new TTLPAK();
		ttlpak.setId(id);
		ttlpak.setTTLPid(TTLP_id);
		ttlpak.setAKid(AK_id);
        ttlpak.setAKlabel(AK_label);
        ttlpak.setAKxlong(AK_xlong);
        ttlpak.setAKylat(AK_ylat);
        ttlpak.setmdfsessionactive(mdfsessionactive);
        

            
		return ttlpak;
	}
    
        public TTLPAK findByAKlabel(String aklabel){
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from TTLP_AK where AK_label=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, aklabel);
			rs = statement.executeQuery();
			if (!rs.next()) {
				return null;
			}
			return read(rs);
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rs, statement, connection);
		}
	}
       public TTLPAK findByAKid(String akid){
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from TTLP_AK where AK_id=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, akid);
			rs = statement.executeQuery();
			if (!rs.next()) {
				return null;
			}
			return read(rs);
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rs, statement, connection);
		}
	}
       public void updatettlpakmdfsessionactive(TTLPAK ttlpak,String mdfsessionactive){
    	     PreparedStatement statement = null;
    	     Connection connection = null;
    	     try {
    			    connection = getConnection();
    	         String sql = "update TTLP_AK set mdfsessionactive=? where id=?";
    	                // + " where Pointer=?";
    	         statement = connection.prepareStatement(sql);
    	         statement.setString(1, mdfsessionactive);
    	         statement.setLong(2, ttlpak.getId().longValue());
    			    statement.executeUpdate();
    		} catch (SQLException e){
    			throw new RuntimeException(e);
    		} 
    		finally {
    			close(statement, connection);
    		}
    	}
   	public void initaks() {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			//String sql = "select * from user_item order by id";
			String sql = "update TTLP_AK set mdfsessionactive=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, "NO");
			statement.executeUpdate();
			return;
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rs, statement, connection);
		}
	}
}
