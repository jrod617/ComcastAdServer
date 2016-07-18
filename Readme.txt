Built with Maven for an Apache Tomcat v8.0 Target.

Uploaded to the GitHub Repo below:
https://github.com/jrod617/ComcastAdServer

- Persistences maintained by a read/write from txt file
	- Extremely costly when dealing with large datasets (move to a MongoDB database), but good for a Proof of Concept
	- Support for multiple ad campaigns per Partner
	- fault tolerance in the form of unique error codes (could of provided Error Messages in JSON with more time)
	
Urls:
	<host>/ComcastAdService/ad -> Post New ads according to JSON object defined in Functional Requirements
	<host>/ComcastAdService/ad/list -> JSON return of all available ad campaigns
	<host>/ComcastAdService/ad/<partner_id> -> JSON Return of valid ad campaigns for a specific Partner Id
