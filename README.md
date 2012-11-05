
Build the app using Maven

mvn clean package


Then run it as follows, from within this directory, with an SVN repo of your choice. The output is in a directory named "output".

java -jar target/checkin-sparklines-1.0.one-jar.jar http://svn.mulle-kybernetik.com/OCMock/trunk

If username/password are required you can specify them on the command line after the repository URL.




https://springframework.svn.sourceforge.net/svnroot/springframework/repos/repo/org/springframework
http://svn.mulle-kybernetik.com/OCMock/trunk
http://svn.apache.org/repos/asf/maven/maven-3/trunk
http://svn.apache.org/repos/asf/subversion/trunk
