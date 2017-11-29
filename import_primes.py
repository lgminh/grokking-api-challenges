import redis

def import_primes():
    r = redis.StrictRedis(host='localhost')
    for i in xrange(1, 51):
        with open('primes{}.txt'.format(i)) as file:
            for line in file.readlines():
                if any(c.isalpha() for c in line) or line == '\r\r\n' :
                    continue
                s = line.strip('\r\r\n').split("    ")
                t = []
                for j in s:
                    if j != '':
                        t.append(j)
                r.sadd("primes", t)
        file.close()
    return 1
if __name__ == '__main__':
    import_primes()
