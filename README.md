# ConSpigot

Create WebSocket on **0.0.0.0:3001** <br>
Send JSON to opened connection.
JSON structure:
```
{
  cpu: integer,
  mem: {
    total: integer,
    free: double,
    usage: double
  },
  offlinePlayers: [{OP: bool, banned: bool, name: string, uuid: string, whitelisted: bool}],
  onlinePlayers: [{OP: bool, displayName: string, food: integer, health: integer, name: string, uuid: string}],
  server: {
    bukkitVersion: string,
    IP: string,
    name: string,
    port: integer,
    version: string
  },
  tps: {
    1m: double,
    5m: double,
    15m : double
  }
}
```
<br>
Example:

```
{
"server":
  {
    "name": "Paper",
    "version": "git-Paper-550 (MC: 1.19.4)",
    "bukkitVersion": "1.19.4-R0.1-SNAPSHOT",
    "ip": "",
    "port": 25565
  },
"tps":
  {
    "1m": 20.01932518912104,
    "5m": 20.003862052428467,
    "15m": 20.00128718510361
  },
"cpu": 66.66666666666666,
"mem":
  {
    "total": 829,
    "free": 240,
    "usage": 0
  },
"onlinePlayers":
  [{
    "name": "XAP3Y",
    "displayName": "XAP3Y",
    "uuid": "f1c3931e-93d3-4125-8fdc-9b1dc39bc4d6",
    "health": 20.0,
    "food": 20,
    "OP": true
  }],
"offlinePlayers":
  [{
    "name": "XAP3Y",
    "uuid": "f1c3931e-93d3-4125-8fdc-9b1dc39bc4d6",
    "banned": false,
    "whitelisted": false,
    "OP": true
  }]
}
```
