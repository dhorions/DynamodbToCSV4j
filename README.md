# DynamodbToCSV4j
This application will export the content of a DynamoDB table into a CSV (Comma delimited value) output. All you need to do is create a config.json file in that same directory where you configure your accessKeyId, secretAccessKey and region as such:
```javascript
{
  "accessKeyId": "REPLACE",
  "secretAccessKey": "REPLACE",
  "region": "eu-west-1",
  "tableName":"testtable"
}
``` 
#  Usage
##  Command Line
* java DynamodbToCSV4j

This will use the config.json file on your path.
* java DynamodbToCSV4j myConfig.json

This will use the myConfig.json file.

# Configuration
## Mandatory Configuration settings

| parameter | Description
| ------------- |-------------| 
| accessKeyId | Your AWS access key
| secretAccessKey | Your AWS secret access key
| region | The AWS Region name
| tableName | The name of the dynamodb table

## Optional Configuration settings

| parameter | Description
| ------------- |-------------| 
| outputfile | The outputfile.  Default will be tablename.csv
| delimiter | The column delimiter, default will be ,
| quotechar | The csv quote character
| headers | If set to anything but "true", the column headers will not be in the output
| nullstring | String to put in columns that have no value
| filterExpression | The [filter expression](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/QueryAndScan.html#FilteringResults) for the scan operation to get the data from dynamodb 
| expressionAttributeValues | The [values](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ExpressionPlaceholders.html#ExpressionAttributeValues) for the filterExpression
| expressionAttributeNames | The [names](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ExpressionPlaceholders.html#ExpressionAttributeNames) for the filterExpression

## Examples
The examples will use a very simple table, with mixed records that contain maps and lists.
![Screenshot of testtable](https://s3.amazonaws.com/misc.quodlibet.be/dynamodb2csv4j/testtable.png)
### Example 1 
#### Configuration
```javascript
{
  "accessKeyId": "REPLACE",
  "secretAccessKey": "REPLACE",
  "region": "eu-west-1",
  "tableName":"testtable",
  "outputfile":"example_1.csv",
  "delimiter":";",
  "quotechar":"\"",
  "headers":"true",
  "nullstring":"N/A",
  "filterExpression":"#lastName = :lastName",
  "expressionAttributeValues" : 
		[
			{"name":":lastName", "value":"Steinbeck", "type":"S"}
		],
	"expressionAttributeNames" : 
		[
			{"name":"#lastName", "value":"lastName"}
		]
}
```
#### Output
```
lastName;firstName;books.0;books.1;books.2;secondary_key;primary_key
Steinbeck;John;Of Mice and Man;Travels With Charlie;Tortilla Flat;1;1
```
### Example 2 
#### Configuration
```javascript
{
  "accessKeyId": "REPLACE",
  "secretAccessKey": "REPLACE",
  "region": "eu-west-1",
  "tableName":"testtable",
  "outputfile":"example_2.csv",
  "delimiter":";",
  "quotechar":"\"",
  "headers":"true",
  "nullstring":"N/A"
}
```
#### Output
```
lastName;firstName;books.0;books.1;books.2;books.3;books.4;secondary_key;Genre.subtype;Genre.type;primary_key
Hamilton;Peter;Pandora's Star;Judas Unchained;The Dreaming Void;The Temportal Void;The Evolutionary Void;2;Space Opera;Science Fiction;2
Steinbeck;John;Of Mice and Man;Travels With Charlie;Tortilla Flat;N/A;N/A;1;N/A;N/A;1

```

## Maven
TODO : include maven xml
## Usage in your java code
```java
JSONObject config = new JSONObject();
config.put("accessKeyId","REPLACE");
config.put("secretAccessKey","REPLACE");
config.put("region","eu-west-1");
config.put("tableName","testtable");
d2csv d = new d2csv(config);
``` 
