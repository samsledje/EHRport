import http.server, json, cgi
from Objects import *

HOST = '0.0.0.0'
PORT = 8080

class Handler(http.server.BaseHTTPRequestHandler):
    def do_HEAD(self):
        return

    def do_POST(self):
        return

    def do_GET(self):
        if self.path.find('?') != -1:
            self.path, self.query_string = self.path.split('?', 1)
        else:
            self.query_string = 'hello=world'
        self.globals = dict(cgi.parse_qsl(self.query_string))
        self.patid = self.headers.getparam("patid")
        #assert self.headers.getsubtype() == 'json',
        self.respond(self.globals)

    def handle_http(self, status, content_type, D):
        # D is a dictionary representing a json object
        self.send_response(status)
        self.send_header('Content-type', content_type)
        self.end_headers()
        return bytes(json.dumps(D), "UTF-8")
    
    def respond(self, D):
        content = self.handle_http(200, 'application/json', D)
        self.wfile.write(content)

server = http.server.HTTPServer((HOST, PORT), Handler)
try:
    print('Started http server')
    server.serve_forever()
except KeyboardInterrupt:
    print('^C received, shutting down server')
    server.socket.close()