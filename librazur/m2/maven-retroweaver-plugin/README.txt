This is the README file for Retroweaver Maven 2 plugin.
Copyright (c) 2005 Librazur.
http://librazur.info

Please note that this application is published under the terms of the
General Public License (GPL). See LICENSE.txt for more information.

This plugin allows you to use the library Retroweaver
(http://retroweaver.sf.net) to make your Java 5 enabled sources be useable
under a JVM 1.2, 1.3 or 1.4. Please visit the Retroweaver website for more
information.

Don't forget to add a dependency in your project on the library retroweaver-rt,
as you will need it at runtime to resolve some classes which are not available
on a JVM lower than 1.5. So, add the following to your pom.xml:

  <dependencies>
    <dependency>
      <groupId>net.sourceforge.retroweaver</groupId>
      <artifactId>retroweaver-rt</artifactId>
      <version>1.1</version>
      <scope>runtime</scope>
    </dependency>
  </dependencies>

The library retroweaver-rt 1.1 is only available in the Librazur repository
right now. As long as there is no published version in the Maven central
repository at Ibiblio, you will have to add theses lines to your pom.xml:

  <repositories>
    <repository>
      <id>librazur</id>
      <name>Librazur Maven 2 repository</name>
      <url>http://maven.librazur.info/maven2</url>
    </repository>
  </repositories>

In order to use this plugin, you have to add the following lines into your
pom.xml:

  <pluginRepositories>
    <pluginRepository>
      <id>librazur-plugin-repository</id>
      <name>Librazur Maven 2 plugins repository</name>
      <url>http://maven.librazur.info/maven2</url>
    </pluginRepository>
  </pluginRepositories>

Then, add theses lines to the file ${user.home}/.m2/settings.xml:

  <pluginGroups>
    <pluginGroup>org.librazur.maven.plugins</pluginGroup>
  </pluginGroups>

You can now use the plugin by invoking: mvn retroweaver:retroweaver

To customize Retroweaver plugin, use the following parameters in your pom.xml
(see http://maven.apache.org/maven2/guides/mini/guide-configuring-plugins.html
for more information):
 - version: targeted JVM version                                [default is 1.4]
 - lazy: whether to launch Retroweaver in lazy mode or not     [default is true]

For example:

  <build>
    <plugins>
      <plugin>
        <groupId>org.librazur.maven.plugins</groupId>
        <artifactId>maven-retroweaver-plugin</artifactId>
        <configuration>
          <version>1.3</version>
          <lazy>false</lazy>
        </configuration>
      </plugin>
    </plugins>
  </build>

If you have trouble using this plugin, or if you want to participate in
Librazur development, contact me at alex@librazur.info.

Thanks for your interest in Retroweaver plugin.
