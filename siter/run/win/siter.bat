@echo off
SET JAVA_HOME=%JAVA_HOME%
SET NUTZ_HOME=D:\home\zozoh\workspace\galaxy\nutz@google\bin
SET SITER_HOME=D:\home\zozoh\workspace\galaxy\lab.siter\bin

SET CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\jre\rt.jar
SET CLASSPATH=%CLASSPATH%;%SITER_HOME%;%NUTZ_HOME%;
java org.nutzx.siter.Siter %1 %2
@echo on
