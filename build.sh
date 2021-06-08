#/bin/sh

cd ./frontend
if [ ! -d "./build" ]; then
	rm -rf ./build
fi
npm run build
if [ ! -d "../backend/src/main/resources/public" ]; then
	mkdir ../backend/src/main/resources/public
else
	rm -rf ../backend/src/main/resources/public/*
fi
mv build/* ../backend/src/main/resources/public/
cd ../backend
mvn package -DskipTests
mv ./target/iCommerce-0.0.1-SNAPSHOT.jar ../app.jar
