mvn clean package
cd database
sudo docker build -t database .
cd ..
cd rest 
sudo docker build -t rest .
cd ..
cd web-app 
sudo docker build -t web-app .
cd
sudo docker run -d --rm --name db -p 1521:1521 -p 81:81 database
sudo docker run -d --rm --name rt -p 8080:8080 rest bash -c "/tomcat/bin/catalina.sh run"
sudo docker run -d --rm --name web -p 8081:8080 web-app bash -c "/tomcat/bin/catalina.sh run"
