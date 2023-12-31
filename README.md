# ConSpigot

Just a WebSocket creating bridge between minecraft server <br>

### How to use

Drop in into your plugins folder and configure stuff you need in plugins/Statuer/config.json <br>
If you disable password required in config, anyone can control your server and grief it <br> 

### Commands
`/statuer` - Reload configuration <br>

### Examples of usage

<h5>Send this JSON as string to your websocket to kick specified player</h5>
`{ "type": "player_kick", "player": "notch", "reason": "Noob!", "password": "your_pass_in_cfg"}`

Successful response: <br>
    `{ "error": false, "message": "Player notch has been kicked with reason: Noob!"}` 
    
Error response: <br>
    `{ "error": true, "message": "Player notch is not online!"}` 

<h5>To listen for events (like player joins/quits):</h5>
`{ "type": "start_listener", "password": "your_cool_pass_in_cfg"}` 

Player join: <br>
`{ "type": "event_playerJoin", "player_info": { "name": "notch", .... } }`

Player quit: <br>
`{ "type": "event_playerQuit", "player_info": {"name": "notch", "display_name": "notch" }}`

On chat message: <br>
`{ "type": "event_chat", "player": "notch", "message": "Hello!"}`

<br>
That is not all, I'm just lazy to add it all here