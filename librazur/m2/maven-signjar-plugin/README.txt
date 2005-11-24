This is the README file for SignJar Maven 2 plugin.
Copyright (c) 2005 Librazur.
http://librazur.info

Please note that this application is published under the terms of the
General Public License (GPL). See LICENSE.txt for more information.

This plugin allows you to signs JAR files in your Maven 2 project. You will be
able to sign the main JAR artifact produced by your project, along with all the
project dependencies.

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

You can now use the plugin by invoking: mvn signjar:signjar

To customize SignJar plugin, use the following parameters in your pom.xml
(see http://maven.apache.org/maven2/guides/mini/guide-configuring-plugins.html
for more information):
 - alias: keystore alias to use (required)
 - storePass: keystore pass (required)
 - keystore: keystore location (File or URL)
 - storeType: keystore type
 - keyPass: entry password in keystore
 - sigFile: name of a .SF/.DSA file to use
 - verbose: verbose output when signing
 - includeSF: include the .SF file inside the signature block
 - sectionsOnly: don't compute hash of entire manifest
 - lazy: flag to control whether the presence of a signature file means a JAR is
         signed
 - signDependencies: whether to sign dependencies or not

For example:

  <build>
    <plugins>
      <plugin>
        <groupId>org.librazur.maven.plugins</groupId>
        <artifactId>maven-signjar-plugin</artifactId>
        <configuration>
          <alias>aliastest</alias>
          <storePass>aliastest</storePass>
        </configuration>
      </plugin>
    </plugins>
  </build>

If you have trouble using this plugin, or if you want to participate in
Librazur development, contact me at alex@librazur.info.

Thanks for your interest in SignJar plugin.
