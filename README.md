# ThermalPrinterChat
curl http://localhost:3000/messages | jq -C '.[] | .text'
curl -H "Content-Type: application/json" --data-raw '{"text":"Hello World"}' -X POST http://localhost:3000/messages

