import http.server, json, cgi
import mysql.connector
import itertools
from Objects import *

HOST = '0.0.0.0'
PORT = 8080

DB_HOST = '67.221.89.61'

class Handler(http.server.BaseHTTPRequestHandler):
    def __init__(self, *args):
        self.get_types = {
            '/boards': self.get_boards,
            '/rooms': self.get_rooms,
            '/doctors': self.get_doctors
        }

        self.enter_types = {
            '/boards': self.enter_board,
            '/rooms': self.enter_room,
            '/doctors': self.enter_doctor
        }
        super(Handler, self).__init__(*args)

    # Request Types
    def get_boards(self):
        # returns array of board names
        cursor = self.DB.cursor()
        cursor.execute("SELECT board.tag FROM board NATURAL JOIN event_tags NATURAL JOIN event_ NATURAL JOIN patient WHERE patient._id = {} GROUP BY board.tag".format(self.patid))
        r = cursor.fetchall()
        cursor.close()
        return [{'tag': l[0]} for l in r]

    def get_rooms(self):
        # returns array of room names
        cursor = self.DB.cursor()
        cursor.execute("SELECT room.title, room._id, room.activity FROM room JOIN patient ON room.patient = patient._id WHERE patient._id = {}".format(self.patid))
        r = cursor.fetchall()
        cursor.close()
        return [{'id': l[1], 'title': l[0], 'activity': l[2]} for l in r]

    def get_doctors(self):
        # returns array of doctor names
        cursor = self.DB.cursor()
        cursor.execute("SELECT doctor.firstname, doctor.lastname, doctor._id FROM doctor JOIN board_permissions ON doctor._id = board_permissions.doctor JOIN  patient ON patient._id = board_permissions.patient WHERE patient._id = {} GROUP BY doctor._id".format(self.patid))
        r = cursor.fetchall()
        cursor.close()
        return [{'id': l[2], 'first': l[0], 'last': l[1]} for l in r]

    def enter_board(self):
        # returns json of events
        cursor = self.DB.cursor()
        cursor.execute("")
        r = cursor.fetchall()
        cursor.close()
        return

    def enter_room(self):
        # returns array of events
        cursor = self.DB.cursor()
        cursor.execute("")
        r = cursor.fetchall()
        cursor.close()
        return

    def enter_doctor(self):
        # returns doctor info
        cursor = self.DB.cursor()
        cursor.execute("")
        r = cursor.fetchall()
        cursor.close()
        return

    # Handle HTTP Requests
    def do_POST(self):
        return

    def do_GET(self):
        self.DB = mysql.connector.connect(
            host=DB_HOST,
            user='hacker',
            passwd='hacker2019',
            database='hack2019'
        )

        if self.path.find('?') != -1:
            self.path, self.query_string = self.path.split('?', 1)
            self.id = dict(cgi.parse_qsl(self.query_string))['id']
        else:
            self.query_string = None
        #self.patid = self.headers.get('patid')
        self.patid = '1'
        if self.query_string is not None:
            J = self.enter_types[self.path]()
        else:
            J = self.get_types[self.path]()
        self.DB.close()
        self.respond(J)

    def handle_http(self, status, content_type, J):
        # D is a dictionary representing a json object
        self.send_response(status)
        self.send_header('Content-type', content_type)
        self.end_headers()
        return bytes(json.dumps(J), "UTF-8")
    
    def respond(self, J):
        content = self.handle_http(200, 'application/json', J)
        self.wfile.write(content)

server = http.server.HTTPServer((HOST, PORT), Handler)
try:
    print('Started http server')
    server.serve_forever()
except KeyboardInterrupt:
    print('^C received, shutting down server')
    server.socket.close()