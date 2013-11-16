# Very hacked together make file for java

# define a custom class path to use
custclass = -classpath ../lib/ojdbc6.jar:../lib/servlet-api-2.3.jar:../lib/commons-fileupload-1.2.2.jar 
# define a custom source path to use
custsource = -sourcepath ./:util 

all: 
	make login
	make register
	make manage_Groups
	make uploadImage
	make manageUser
login:
	javac $(custsource) $(custclass) loginservlet.java
register:
	javac $(custsource) $(custclass) registerservlet.java
manage_Groups:
	javac $(custsource) $(custclass) manage_Groups.java
uploadImage:
	javac $(custsource) $(custclass) uploadImage.java
manageUser:
	javac $(custsource) $(custclass) manageUser.java
clean:
	rm -rf *.class
