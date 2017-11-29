### Grokking API
##  Usage

    -Create user with username
     -Endpoint: http://<host>/register.json?=username
     -Method: POST
     -Params: username
     -Response: token for user

    -Submit answer
     -Endpoint: http://<host>/submit.json
     -Method: POST
     -Params:
        -Header:
            X-User username
            X-Token token
        -Data: array of integer
     -Response:
            201 - Success
            403 - Unauthorized


