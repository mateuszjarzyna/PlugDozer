1. Build `contract` project and add the jar to your local repository  
`mvn clean install`
2. Create direcotory for storing jars  
`mkdir /tmp/jars`
3. Build pl-plugin 
```
cd pl-plugin
mvn clean package
``` 
4. Copy plugin  
`cp target/pl-plugin-1.0-SNAPSHOT.jar /tmp/jars`
5. Do the same with `de-plugin`
6. Build 'app' project
```
cp app 
mvn clean package
```
7. Execut app
```
$ java -jar target/app-1.0-SNAPSHOT-shaded.jar -Djars=/tmp/jars
Hallo Robert
Czesc Robert
```
