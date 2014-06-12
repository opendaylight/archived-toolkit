package org.sdnhub.dnsguard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.dougharris.dns.ResourceRecord;

import org.sdnhub.dnsguard.renders.DnsRecordReply;
import org.sdnhub.dnsguard.renders.DnsUsage;
import org.sdnhub.dnsguard.renders.Violator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Luis Chiang
 * This class is in charge to create a connection to the database and keep a 
 * memory buffer to store the dns responses. 
 * 
 */
public class DnsGuardPersistence implements IDnsGuard {
	protected static final Logger log = LoggerFactory
			.getLogger(DnsGuardPersistence.class);
	
	private String dbdriver = "com.mysql.jdbc.Driver";
	private String dbserver,dbname,user,passwd;
	private int dbport;
	private Connection connection;
	
	// the counter for connections retry
	private int conn_retry;
	
	// 
	private int INTERNAL_BUFFER = 200;
	
	//
	private int INTERNAL_BUFFER_MAX = 400;
	
	private List<ResourceRecord> internal_buffer_rr; //= new ArrayList<ResourceRecord>(INTERNAL_BUFFER_MAX);
	private List<DnsReply> internal_buffer_dnsreply; //= new ArrayList<DnsReply>(INTERNAL_BUFFER_MAX);
	
	private String sqlCreation = "";
	
	
	public String getSqlCreation() {
		return sqlCreation;
	}

	public void setSqlCreation(String sqlCreation) {
		this.sqlCreation = sqlCreation;
	}

	public DnsGuardPersistence(String dbserver, int dbport, String dbname, String user, String passwd, int internal_buffer_init_size, int internal_buffer_max_size, String sqlCreation){
		
		this.dbserver = dbserver;
		this.dbport = dbport;
		this.dbname = dbname;
		this.user = user;
		this.passwd = passwd;
		
		this.INTERNAL_BUFFER = internal_buffer_init_size;
		this.INTERNAL_BUFFER_MAX = internal_buffer_max_size;
		
		internal_buffer_rr = new ArrayList<ResourceRecord>(INTERNAL_BUFFER_MAX);
		internal_buffer_dnsreply = new ArrayList<DnsReply>(INTERNAL_BUFFER_MAX);
		
		this.sqlCreation = sqlCreation;
	}
	
	public Boolean isConnected(){
		 
		if (this.connection == null){
			return false;
		}
		
		try {
			 
			return this.connection.isValid(10);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			//return false;
		}
		
		return false;
	}
	
