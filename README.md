# ConSpigot

Create WebSocket on **0.0.0.0:8080** <br>

<details>
  <summary>JSON structure:</summary>
  
```
{
  cpu: integer,
  mem: {
    total: integer,
    free: double,
    usage: double
  },
  offlinePlayers: [{
    OP: bool,
    banned: bool,
    name: string,
    uuid: string,
    whitelisted: bool
  }],
  onlinePlayers: [{
    OP: bool,
    displayName: string,
    food: integer,
    health: integer,
    name: string,
    uuid: string
  }],
  server: {
    bukkitVersion: string,
    IP: string,
    name: string,
    port: integer,
    version: string
  },
  tps: {
    1m: double
  }
}
```
</details>
<br>

<details>
  <summary>Example of JSON:</summary>
  
```
{
  "server": {
    "name": "Paper",
    "version": "git-Paper-550 (MC: 1.19.4)",
    "bukkitVersion": "1.19.4-R0.1-SNAPSHOT",
    "ip": "172.10.10.252",
    "port": 25570
  },
  "tps": {
    "1m": 19.94
  },
  "cpu": 0.0,
  "mem": {
    "total": 7106.0,
    "free": 1208.5,
    "usage": 5897.5
  },
  "onlinePlayers": [{
    "name": "XAP3Y",
    "displayName": "XAP3Y",
    "uuid": "f1c3931e-93d3-4125-8fdc-9b1dc39bc4d6",
    "health": 20.0,
    "food": 20,
    "OP": true
  }],
  "offlinePlayers": [{
    "name": "XAP3Y",
    "uuid": "f1c3931e-93d3-4125-8fdc-9b1dc39bc4d6",
    "banned": false,
    "whitelisted": false,
    "OP": true
  }]
}
```
</details>
