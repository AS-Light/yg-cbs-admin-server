docker rm -f yg-cbs-admin-server
docker rmi ejio/yg-cbs-admin-server:3.0.0-RELEASE

cd /root/yg-cbs-admin-server
mvn clean install -Dmaven.test.skip=true
mvn package docker:build -Dmaven.test.skip=true

docker run -dit --name yg-cbs-admin-server -d -p 10010:10010 -v /etc/localtime:/etc/localtime:ro --restart=always ejio/yg-cbs-admin-server:3.0.0-RELEASE
