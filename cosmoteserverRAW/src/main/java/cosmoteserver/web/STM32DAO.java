package cosmoteserver.web;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class STM32DAO extends DataAccessObject {
private static STM32DAO instance = new STM32DAO();
public static STM32DAO getInstance() {
    return instance;
}
	private STM32 read(ResultSet rs) throws SQLException{
		Long id = new Long(rs.getLong("id"));
                String ip=rs.getString("ip");
		String ak_id = rs.getString("Ak_id");
		String box_label = rs.getString("box_label");
                String cp_state = rs.getString("CP_state");
                String CMD_state = rs.getString("CMD_state");
                String F1O=rs.getString("Fissa1_Open");
                String F2O=rs.getString("Fissa2_Open");
                String F3O=rs.getString("Fissa3_Open");
                String F1S=rs.getString("Fissa1_Short");
                String F2S=rs.getString("Fissa2_Short");
                String F3S=rs.getString("Fissa3_Short");
                String F1SG=rs.getString("Fissa1_Signal");
                String F2SG=rs.getString("Fissa2_Signal");
                String F3SG=rs.getString("Fissa3_Signal");
                String F1N=rs.getString("Fissa1_Newnet");
                String F2N=rs.getString("Fissa2_Newnet");
                String F3N=rs.getString("Fissa3_Newnet");

                
                
               // String Action_request=rs.getString("Action_request");
                
		STM32 stm32 = new STM32();
		stm32.setId(id);
                stm32.set_ip(ip);
		stm32.setAK_id(ak_id);
		stm32.setbox_label(box_label);
                stm32.setCP_state(cp_state);
                stm32.setCMD_state(CMD_state);
                stm32.Fissa1_Open(F1O);
                stm32.Fissa2_Open(F2O);
                stm32.Fissa3_Open(F3O);
                stm32.Fissa1_Short(F1S);
                stm32.Fissa2_Short(F2S);
                stm32.Fissa3_Short(F3S);
                stm32.Fissa1_Signal(F1SG);
                stm32.Fissa2_Signal(F2SG);
                stm32.Fissa3_Signal(F3SG);
                stm32.Fissa1_Newnet(F1N);
                stm32.Fissa2_Newnet(F2N);
                stm32.Fissa3_Newnet(F3N);
                
                
               // stm32.setAction_request(Action_request);
            
		return stm32;
	}
        private String readAK(ResultSet rsAK) throws SQLException{
		//Long id = new Long(rs.getLong("id"));
		//String username = rs.getString("username");
		//String password = rs.getString("password");
                String AK = rsAK.getString("AK_id");
		return AK;
	}
        private String readTTLP(ResultSet rsphone) throws SQLException{
		//Long id = new Long(rs.getLong("id"));
		//String username = rs.getString("username");
		//String password = rs.getString("password");
                String TTLP = rsphone.getString("TTLP");
		return TTLP;
	}
        private String readAKlabel(ResultSet rslabel) throws SQLException{
		//Long id = new Long(rs.getLong("id"));
		//String username = rs.getString("username");
		//String password = rs.getString("password");
                String label = rslabel.getString("AK_label");
		return label;
	}
        private String readbox(ResultSet rsbox) throws SQLException{
		//Long id = new Long(rs.getLong("id"));
		//String username = rs.getString("username");
		//String password = rs.getString("password");
                String label = rsbox.getString("box_label");
		return label;
	}    
	public STM32 find(Long id){
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try
			{
			connection = getConnection();
			String sql = "select * from user_item where id=?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id.longValue());
			rs = statement.executeQuery();
			if (!rs.next()){
				return null;
			}
			return read(rs);
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally {
			close(rs, statement, connection);
		}
	}
	public STM32 findByUsername(String username){
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from user_item where username=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
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
        	public STM32 findByAK(String ak){
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from "+ak+"_box"+" where AK_id=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, ak);
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
            public STM32 findByBoxName(String boxname){
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			//String sql = "select * from KLM_box where box_label=?";
                        String sql = "select * from "+boxname+" where box_label=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, boxname);
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

	public void update(User user) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "update user_item set " + "password=? where id=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getPassword());
			statement.setLong(2, user.getId().longValue());
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
        	public void updateSTM32(String boxlabel, String cpstate) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "update "+boxlabel+" set " + "CP_state=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, cpstate);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
            	public void updateSTM32ip(String boxlabel, String ip) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "update "+boxlabel+" set " + "ip=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, ip);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
        public void updateSTM32relaystatus(String boxlabel, String relaystatus) {
		PreparedStatement statement = null;
		Connection connection = null;
               // int f1 = Integer.parseInt(relaystatus);
              //  String f2=Integer.toBinaryString(f1);
                char[] charArray = relaystatus.toCharArray();
                String F1O,F2O,F3O,F1S,F2S,F3S;
                String F1SG,F2SG,F3SG,F1N,F2N,F3N;
                F1O=F2O=F3O=F1S=F2S=F3S=F1SG=F2SG=F3SG=F1N=F2N=F3N="0";
                F1O=String.valueOf(charArray[0]);F1S=String.valueOf(charArray[1]);F1SG=String.valueOf(charArray[2]);F1N=String.valueOf(charArray[3]);
                F2O=String.valueOf(charArray[4]);F2S=String.valueOf(charArray[5]);F2SG=String.valueOf(charArray[6]);F2N=String.valueOf(charArray[7]);
                F3O=String.valueOf(charArray[8]);F3S=String.valueOf(charArray[9]);F3SG=String.valueOf(charArray[10]);F3N=String.valueOf(charArray[11]);
                
            /*    switch(relaystatus){
                    case "0":
                     F1G=F2G=F3G="0";
                     break;
                    case "1":
                     F2G=F3G="0"; 
                     F1G="1";
                     break;
                    case "2":
                     F1G=F3G="0"; 
                     F2G="1";
                     break;
                    case "3":
                     F3G="0"; 
                     F1G=F2G="1";
                     break;
                    case "4":
                     F1G=F2G="0"; 
                     F3G="1";
                     break;
                    case "5":
                     F2G="0"; 
                     F1G=F3G="1";
                     break;
                    case "6":
                     F1G="0"; 
                     F2G=F3G="1";
                     break;
                    case "7": 
                     F1G=F2G=F3G="1";
                     break;    
                        
                }*/

		try {
			connection = getConnection();
			String sql = "update "+boxlabel+" set " + "Fissa1_Open=?, Fissa1_Short=?, Fissa1_Signal=?, Fissa1_Newnet=?, "
                        + "Fissa2_Open=?, Fissa2_Short=?, Fissa2_Signal=?, Fissa2_Newnet=?, "
					    + "Fissa3_Open=?, Fissa3_Short=?, Fissa3_Signal=?, Fissa3_Newnet=?";
			statement = connection.prepareStatement(sql);
			            statement.setString(1, F1O);
                        statement.setString(2, F1S);
                        statement.setString(3, F1SG);
                        statement.setString(4, F1N);
			            statement.setString(5, F2O);
                        statement.setString(6, F2S);
                        statement.setString(7, F2SG);
                        statement.setString(8, F2N);
			            statement.setString(9, F3O);
                        statement.setString(10, F3S);
                        statement.setString(11, F3SG);
                        statement.setString(12, F3N);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
            }
               public void updateSTM32CMD(String boxlabel, String cmdstate) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "update "+boxlabel+" set " + "CMD_state=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, cmdstate);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
	public void create(User user){
		Long id = getUniqueId();
		user.setId(id);
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into user_item " + "(id, username, password) "
			+ "values (?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id.longValue());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getPassword());
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} finally{
			close(statement, connection);
		}
	}
	public void delete(User user){
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = getConnection();
			String sql = "delete from user_item where id=?";
			statement = connection.prepareStatement(sql);
			Long id = user.getId();
			statement.setLong(1, id.longValue());
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally{
			close(statement, connection);
		}
	}
        public String findTTLP(String username){
		ResultSet rsTTLP = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from user_item where username=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			rsTTLP = statement.executeQuery();
			if (!rsTTLP.next()) {
				return null;
			}
			return readTTLP(rsTTLP);
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rsTTLP, statement, connection);
		}
	}
        public List<String> findAllAK(String TTLP) {
		List<String> AKs = new ArrayList<String>();
		ResultSet rsAK = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from TTLP_AK where TTLP_id=?";
                        statement = connection.prepareStatement(sql);
			statement.setString(1, TTLP);
			rsAK = statement.executeQuery();
			while (rsAK.next()) {
				String AK = readAK(rsAK);
				AKs.add(AK);
			}
			return AKs;
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rsAK, statement, connection);
		}
	}
        public void initboxes() {
            List<String> boxes = new ArrayList<String>();
            ResultSet rsbox = null;
            PreparedStatement statement = null;
	    Connection connection = null;
	    try {
	        connection = getConnection();
		String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='COSMOTEdb'" ;
           statement = connection.prepareStatement(sql);
	        rsbox = statement.executeQuery();
		while (rsbox.next()) {
				String tableName = rsbox.getString("TABLE_NAME");
				boxes.add(tableName);
			}
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rsbox, statement, connection);
		}
            //update boxes to DOWN/IDLE
            for (int i = 0; i < boxes.size(); i++) {
                //check if table name is a box.
                String str2="_box";
                String str1=boxes.get(i);
                if(str1.toLowerCase().contains(str2.toLowerCase())){             
                    updateSTM32(str1,"DOWN");
                    updateSTM32CMD(str1,"IDLE");
                }
             }
            
            return;
        }
        public List<String> findAllAKLabels(String TTLP) {
		List<String> labels = new ArrayList<String>();
		ResultSet rslabel = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from TTLP_AK where TTLP_id=?";
                        statement = connection.prepareStatement(sql);
			statement.setString(1, TTLP);
			rslabel = statement.executeQuery();
			while (rslabel.next()) {
				String label = readAKlabel(rslabel);
				labels.add(label);
			}
			return labels;
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rslabel, statement, connection);
		}
	}
        
        public void requeststm32box(User user,String akid,String cpstate,String cmdstate, String message){
            String []parts=message.split(" ");
            String tablename=akid+"_box";
            String Cpstate=cpstate;
            String Cmdstate=cmdstate;
            //String Actionreq=parts[2];
            String F1G=parts[0];
            String F1R=parts[1];
            String F1B=parts[2];
            String F1Y=parts[3];
            String F2G=parts[4];
            String F2R=parts[5];
            String F2B=parts[6];
            String F2Y=parts[7];
            String F3G=parts[8];
            String F3R=parts[9];
            String F3B=parts[10];
            String F3Y=parts[11];
            PreparedStatement statement = null;
            Connection connection = null;
            try {
		connection = getConnection();
                String sql = "update "+tablename+ " set CP_state=?, CMD_state=?, Fissa1_Open=?"
                        + ", Fissa1_Short=?, Fissa2_Open=?, Fissa2_Short=?, Fissa3_Open=?, Fissa3_Short=?"
                        + " where AK_id=?";
                statement = connection.prepareStatement(sql);
                        //statement.setLong(1, id);
                        statement.setString(1, Cpstate);
                        statement.setString(2, Cmdstate);
			//statement.setString(3, Actionreq);
                        statement.setString(3,F1G);
                        statement.setString(4,F1R);
                        statement.setString(5,F2G);
                        statement.setString(6,F2R);
                        statement.setString(7,F3G);
                        statement.setString(8,F3R);
                        statement.setString(9, akid);
                                              
                      //  statement.setString(5, Date);
                      //  statement.setString(6, Time);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
        }


}