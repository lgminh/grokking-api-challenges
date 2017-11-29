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

	How to load primes into redis with python script.
	- Step 1 download 50 file of primes from this website https://primes.utm.edu/lists/small/millions/ (Do not change the name of any files)
	- Step 2: put the script import_primes.py into the same folder with other 50 files of primes.
	- Run the script. the script will read each file and put into the redies with the key named "primes". You can check the key "primes" when the script finishs.
	
	
