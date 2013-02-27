package com.joymeng.core.db.cache.couchbase;

import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.spy.memcached.CASValue;
import net.spy.memcached.ConnectionObserver;

//import com.couchbase.client.CouchbaseClient;
//import com.couchbase.client.CouchbaseConnectionFactory;
//import com.joymeng.core.utils.EncryptUtil;

public class CouchBaseUtil {
//	private static CouchbaseClient client;
//	// 服务器地址,采用默认端口和默认库，
////	public static String serverAddress = "10.80.1.97";
//
//	/**
//	 * Connect to the server, or servers given.
//	 * 
//	 * @param serverAddress
//	 *            the server addresses to connect with.
//	 * @throws IOException
//	 *             if there is a problem with connecting.
//	 * @throws URISyntaxException
//	 * @throws ConfigurationException
//	 */
//	public static void connect(String serverAddress) throws Exception {
//
//		URI base = new URI(String.format("http://%s:8091/pools", serverAddress));
//		List<URI> baseURIs = new ArrayList<URI>();
//		baseURIs.add(base);
//		CouchbaseConnectionFactory cf = new CouchbaseConnectionFactory(
//				baseURIs, "server1", "");
//
//		client = new CouchbaseClient((CouchbaseConnectionFactory) cf);
//		client.addObserver(new ConnectionObserver() {
//			public void connectionLost(SocketAddress sa) {
//				System.out.println("Connection lost to " + sa.toString());
//			}
//
//			public void connectionEstablished(SocketAddress sa,
//					int reconnectCount) {
//				System.out.println("Connection established with "
//						+ sa.toString());
//				System.out.println("Reconnected count: " + reconnectCount);
//			}
//		});
//
//	}
//
//	/**
//	 * 
//	 * @param userId
//	 * @param time
//	 *            过期时间 秒
//	 */
//	public static void put(String key, String value, int time) {
////		System.out.println("-----------put key=" + key);
//		client.set(key, time, value);
//	}
//	/**
//	 * 获得value
//	 * @param key
//	 * @return
//	 */
//	public static String get(String key) {              
//		CASValue<Object> casValue = client.gets(key);
//		if (casValue == null) {
////			System.out.println("getMessage is null,key=" + key);
//			return null;
//		} else {
//			String str = (String) casValue.getValue();
////			System.out.println("get key=" + key + " value=" + str);
//			return str;
//		}
////		client.getBulk(keys);
//	}
//	/**
//	 * 取得多个key的value
//	 * @param keys
//	 * @return
//	 */
//	public static Map<String, Object> get(List<String> keys){
//		Map<String, Object> map=client.getBulk(keys);
//		Iterator<String> it=map.keySet().iterator();
//		while(it.hasNext()){
//			String key=it.next();
////			System.out.println("key=="+key+" value="+map.get(key).toString());
//		}
//		return map;
//	}
//	public static void incr(String key,int offset){
//		client.incr(key, offset);
//	}
}
