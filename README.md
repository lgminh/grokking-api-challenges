## Grokking API
##  Usage
### Create user with username

- Endpoint: http://domain.com/register.json?username={username}
- Method: POST
- Params: username
- Response: token for user

- Example:
```
curl \
	-X POST \
	http://domain.com/register.json?username=abcxyz@gmail.com
	
gjkfsgkfgfd
```

### Submit answer
- Endpoint: http://domain.com/submit.json
- Method: POST
- Header:
	- X-User username
	- X-Token token
	- Content-Type: application/json
- Data: array of integer
- Response:
	- 201 - Success
	- 403 - Unauthorized
	
- Example:
```
curl \
	-d "[1,2,3,4,5]" \
	-H "Content-Type: application/json" \
	-H "X-User: abcxyz@gmail.com" \
	-H "X-Token: gjkfsgkfgfd" \
	-X POST \
	http://localhost:8080/submit.json
```

### How to load primes into redis with python script.
- Step 1 download 50 file of primes from this website https://primes.utm.edu/lists/small/millions/ (Do not change the name of any files)
- Step 2: put the script import_primes.py into the same folder with other 50 files of primes.
- Step 3: Run the script. the script will read each file and put into the redies with the key named "primes". You can check the key "primes" when the script finishs.
	
	