	public Boolean Connect(){
		
		try {
			
			// fix in case of class not found, edit file: \main\src\assemble\bin.xml
			// and add the line: <include>mysql:mysql-connector-java</include>
			
	          Class.forName(dbdriver).newInstance();
	          String url = "jdbc:mysql://" + dbserver +":" + String.valueOf(dbport) +"/" + dbname;
	          
	          this.connection = DriverManager.getConnection(url,user,passwd);
	          createTables();
	          conn_retry = 0;
	          
	          return true;
	           
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		conn_retry++;
		
		log.error("Could not connect to db, retry {}", conn_retry);
		
		return false;
	}
	
	private void createTables(){
		
		// we dont check if the table exist, just create without deleting before
		try{
			
			Statement statement = connection.createStatement();
			
			statement.execute(sqlCreation);

			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(List<ResourceRecord> reply_answers){

		// List<ResourceRecord> reply_answers = dnsreply.getAllAnswers();
		
		this.internal_buffer_rr.addAll(reply_answers);
		
		// log.info("rr internal buffer size {}", this.internal_buffer_rr.size());
		
		if (this.internal_buffer_rr.size() >= INTERNAL_BUFFER){
			// flush local cache
			
			try {
				
				if(!isConnected()){
					Connect();
				}
				
				Statement statement = connection.createStatement();
			
			for (ResourceRecord rr: internal_buffer_rr) {
				
				// TODO: improve security
				try{
					
				    String sql = "INSERT INTO bulkreply (tstamp, request, type, ttl, len, data) values (NOW(), '" + rr.getName() + "', " + rr.getType() + ", " + rr.getTTL() + ", " + rr.getLength() + ", '" + rr.dataToString() +"');";			    
				    statement.addBatch(sql);
			    
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
			statement.executeBatch();
			statement.close();
			
			log.info("rr flush at {}", this.internal_buffer_rr.size());
			
			this.internal_buffer_rr.clear();
			((ArrayList<ResourceRecord>)this.internal_buffer_rr).trimToSize();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void save(DnsReply dnsreply){
		// TODO: this functions is not working propertly, should iterate using
		// internal_buffer_dnsreply and save the ip.src ip.dst udp.src udp.dst

		this.internal_buffer_dnsreply.add(dnsreply);

		log.info("dns internal buffer size {}",
				this.internal_buffer_dnsreply.size());

		if (this.internal_buffer_dnsreply.size() >= INTERNAL_BUFFER) {
			// flush local cache
			   flushLocalCache();
		}
	}
	
	private void flushLocalCache(){
		
		if(this.internal_buffer_dnsreply.size() > 0){
			
			try {
				 
				if(!isConnected()){
					Connect();
				}
				
				Statement statement = connection.createStatement();
	
				for (DnsReply tmpReply : this.internal_buffer_dnsreply) {
	
					List<ResourceRecord> tmp_rr = tmpReply.getAllAnswers();
	
					for (ResourceRecord rr : tmp_rr) {
	
						// TODO: Improve security
						try {
	
							String sql = "INSERT INTO bulkreply (tstamp, request, type, ttl, len, data, ip_src, ip_dst, udp_src, udp_dst) values ( NOW(), '"
									+ rr.getName()
									+ "', "
									+ rr.getType()
									+ ", "
									+ rr.getTTL()
									+ ", "
									+ rr.getLength()
									+ ", '"
									+ rr.dataToString() + "','" + tmpReply.getSrc_ip() +  "','" + tmpReply.getDst_ip() + "'," + tmpReply.getUdp_src() + "," + tmpReply.getUdp_dst() + ");";
							statement.addBatch(sql);
	
						} catch (Exception ex) {
							ex.printStackTrace();
						}
	
					}
				}
	
				statement.executeBatch();
				statement.close();
	
				log.info("internal buffer flush at {}", this.internal_buffer_dnsreply.size());
				
				this.internal_buffer_dnsreply.clear();
				((ArrayList<DnsReply>)this.internal_buffer_dnsreply).trimToSize();
				
	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<String> lazyresolv(String appIp) {
	
		List<String> results = new ArrayList<String>();
		
		try {
			
			if(!isConnected()){
				Connect();
			}
			
			Statement statement = connection.createStatement();

			String sql = "select distinct request  FROM bulkreply where data = '" + appIp + "'";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()){
				
				results.add( rs.getString(1) );
			}
			
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	public List<String> appsbyip(String sourceIp) {
		
		List<String> results = new ArrayList<String>();
		
		try {
			
			if(!isConnected()){
				Connect();
			}
			
			Statement statement = connection.createStatement();

			String sql = "select distinct request FROM bulkreply where ip_dst = '" + sourceIp + "'";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()){
				
				results.add( rs.getString(1) );
			}
			
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
	
	public List<Violator> getViolators() {
		
		List<Violator> results = new ArrayList<Violator>();
		 
		try {
			
			if(!isConnected()){
				Connect();
			}
			
			Statement statement = connection.createStatement();
			
			String sql = "select ip_dst, tstamp FROM bulkreply where ip_src not in (select configval from config where configname = 'localdns') group by ip_dst";
			
			//String sql = "select ip_dst, tstamp FROM dnsspy.bulkreply LIMIT 10";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()){
				
				results.add( new Violator(rs.getString(1),  rs.getString(2)) );
			}
			
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	public void save(DnsRequest dnsrequest){
		// TODO:
		
	}
	
	public DnsReply resolve(DnsRequest dnsrequest){
		// TODO: 
		DnsReply dnsreply = null;
		
		
		return dnsreply;
		
	}

	@Override
	public String echo(String in) { 
		
		return in + " OK, I will not hit the db using a echo";
	}

	@Override
	public String setLocalDnsServer(String local_dns) { 
		 
		Statement statement;
		
		try {
			
			if(!isConnected()){
				Connect();
			}
			
			statement = connection.createStatement();
			
			String sql = "update config set configval = '" + local_dns + "' where configname = 'localdns'";
			
			// false if is an update
			if( statement.execute(sql) == false){
				
				return "Updated Ok";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return "Failed to Update";
	}

	@Override
	public String getLocalDnsServer() { 
		
		String results = new String();
		try {
			
			if(!isConnected()){
				Connect();
			}
			
			Statement statement = connection.createStatement();
			
			String sql = "select configval from config where configname = 'localdns'";
			
			ResultSet rs = statement.executeQuery(sql);
			
			rs.next();
			results = rs.getString(1); 
			
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public List<DnsUsage> getExternalDnsUsage(int top) { 
		
		List<DnsUsage> results = new ArrayList<DnsUsage>();
		 
		try {
			
			if(!isConnected()){
				Connect();
			}
			
			Statement statement = connection.createStatement();
			
			String sql = "select ip_src, count(ip_src) FROM bulkreply WHERE ip_src not in (Select configval from config where configname = 'localdns') group by ip_src order by count(ip_src) limit " + Integer.toString(top);
			
			ResultSet rs = statement.executeQuery(sql);
			
			int count = 0;
			while (rs.next() && count < top){
				results.add( new DnsUsage(rs.getString(1),  rs.getInt(2), count) ) ;
				count++;
			}
			
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public List<DnsRecordReply>getDatabaseDnsRecords(int limit, int offset) {
		

		// SELECT idbulkreply, request, type, ttl, len, data, ip_src, ip_dst, udp_src, udp_dst, tstamp FROM  bulkreply;

		List<DnsRecordReply> results = new ArrayList<DnsRecordReply>();
		 
		try {
			
			if(!isConnected()){
				Connect();
			}
			
			Statement statement = connection.createStatement();
			
			String sql = "SELECT ip_src, ip_dst, request, type, data, tstamp FROM bulkreply where ip_src not in (select configval from config where configname = 'localdns') limit " + String.valueOf(limit) ;	
			
			ResultSet rs = statement.executeQuery(sql);
			
			// public DnsRecordReply(String srcIp 1, String dstIp 2, String request 3, int respType 4, String data 5, String timestamp 6)

			int count = 0;
			while (rs.next() && count < limit){
				DnsRecordReply ndsr = new DnsRecordReply(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),  rs.getString(6));
				results.add(ndsr) ;
				count++;
			}
			
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
	
	public void savePropsAndConnect(Properties props){
	
		// Do nothing.. used for the business class object
	}
	
}
