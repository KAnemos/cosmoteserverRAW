package cosmoteserver.web;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.text.Format;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class UserDAO extends DataAccessObject {
	private static UserDAO instance = new UserDAO();
	public static UserDAO getInstance() {
		return instance;
	}
	private User read(ResultSet rs) throws SQLException{
		Long id = new Long(rs.getLong("id"));
		String username = rs.getString("username");
		String password = rs.getString("password");
				String loginstate=rs.getString("loginstate");
		
		String homesessionactive=rs.getString("homesessionactive");
                String TTLPid = rs.getString("TTLP_id");
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
        user.setTTLPid(TTLPid);
        user.setLoginstate(loginstate);
        user.sethomesessionactive(homesessionactive);

		return user;
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
                String TTLP = rsphone.getString("TTLP_id");
		return TTLP;
	}
        private String readAKlabel(ResultSet rslabel) throws SQLException{
		//Long id = new Long(rs.getLong("id"));
		//String username = rs.getString("username");
		//String password = rs.getString("password");
                String label = rslabel.getString("AK_label");
		return label;
	}
        private String readphonedata(ResultSet rsphonedata) throws SQLException{
		//Long id = new Long(rs.getLong("id"));
		String XYpoint = rsphonedata.getString("XYpoint");
		String Accuracy = rsphonedata.getString("Accuracy");
                String Speed  = rsphonedata.getString("Speed");
                

                DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date  date= rsphonedata.getTimestamp("Date");
                String s = df.format(date);
               // String Time  = rsphonedata.getString("Time");
               // String all=XYpoint+" "+Accuracy+" "+Speed+" "+Date+" "+Time;
                String all="";
                if(XYpoint.equals("null")) { 
                   //no data in db  all =""
                }
                else{
                    all=XYpoint+" "+Accuracy+" "+Speed+" "+s;
                }
                
		return all;
	}
        
	public User find(Long id){
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
	public User findByUsername(String username){
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
        	public User findByTTLPid(String ttlpid){
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from user_item where TTLP_id=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, ttlpid);
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
	public List<User> findAll() {
		LinkedList<User> users = new LinkedList<User>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from user_item order by id";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				User user = read(rs);
				users.add(user);
			}
			return users;
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
        public void createphone(User user,String phonenumber,String label,Integer pointer) {
                Long id = getUniqueId();
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into phone_item " + "(id, user_id, phone, label, pointer) "
			+ "values (?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
                        statement.setLong(1, id.longValue());
                        statement.setLong(2, user.getId().longValue());
			//statement.setString(3, user.getPhonenumber(0));
                        statement.setString(3, phonenumber);
                        statement.setString(4, label);
                        statement.setInt(5, 0);
			
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
        public void deletephone(User user,String phonenumber){
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = getConnection();
			String sql = "delete from phone_item where phone=?";
			statement = connection.prepareStatement(sql);
			//Long id = user.getId();
			//statement.setLong(1, id.longValue());
                        statement.setString(1, phonenumber);
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
        public void createphonedatatable(String username,String phonenumber) {
                Long id = getUniqueId();
		PreparedStatement statement = null;
		Connection connection = null;
                try{
			connection = getConnection();
			String sql = "CREATE TABLE "+ "T_" +username+phonenumber+
                                    "(id INTEGER not NULL, " +
                                    " Pointer INTEGER not NULL,"+
                                    " XYpoint VARCHAR(40), " + 
                                    " Accuracy VARCHAR(10), "+
                                    " Speed VARCHAR(10), " +
                                    " Date TIMESTAMP, " +
                                //    " Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"+
                                //    " Date VARCHAR(10), " +
                                //    " Time VARCHAR(10), " +
                                    " PRIMARY KEY ( id ))"; 
			statement = connection.prepareStatement(sql);
			//Long id = user.getId();
			//statement.setLong(1, id.longValue());
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally{
			close(statement, connection);
		}
	}
        
        public void createphonedata(String phonenumber) {
                Long id = getUniqueId();
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into phone_data " + "(id, phone, pointer, d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27,d28,d29,d30,d31,d32,d33,d34,d35,d36,d37,d38,d39,d40,d41,d42,d43,d44,d45,d46,d47,d48,d49,d50) "
			+ "values (?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);
                        statement.setLong(1, id.longValue());
                        statement.setString(2, phonenumber);
			//statement.setString(3, user.getPhonenumber(0));
                        statement.setInt(3, 1);
                        statement.setString(4, "null");
			statement.setString(5, "null");
                        statement.setString(6, "null");
                        statement.setString(7, "null");
			statement.setString(8, "null");
                        statement.setString(9, "null");
                        statement.setString(10, "null");
                        statement.setString(11, "null");
                        statement.setString(12, "null");
                        statement.setString(13, "null");
                        statement.setString(14, "null");
                        statement.setString(15, "null");
                        statement.setString(16, "null");
                        statement.setString(17, "null");
                        statement.setString(18, "null");
                        statement.setString(19, "null");
                        statement.setString(20, "null");
                        statement.setString(21, "null");
                        statement.setString(22, "null");
                        statement.setString(23, "null");
                        statement.setString(24, "null");
                        statement.setString(25, "null");
                        statement.setString(26, "null");
                        statement.setString(27, "null");
                        statement.setString(28, "null");
                        statement.setString(29, "null");
                        statement.setString(30, "null");
                        statement.setString(31, "null");
                        statement.setString(32, "null");
                        statement.setString(33, "null");
                        statement.setString(34, "null");
                        statement.setString(35, "null");
                        statement.setString(36, "null");
                        statement.setString(37, "null");
                        statement.setString(38, "null");
                        statement.setString(39, "null");
                        statement.setString(40, "null");
                        statement.setString(41, "null");
                        statement.setString(42, "null");
                        statement.setString(43, "null");
                        statement.setString(44, "null");
                        statement.setString(45, "null");
                        statement.setString(46, "null");
                        statement.setString(47, "null");
                        statement.setString(48, "null");
                        statement.setString(49, "null");
                        statement.setString(50, "null");
                        statement.setString(51, "null");
                        statement.setString(52, "null");
                        statement.setString(53, "null");
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
        public void deletephonedata(String phonenumber){
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = getConnection();
			String sql = "delete from phone_data where phone=?";
			statement = connection.prepareStatement(sql);
			//Long id = user.getId();
			//statement.setLong(1, id.longValue());
                        statement.setString(1, phonenumber);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally{
			close(statement, connection);
		}
	}
           public void deletephonedata2(String username,String phonenumber){
		PreparedStatement statement = null;
		Connection connection = null;
                String tablename="T_" +username+phonenumber;
		try{
			connection = getConnection();
			String sql = "drop table "+tablename;
			statement = connection.prepareStatement(sql);
			//Long id = user.getId();
			//statement.setLong(1, id.longValue());
                        
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally{
			close(statement, connection);
		}
	}
        public int findPointerfromPhoneItem(String phonenumber, Long userid){
		ResultSet rsphone = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from phone_item where phone=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, phonenumber);
			rsphone = statement.executeQuery();
			if (!rsphone.next()) {
				return -1;
			}                                                
			Integer pointer = rsphone.getInt("pointer");
                        Long user_id=rsphone.getLong("user_id");
                        if(user_id.longValue()==userid.longValue()){
                            return pointer;
                        }
                        else return 0;
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rsphone, statement, connection);
		}
	}
                public void Updatepointer(String phonenumber,Integer pointer) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "update phone_item set pointer=? where phone=?";
			statement = connection.prepareStatement(sql);
                        statement.setInt(1, pointer);
                        statement.setString(2, phonenumber);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
        public void updatephonedata(String phonenumber,Integer pointer,String col,String message) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "update phone_data set pointer=?," +col+"=? where phone=?";
			statement = connection.prepareStatement(sql);
                        statement.setInt(1, pointer);
                        statement.setString(2, message);
			statement.setString(3, phonenumber);
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
        public void requeststm32box(User user,String ak, String message){
        	
        //warning in this sub we do not have the complete list of 
        //fyssas, open, short, signal etc	
            String []parts=message.split(" ");
            String tablename=ak+"_box";
            String Cpstate=parts[0];
            String Cmdstate=parts[1];
            String Actionreq=parts[2];
            String F1G=parts[3];
            String F1R=parts[4];
            String F2G=parts[5];
            String F2R=parts[6];
            String F3G=parts[7];
            String F3R=parts[8];
            PreparedStatement statement = null;
            Connection connection = null;
            try {
		connection = getConnection();
                String sql = "update "+tablename+ " set CP_state=?, CMD_state=?, Action_request=?, Fissa1_Open=?"
                        + ", Fissa1_Short=?, Fissa2_Open=?, Fissa2_Short=?, Fissa3_Open=?, Fissa3_Short=?";
                       // + " where Pointer=?";
                statement = connection.prepareStatement(sql);
                        //statement.setLong(1, id);
                        statement.setString(1, Cpstate);
                        statement.setString(2, Cmdstate);
			statement.setString(3, Actionreq);
                        statement.setString(4, F1G);
                        statement.setString(5,F1R );
                        statement.setString(6, F2G);
                        statement.setString(7,F2R );
                        statement.setString(8, F3G);
                        statement.setString(8,F3R );
                                              
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
 public void updateuserstate(User user,String loginstate){
            PreparedStatement statement = null;
            Connection connection = null;
            try {
       		    connection = getConnection();
                String sql = "update user_item set loginstate=? where id=?";
                       // + " where Pointer=?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, loginstate);
                statement.setLong(2, user.getId().longValue());
			    statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
    }
 public void updateuserhomesessionactive(User user,String homesessionactive){
     PreparedStatement statement = null;
     Connection connection = null;
     try {
		    connection = getConnection();
         String sql = "update user_item set homesessionactive=? where id=?";
                // + " where Pointer=?";
         statement = connection.prepareStatement(sql);
         statement.setString(1, homesessionactive);
         statement.setLong(2, user.getId().longValue());
		    statement.executeUpdate();
	} catch (SQLException e){
		throw new RuntimeException(e);
	} 
	finally {
		close(statement, connection);
	}
}

        public void updatephonedata2(String username,String phonenumber,Integer pointer,String message) {
                String XYpoint,Actina,Speed,Date,Time,DateTime,tablename;
                String []parts=message.split(" ");
                tablename="T_" +username+phonenumber;
                XYpoint=parts[0]+" "+parts[1];
                Actina=parts[2];
                Speed=parts[3];
                Date=parts[4];
                Time=parts[5];
                DateTime=Date+" "+Time;
                Timestamp ts = Timestamp.valueOf(DateTime);
               // Long id = getUniqueId();
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
                        //String sql = "update "+tablename+" set XYpoint=?, Accuracy=?, Speed=?, Date=?";
                      //  String sql = "insert into "+tablename+ "(id, XYpoint, Accuracy, Speed, Date, Time) "
		      //	+ "values (?, ?, ?, ?, ?, ?)";	
                      //  String sql = "insert into "+tablename+ "(id, XYpoint, Accuracy, Speed) "
		     // 	+ "values (?, ?, ?, ?)";
                        String sql = "update "+tablename+ " set XYpoint=?, Accuracy=?, Speed=?, Date=? where Pointer=?";
			statement = connection.prepareStatement(sql);
                        //statement.setLong(1, id);
                        statement.setString(1, XYpoint);
                        statement.setString(2, Actina);
			statement.setString(3, Speed);
                        statement.setTimestamp(4, ts);
                        statement.setInt(5,pointer );
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
                public void fillphonedatatable(String username,String phonenumber,short maxalloweddataperphone){
                String tablename;
                Integer i;
                String text="null";
                tablename="T_" +username+phonenumber;
                
                
                    PreparedStatement statement = null;
                    Connection connection = null;
                    try {
                        connection = getConnection();
                        //r(i=1;i<Constants.POINTS_TO_BE_SAVED+1;i++){
                        for(i=1;i<maxalloweddataperphone+1;i++){

                            Long id = getUniqueId();
                            String sql = "insert into "+tablename+ "(id, Pointer, XYpoint, Accuracy, Speed) values (?, ?, ?, ?,?)";
                            statement = connection.prepareStatement(sql);
                            statement.setLong(1, id);
                            statement.setInt(2, i);
                            statement.setString(3, text);                    
                            statement.setString(4, text);
                            statement.setString(5, text);
                      //  statement.setString(5, Date);
                      //  statement.setString(6, Time);
                            statement.executeUpdate();
                        }
		} catch (SQLException e){
			throw new RuntimeException(e);
		} 
		finally {
			close(statement, connection);
		}
	}
        public List<String> findAllPhoneData(String phonenumber){
                List<String> data = new ArrayList<String>();
		ResultSet rsphone = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from phone_data where phone=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, phonenumber);
			rsphone = statement.executeQuery();
			if (!rsphone.next()) {
				return null;
			}                                                
			//Integer pointer = rsphone.getInt("pointer");
                        for (int i=1; i<Constants.POINTS_TO_BE_SAVED+1; i++) {

                           String istr = Integer.toString(i);
                           String sdata=rsphone.getString("d"+istr);
                           if(sdata.equals("null")){
                               
                           }
                           else{
                                data.add(sdata);
                           }
                        }
                        return data;
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rsphone, statement, connection);
		}
	}
        public List<String> findAllPhoneData2(String username,String phonenumber){
                List<String> data = new ArrayList<String>();
		ResultSet rsphonedata = null;
		PreparedStatement statement = null;
                String tablename="T_" +username+phonenumber;
		Connection connection = null;
		try {
			connection = getConnection();
			//String sql = "select XYpoint, Accuracy, Speed, Date from "+tablename;
                        String sql = "select XYpoint, Accuracy, Speed, Date from "+tablename+" ORDER BY Date DESC";
			statement = connection.prepareStatement(sql);
			rsphonedata = statement.executeQuery();
			while (rsphonedata.next()) {
				String sdata=readphonedata(rsphonedata);
                                
                                data.add(sdata);
			}                                                

                        return data;
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			close(rsphonedata, statement, connection);
		}
	}
    	public void initusers() {
    		ResultSet rs = null;
    		PreparedStatement statement = null;
    		Connection connection = null;
    		try {
    			connection = getConnection();
    			//String sql = "select * from user_item order by id";
    			String sql = "update user_item set loginstate=?, homesessionactive=?";
    			statement = connection.prepareStatement(sql);
    			statement.setString(1, "OFF");
                statement.setString(2, "NO");
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