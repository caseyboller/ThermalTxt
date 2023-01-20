const http = require('http');

// An array to store messages
const messages = [];

// Create a server and handle incoming requests
const server = http.createServer((req, res) => {
    if (req.url === '/messages' && req.method === 'GET') {
    // Handle the API endpoint for getting messages
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(messages));
  } else if (req.url === '/messages' && req.method === 'POST') {
    let body = '';
    // Assemble the message payload
    req.on('data', (chunk) => {
        body += chunk.toString();
    });
    req.on('end', () => {
        try{
            const message = JSON.parse(body);
            if(message.text){
                messages.push(message);
                res.statusCode = 201;
                res.end('Message added successfully');
            }else{
                res.statusCode = 400;
                res.end('Invalid message format');
            }
        }catch(e){
            res.statusCode = 400;
            res.end('Invalid message format');
        }
    });
  } else {
    // Handle other requests
    res.statusCode = 404;
    res.end('Not found');
  }
});

// Start the server
server.listen(3000, () => {
  console.log('Server listening on http://localhost:3000');
});
