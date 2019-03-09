import http.server
import json

HOST = 'localhost'
PORT = 8081

class Handler(http.server.BaseHTTPRequestHandler):
    def do_HEAD(self):
        return

    def do_POST(self):
        return

    def do_GET(self):
        if self.path.find('?') != -1:
            self.path, self.query_string = self.path.split('?', 1)
        else:
            self.query_string = ''
        self.respond()

    def handle_http(self, status, content_type):
        self.send_response(status)
        self.send_header('Content-type', content_type)
        self.end_headers()
        return bytes(json.dumps({'hello': 'world'}), "UTF-8")
    
    def respond(self):
        content = self.handle_http(200, 'application/json')
        self.wfile.write(content)

server = http.server.HTTPServer((HOST, PORT), Handler)
try:
    print('Started http server')
    server.serve_forever()
except KeyboardInterrupt:
    print('^C received, shutting down server')
    server.socket.close()