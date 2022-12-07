# Intro
This sampe is for auth & resource server.
This uses grant type as password. In which client has to send client credentails as a reqeust Basic Auth & user credentails as a request param.
This uses in-mem db for holding users/ client details.

# Sample request

1. open postman/ soapui
2. change method to POST
3. change URI to http://localhost:9092/oauth/token
4. Select Auth Type "Basic Auth" and enter client user & password - javainuse-client & javainuse-secret
5. Add reqeust param name & vlaue as follows 
username - javainuse-user
password - javainuse-pass
grant_type - password
scopes - read write

6. Now send request. You'll see response with acces token.
7. Use the access token to call http://localhost:9092/hello
