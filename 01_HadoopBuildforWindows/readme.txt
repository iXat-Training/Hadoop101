Download and Install Windows SDK - https://www.microsoft.com/en-in/download/details.aspx?id=8442
	(or) download the file GRMSDKX_EN_DVD.iso from the lab

Download and Install JDK - http://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase7-521261.html#jdk-7u80-oth-JPR
	
Download and Install Maven - https://maven.apache.org/download.cgi
	Set M2_HOME and Path variables for Maven
	Modify maven settings to use our lab proxy
		in M2_HOME\conf\settings.xml modify the proxy section as below -
			<proxies>
   			    <proxy>
			      <id>optional</id>
			      <active>true</active>
			      <protocol>http</protocol>
			      <username></username>
			      <password></password>
			      <host>ixatlabmaster</host>
			      <port>3128</port>
			      <nonProxyHosts>local.net|some.host.com</nonProxyHosts>
			    </proxy>
		  </proxies>

Download and Install Protobuf - https://protobuf.googlecode.com/files/protoc-2.5.0-win32.zip
	Set Path variable for protobuf

Download and Install Unix command-line tools for Windows, example cygwin/GnuWin32 - 
	set proxy for downloads as ixatlabmaster:3128
	Set path for the downloaded unix tool

Set proxy in Cygwin to ixatlabmaster:3128

Download and Install CMAKE - http://www.cmake.org/files/v3.0/cmake-3.0.2-win32-x86.exe

Add the “Platform” environment variable with the value of either “x64” or “Win32” for building on 64-bit or 32-bit system.(Case-sensitive)

Download hadoop source - for 2.7 http://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.7.1/hadoop-2.7.1-src.tar.gz

open windows7 sdk command prompt and set all required env vars, for example path on my system is as following



SET JAVA_HOME=E:\JDK1.7
set M2_HOME=C:\Maven
set Platform=x64
SET PATH=%PATH%;%JAVA_HOME%\bin;%M2_HOME%\bin;C:\protobuf;C:\cygwin64\bin

run maven compile for hadoop src on an exploded archive
mvn package -Pdist,native-win -DskipTests -Dtar  
