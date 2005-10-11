This is the README file for Getting Things Done.
Copyright (c) 2005 Librazur.
http://librazur.eu.org

Please note that this application is published under the terms of the
General Public License (GPL). See LICENSE.txt for more information.

This application is a multi-user task manager, developed with Java 5, thanks to
the use of cutting edge technologies such as EJB3, Hibernate3, Spring, Acegi Security,
Sitemesh and Maven2. It serves as a reference sample of the new EJB
specifications version 3, which should be out in 2006. It also leverages the
Spring framework and the benefits of AOP and IoC. The application is based upon
the MVC library Struts.

GTD is available in two languages: English (default), and French. Please forgive
me there is some errors in the english version, as the french language is my
mother tongue ;) Feel free to contribute if you want to add your language, or
correct my errors!

You need a servlet 2.4 container to use GTD. We advise you to use Apache Tomcat
5.5, as it was the container used for development. Other containers may be used,
because this application complies with the J2EE 1.4 specifications. Use the
documentation for your servlet container in order to install GTD. In case of
Tomcat, you only need to copy "gtd.war" in the "webapps" directory, and start
Tomcat.

GTD comes with an embedded memory database. All data is cleared when you stop
the application. If you want to persist your data, you have to create a file
${user.home}/.librazur.gtd.properties (where "user.home" stands for the home
directory of the user who starts the servlet container), and set the following
properties:

jdbc.driver = <your JDBC driver: org.hsqldb.jdbcDriver, org.postgresql.Driver>
jdbc.url = <the JDBC url: jdbc:hsqldb:file:gtd, jdbc:postgresql://localhost/gtd>
jdbc.username = <database username>
jdbc.password = <database password> 
jdbc.hibernate.dialect = <Hibernate HSQL dialect:
  see http://www.hibernate.org/hib_docs/v3/reference/en/html/session-configuration.html#configuration-optional-dialects>

You can use any database as long as it is supported by Hibernate and you have
a JDBC driver. Don't forget to copy the JDBC driver into the servlet container
classpath!

The default GTD user for administration is "admin", with the password "admin".

We hope you'll find this work useful for you.

Thanks for your interest in Librazur GTD.
