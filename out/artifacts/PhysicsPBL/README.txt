In order to run this application successfully it is required to:

1) Install jre-8 by running "jre-8u251-windows-i586-iftw.exe"
2) Install jdk-11.0.6 by running "jdk-11.0.6_windows-x64_bin.exe". 
3) Create (or add new line) and set values for the "System environment variables":
	1. "JAVA_HOME" with value "..\Java\jdk-11.0.6" (i.e. specify the path to the jdk folder).
	2. "PATH" with value "..\Java\jdk-11.0.6\bin" (i.e. specify the path to the jdk BIN folder).
	3. "PATH" with value "C:\Program Files (x86)\Common Files\Oracle\Java\javapath" (i.e. specify the path to the java executables folder).
4) In "PhysicsPBL" right click on "settings.bat" and choose "Edit",
   then change the path starting from "D:\" to your path
   where "..\javafx-sdk-11.0.2\lib" is located.
5) Run the "EM Oscillations.exe" executable.