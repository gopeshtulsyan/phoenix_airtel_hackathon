mvn clean install
rm -rf phoenix_airtel_hackathon-*
unzip target/phoenix_airtel_hackathon-0.0.1-FINAL.zip
cd phoenix_airtel_hackathon-0.0.1/bin/
./runserver.sh
cd ../..
