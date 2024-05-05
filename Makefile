TOMCAT_HOME=/opt/goinfre/sommerha/apache-tomcat-9.0.88


start:
	mvn clean
	mvn package -DskipTests
	mv target/rusgramm-0.0.1-SNAPSHOT.war ${TOMCAT_HOME}/webapps/rusgramm.war
	sh ${TOMCAT_HOME}/bin/startup.sh
	sleep 5
	open http://localhost:8080/rusgramm

start-docker:
	docker-compose up -d
	mvn clean
	mvn package -DskipTests
	mv target/rusgramm-0.0.1-SNAPSHOT.war ${TOMCAT_HOME}/webapps/rusgramm.war
	sh ${TOMCAT_HOME}/bin/startup.sh
	sleep 5
	open http://localhost:8080/rusgramm




stop:
	sh ${TOMCAT_HOME}/bin/shutdown.sh