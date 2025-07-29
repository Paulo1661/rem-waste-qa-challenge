#Install dependencies, start backend + frontend
(cd backend && npm install && npm run dev) &
(cd frontend && npm install && npm run dev) &

#Run the tests
cd test && mvn clean verify

#Generate tests report
mvn serenity:aggregate