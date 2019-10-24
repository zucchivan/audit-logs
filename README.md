# audit-logs

A simple Java micro service that listens to Kafka messages for storing audit logs into MongoDB and exposes them through REST APIs.

## Usage

This micro service is meant to listen to Kafka topics following the pattern "*-logs", store those messages into a NoSQL DB and allow them to be queried properly with REST APIs. 

When adding it to your micro service ecosystem, just edit DB and Kafka configurations to point it to the proper servers and make your other services to post their audit logs to Kafka topics following the pattern "[service_name]-logs". 

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
