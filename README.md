# запити для підняття контейнерів
##  MongoDB
    docker run --name mongodb -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=secret mongo

##  Redis
    docker run --name redis_stat -p 6379:6379 -d redis:latest