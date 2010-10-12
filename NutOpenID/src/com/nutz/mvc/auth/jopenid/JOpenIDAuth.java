package com.nutz.mvc.auth.jopenid;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.expressme.openid.Association;
import org.expressme.openid.Authentication;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdException;
import org.expressme.openid.OpenIdManager;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.nutz.mvc.auth.AuthConfig;
import com.nutz.mvc.auth.AuthUserInfo;

@IocBean(create="init")
@InjectName
public class JOpenIDAuth {

    static final long ONE_HOUR = 3600000L;
    static final long TWO_HOUR = ONE_HOUR * 2L;
    static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";
	
	private Dao dao;
	
	private String enpoint;
	
	private OpenIdManager manager = new OpenIdManager();
	
	@At("/auth/jopenid/login")
	@Ok(">>:${obj}")
	public String login(HttpServletRequest request) {
		Endpoint endpoint = manager.lookupEndpoint(enpoint);
        Association association = manager.lookupAssociation(endpoint);
        request.getSession().setAttribute(ATTR_MAC, association.getRawMacKey());
        request.getSession().setAttribute(ATTR_ALIAS, endpoint.getAlias());
        return manager.getAuthenticationUrl(endpoint, association);
	}
	
	public void logout(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	public boolean isAuthed() {
		return true;
	}
	
	@Ok("jsp:auth.ok")
	@At("/auth/jopenid/returnPoint")
	public Object returnPoint(HttpServletRequest request) {
		Logs.getLog(getClass()).info(request.getQueryString());
		Logs.getLog(getClass()).info(request.getParameter("openid.ext1.type.language"));
		checkNonce(request.getParameter("openid.response_nonce"));
        // get authentication:
        byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
        String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
        Authentication authentication = manager.getAuthentication(request, mac_key, alias);
        String identity = authentication.getIdentity();
        AuthUserInfo authUserInfo = dao.fetch(AuthUserInfo.class, identity);
        if (authUserInfo == null) {
        	authUserInfo = new AuthUserInfo();
        	authUserInfo.setIdentity(identity);
        	authUserInfo.setEmail(authentication.getEmail());
        	dao.insert(authUserInfo);
        	authUserInfo = dao.fetch(AuthUserInfo.class, identity);
        }
        request.getSession().setAttribute("AuthUserInfo_OBJ", authUserInfo);
        return authUserInfo;
	}
	
    void checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce==null || nonce.length()<20)
            throw new OpenIdException("Verify failed.");
        long nonceTime = getNonceTime(nonce);
        long diff = System.currentTimeMillis() - nonceTime;
        if (diff < 0)
            diff = (-diff);
        if (diff > ONE_HOUR)
            throw new OpenIdException("Bad nonce time.");
        if (isNonceExist(nonce))
            throw new OpenIdException("Verify nonce failed.");
        storeNonce(nonce, nonceTime + TWO_HOUR);
    }

    boolean isNonceExist(String nonce) {
    	Cnd cnd = Cnd.where("nonceStr", "like", nonce).and("expireTime", ">", System.currentTimeMillis());
    	return null != dao.fetch(NonceInfo.class, cnd);
    }

    void storeNonce(String nonce, long expires) {
    	NonceInfo nonceInfo = new NonceInfo();
    	nonceInfo.setExpireTime(System.currentTimeMillis()+expires);
    	nonceInfo.setNonceStr(nonce);
    	dao.insert(nonceInfo);
    }
	
    long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch(ParseException e) {
            throw new OpenIdException("Bad nonce time.");
        }
    }
    
	@Inject
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	@Inject
	public void setAuthConfig(AuthConfig authConfig){
		manager.setReturnTo(authConfig.get("returnURL"));
		manager.setRealm(authConfig.get("realm"));
		enpoint = authConfig.get("enpoint");
		if (enpoint == null)
			enpoint = "Google";
	}
	
	public void init() {
		if (!dao.exists(AuthUserInfo.class)) {
			dao.run(new ConnCallback() {
				
				@Override
				public void invoke(Connection connection) throws Exception {
					String sql = "CREATE Table t_auth_user_info(" +
							"uid BIGINT IDENTITY PRIMARY KEY, " +
							"identity CHAR(255) NOT NULL ," +
							"email CHAR(255))";
					connection.prepareCall(sql).execute();
					connection.commit();
				}
			});
		}
		if (!dao.exists(NonceInfo.class)) {
			dao.run(new ConnCallback() {
				
				@Override
				public void invoke(Connection connection) throws Exception {
					String sql = "CREATE Table t_nonce_info(" +
							"oid BIGINT IDENTITY PRIMARY KEY, " +
							"nonceStr CHAR(255) NOT NULL ," +
							"expireTime BIGINT)";
					connection.prepareCall(sql).execute();
					connection.commit();
				}
			});
		}
	}
}
