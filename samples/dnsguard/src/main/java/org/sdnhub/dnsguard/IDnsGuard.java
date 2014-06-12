package org.sdnhub.dnsguard;

import java.util.List;
import java.util.Properties;

import org.sdnhub.dnsguard.renders.DnsRecordReply;
import org.sdnhub.dnsguard.renders.DnsUsage;
import org.sdnhub.dnsguard.renders.Violator;

public interface IDnsGuard {

	public String echo (String in);
	
	public List<String> lazyresolv (String appIp);
	
	public List<String> appsbyip (String sourceIp);
	
	public List<Violator>getViolators();
	
	public List<DnsUsage>getExternalDnsUsage(int top);
	
	public List<DnsRecordReply>getDatabaseDnsRecords(int limit, int offset);
	
	public String setLocalDnsServer (String local_dns);
	
	public String getLocalDnsServer ();
	
	//db related
	public Boolean isConnected();
	public void savePropsAndConnect(Properties props);
	
}
