# DynamodbToCSV4j
![Crates.io](https://img.shields.io/crates/l/rustc-serialize.svg)


This application will export the content of a DynamoDB table into a CSV (Comma delimited value) output. All you need to do is create a config.json file where you configure the table to export and some other optional parameters.  The config might look like:
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
| tableName | The name of the dynamodb table

## Optional Configuration settings

| parameter | Description
| ------------- |-------------| 
| accessKeyId | Your AWS access key (if this is specified then secretAccessKey is required)
| secretAccessKey | Your AWS secret access key
| region | The name of the AWS Region where the DynamoDB database is hosted
| outputfile | The outputfile.  Default will be tablename.csv
| delimiter | The column delimiter, default will be ,
| quotechar | The csv quote character
| headers | If set to anything but "true", the column headers will not be in the output
| nullstring | String to put in columns that have no value
| projectionExpression | The [projection expression](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ProjectionExpressions.html) that defines which fields to return
| filterExpression | The [filter expression](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/QueryAndScan.html#FilteringResults) for the scan operation to get the data from dynamodb 
| expressionAttributeValues | The [values](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ExpressionPlaceholders.html#ExpressionAttributeValues) for the filterExpression
| expressionAttributeNames | The [names](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ExpressionPlaceholders.html#ExpressionAttributeNames) for the filterExpression

## Examples
The examples will use a very simple table, with mixed records that contain maps and lists.
![Screenshot of testtable](https://s3.amazonaws.com/misc.quodlibet.be/dynamodb2csv4j/testtable.png)
### Example 1

This assumes that your AWS credentials and region will be found in your environment.  See [setting up credentials](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html).
If any of the field names are reserved words then they can be mapped using expressionAttributeNames (see next example).

#### Configuration
```javascript
{
  "tableName":"testtable",
  "headers":"true",
  "projectionExpression":"lastName, firstName",
}
```
#### Output
```
lastName,firstName
Steinbeck,John
```
### Example 2

You can also specify credentials explicitly if you wish, as well as setting a filter expression so that only a subset of records will be returned.

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
### Example 3
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
The project can be used as a Maven dependency by using jitpack.io.
* add the repository : 
```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```
* add the dependency
```xml
<dependencies>
	<dependency>
	    <groupId>com.github.dhorions</groupId>
	    <artifactId>DynamodbToCSV4j</artifactId>
	    <version>v0.1</version>
	</dependency>
</dependencies>
```

## Usage in your java code
```java
JSONObject config = new JSONObject();
config.put("accessKeyId","REPLACE");
config.put("secretAccessKey","REPLACE");
config.put("region","eu-west-1");
config.put("tableName","testtable");
d2csv d = new d2csv(config);
``` 

## Alternative options to export data from dynamodb to csv
This [article on medium](https://medium.com/@quodlibet_be/an-overview-of-tools-to-export-from-dynamoddb-to-csv-d2707ad992ac#.52ymlbfv1) gives an overview of alternative options to export data to comma separated files from dynamodb.
