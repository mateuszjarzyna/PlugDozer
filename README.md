# PlugDozer
Modern way to create extensions in Java world

## About
PlugDozer is a simple and lightweight micro-framework that allows you to create plugins for your Java application.

Design several interfaces and let third parties create modules/plugins that adds extra features to your application.

Just add the @Plugin annotation to your class, build a .jar file, copy it to the plugins' directory (or in advanced use cases it is possible to load classes from different sources, such as database or network, but you have to add few extra line of code) and thats all - your application is ready to load extra functionality.

PlugDozer integrates well with Spring Boot (see Integration section)
