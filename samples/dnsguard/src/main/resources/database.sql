CREATE TABLE IF NOT EXISTS bulkreply (
		  idbulkreply int(11) NOT NULL AUTO_INCREMENT,
		  request varchar(255) DEFAULT NULL,
		  type int(11) DEFAULT NULL,
		  ttl int(11) DEFAULT NULL,
		  len int(11) DEFAULT NULL,
		  data varchar(255) DEFAULT NULL,
		  ip_src varchar(16) DEFAULT NULL,
		  ip_dst varchar(16) DEFAULT NULL,
		  udp_src int(11) DEFAULT NULL,
		  udp_dst int(11) DEFAULT NULL,
		  tstamp timestamp NULL DEFAULT NULL,
		  PRIMARY KEY (idbulkreply)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS config (
  idconfig int(11) NOT NULL AUTO_INCREMENT,
  configname varchar(16) DEFAULT NULL,
  configval varchar(16) DEFAULT NULL,
  PRIMARY KEY (idconfig)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;


INSERT INTO config (configname, configval)
SELECT 'localdns', '192.168.0.1'
  FROM dual
  WHERE NOT EXISTS (SELECT 1 FROM config WHERE configname = 'localdns');
