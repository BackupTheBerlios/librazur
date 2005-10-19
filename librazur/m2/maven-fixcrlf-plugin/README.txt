This is the README file for Maven 2 plugin fixcrlf.
Copyright (c) 2005 Librazur.
http://librazur.eu.org

Please note that this application is published under the terms of the
General Public License (GPL). See LICENSE.txt for more information.

This plugin allows you to clean text files:
 - use the same end-of-line characters,
 - strip remaining spaces at end of line,
 - encode your file with a specific charset.

Fixcrlf is a plugin for Maven 2 which provides the same services than the Ant
task fixcrlf. There may be some other alternatives to this plugin... As this is
my first plugin for Maven 2, fixcrlf is for me like a simple tutorial on how
to develop plugin for Maven 2. Feel free to reuse this work to write tutorials,
as long as you respect the GPL!

In order to use this plugin, you have to add the following lines into your
pom.xml:

  <pluginRepositories>
    <pluginRepository>
      <id>librazur-plugin-repository</id>
      <name>Librazur Maven 2 plugins repository</name>
      <url>http://www.librazur.online.fr/maven2</url>
    </pluginRepository>
  </pluginRepositories>

Then, add theses lines to the file ${user.home}/.m2/settings.xml:

  <pluginGroups>
    <pluginGroup>org.librazur.maven.plugins</pluginGroup>
  </pluginGroups>

You can now use the plugin by invoking: mvn fixcrlf:fixcrlf

To customize fixcrlf, use the following parameters in your pom.xml
(see http://maven.apache.org/maven2/guides/mini/guide-configuring-plugins.html
for more information):
 - encoding: new file encoding                           [default is ISO-8859-1]
 - eol: specifies how end-of-lines (EOL) characters are to be handled; possible
   values are: mac, unix, dos                                  [default is unix]
 - stripEndSpaces: remove spaces at the end of line            [default is true]
 - includes: file masks to include     [default is **/*.java, **/*.css, **/*.js,
                                            **/*.xml, **/*.properties, **/*.txt]
 - excludes: file masks to exclude     [default is **/*.jpg, **/*.gif, **/*.png,
                                                             **/*.jar, **/*.zip]

For example:

  <build>
    <plugins>
      <plugin>
        <groupId>org.librazur.maven.plugins</groupId>
        <artifactId>maven-fixcrlf-plugin</artifactId>
        <configuration>
          <encoding>UTF-8</encoding>
          <eol>dos</eol>
        </configuration>
      </plugin>
    </plugins>
  </build>

This guide helped me a lot while I was developing this plugin:
http://maven.apache.org/maven2/guides/plugin/guide-java-plugin-development.html.

If you have trouble using this plugin, or if you want to participate in
Librazur development, contact me at librazur@gmail.com.

Thanks for your interest in fixcrlf.
