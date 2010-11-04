package net.sunfarms.z.auth.jopenid;

import org.nutz.dao.entity.annotation.*;

@Table("t_nonce_info")
public class NonceInfo {

	@Id
	private long oid;
	
	@Name
	private String nonceStr;
	
	private long expireTime;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
	
}
