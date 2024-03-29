
package com.inga.server.sdk.session;

import com.inga.constant.CIMConstant;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Channel包装类,集群时 将此对象存入表中
 */

public class CIMSession  implements Serializable{

	/**
	 * 
	 */
	private transient static final long serialVersionUID = 1L;
	public  transient static String ID = "ID";
	public  transient static String HOST = "HOST";
    public  transient static final int STATUS_ENABLED = 0;
    public  transient static final int STATUS_DISABLED = 1;
    public  transient static final int APNS_ON=1;
    public  transient static final int APNS_OFF=0;
    
    public  transient static String CHANNEL_IOS = "ios";
    public  transient static String CHANNEL_ANDROID = "android";
    public  transient static String CHANNEL_WINDOWS = "windows";
    public  transient static String CHANNEL_WP = "wp";
    
	private transient Channel session;

	private ChannelId channelId;
	private String gid;//session全局ID
	private String nid;//session在本台服务器上的ID
	private String deviceId;//客户端ID  (设备号码+应用包名),ios为devicetoken
	private String host;//session绑定的服务器IP
	private String port;//im服务器的端口号
	private String account;//session绑定的账号
	private String channel;//终端设备类型
	private String deviceModel;//终端设备型号
	private String clientVersion;//终端应用版本
	private String systemVersion;//终端系统版本
	private String packageName;//终端应用包名
	private Long bindTime;//登录时间
	private Long heartbeat;//心跳时间
	private Double longitude;//经度
	private Double latitude;//维度
	private String location;//位置
	private int apnsAble;//apns推送状态
	private int status;// 状态
	public CIMSession(Channel session) {
		this.session = session;
		this.nid = session.id().asShortText();
		this.channelId = session.id();

		System.out.println(" ==  ip host : " + session.remoteAddress().toString() + "  ==  ");

		setAttribute("host", host);
	}
 
	public CIMSession(){
		
	}
	
	
 

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
		
		setAttribute(CIMConstant.SESSION_KEY, account);
	}


	public ChannelId getChannelId() {
		return channelId;
	}

	public void setChannelId(ChannelId channelId) {
		this.channelId = channelId;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		setAttribute("longitude", longitude);
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		setAttribute("latitude", latitude);
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		setAttribute("location", location);
		this.location = location;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		
		this.gid = gid;
		
		setAttribute("gid", gid);
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getDeviceId() {
		return deviceId;
	}


	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
		
		setAttribute("channel", channel);
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
		
		setAttribute("deviceModel", deviceModel);
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		
		setAttribute("deviceId", deviceId);
	}


   

	public String getHost() {
		return host;
	}



	public Long getBindTime() {
		return bindTime;
	}

	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
	    setAttribute("bindTime", bindTime);
	}


	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
		setAttribute("clientVersion", clientVersion);
	}

	

	
	
	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
		setAttribute("systemVersion", systemVersion);
	}

	public Long getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(Long heartbeat) {
		this.heartbeat = heartbeat;
		setAttribute(CIMConstant.HEARTBEAT_KEY, heartbeat);
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	//	publicacc void setHost(String host) {
//		this.host = host;
//
//		setAttribute("host", host);
//	}

	public void setChannel(Channel session) {
		this.session = session;
	}


	public int getApnsAble() {
		return apnsAble;
	}

	public void setApnsAble(int apnsAble) {
		this.apnsAble = apnsAble;
		setAttribute("apnsAble", apnsAble);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		setAttribute("status", status);
	}


	public void setAttribute(String key, Object value) {
		if(session!=null)
		session.attr(AttributeKey.valueOf(key)).set(value);
	}


	public boolean containsAttribute(String key) {
		if(session!=null)
		return session.hasAttr(AttributeKey.valueOf(key));
		return false;
	}
	
	public Object getAttribute(String key) {
		if(session!=null)
		return session.attr(AttributeKey.valueOf(key)).get();
		return null;
	}

	public void removeAttribute(String key) {
		if(session!=null)
		session.attr(AttributeKey.valueOf(key)).set(null);;
	}

	public SocketAddress getRemoteAddress() {
		if(session!=null)
		return session.remoteAddress();
		return null;
	}

	public  boolean write(Object msg) {
		if(session!=null)
		{
			return session.writeAndFlush(msg).awaitUninterruptibly(5000);
		}
		
		return false;
	}

	public boolean isConnected() {
		if(session != null)
		{
			return session.isActive();
		}

		if(!isLocalhost())
		{
			return status == STATUS_ENABLED;
		}
		
		return false;
	}

	public boolean  isLocalhost()
	{
		
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip.equals(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
		 
	}
	
 
	public void closeNow() {
		if(session!=null)
		session.close();
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
		setAttribute("packageName", apnsAble);
	}

	public String getPackageName() {
		return packageName;
	}
	
	
	public int hashCode(){
		
		return (deviceId + nid + host).hashCode();
	}
	
	public boolean equals(Object o) {
        
		if(o instanceof CIMSession){
			return hashCode() == o.hashCode();
		}
		return false;
	}

    public boolean fromOtherDevice(Object o) {
        
		if (o instanceof CIMSession) {
			
			CIMSession t = (CIMSession) o;
			if(t.deviceId!=null && deviceId!=null)
			{
				return !t.deviceId.equals(deviceId);
			} 
		}  
		return false;
	}

    public boolean fromCurrentDevice(Object o) {
        
		return !fromOtherDevice(o);
	}
 
	
	public String  toString()
	{
		StringBuffer buffer = new   StringBuffer();
		buffer.append("{");
		
		buffer.append("\"").append("gid").append("\":").append("\"").append(gid).append("\"").append(",");
		buffer.append("\"").append("nid").append("\":").append(nid).append(",");
		buffer.append("\"").append("deviceId").append("\":").append("\"").append(deviceId).append("\"").append(",");
		buffer.append("\"").append("host").append("\":").append("\"").append(host).append("\"").append(",");
		buffer.append("\"").append("account").append("\":").append("\"").append(account).append("\"").append(",");
		buffer.append("\"").append("channel").append("\":").append("\"").append(channel).append("\"").append(",");
		buffer.append("\"").append("deviceModel").append("\":").append("\"").append(deviceModel).append("\"").append(",");
		buffer.append("\"").append("status").append("\":").append(status).append(",");
		buffer.append("\"").append("apnsAble").append("\":").append(apnsAble).append(",");
		buffer.append("\"").append("bindTime").append("\":").append(bindTime).append(",");
		buffer.append("\"").append("heartbeat").append("\":").append(heartbeat);
		buffer.append("}");
		return buffer.toString();
		
	}

	public static void main(String[] args) {
		List<String> ipList = new ArrayList<String>();
		InetAddress ip = null;
		try {
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				// 遍历所有ip
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = (InetAddress) ips.nextElement();
					if (null == ip || "".equals(ip)) {
						continue;
					}
					String sIP = ip.getHostAddress();
					if(sIP == null || sIP.indexOf(":") > -1) {
						continue;
					}
					ipList.add(sIP);
					System.out.println(sIP);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(String str : ipList){
			System.out.println(str);
		}
	}



}
