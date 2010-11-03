var ioc = {
	authConfig : { /*授权系统,针对OpenID的*/
		type : 'java.util.HashMap',
		args : [
		        {/*实际使用时,localhost改为相应的域名*/
		        	returnURL : 'http://localhost:8080/NutOpenID/auth/jopenid/returnPoint.nut',
		        	realm : 'http://localhost:8080/',
		        	enpoint : 'Google'/*可以是Google或者雅虎*/
		        }
		]
	}
}