import http.server, json, cgi
import mysql.connector
import time

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
        }

        self.put_types = {
            '/rooms': self.post_room,
            '/boards': self.post_board,
        }

        self.delete_types = {
            '/rooms': self.delete_room,
            '/boards': self.delete_board,
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
        cursor.execute("SELECT room.title, room._id, room.activity FROM room WHERE room.patient = {}".format(self.patid))
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
        cursor.execute("SELECT event_._id, event_.doctor, event_.contents, event_.tags, event_.create_time "
                       "FROM event_ JOIN room ON event_.room = room._id JOIN event_tags ON event_._id = event_tags.event_ "
                       "WHERE event_tags.tag = \"{}\" AND room.patient = {};".format(self.id, self.patid))
        r = cursor.fetchall()
        cursor.close()
        return [{'id': l[0], 'doctor': l[1], 'contents': l[2], 'tag': l[3], 'create_time': int(time.mktime(l[4].timetuple()))} for l in r]

    def enter_room(self):
        print("getting room {}".format(self.id))
        # returns array of events
        cursor = self.DB.cursor()
        cursor.execute("SELECT event_._id, event_.doctor, event_.contents, event_.tags, event_.create_time FROM event_ WHERE event_.room = {} ORDER BY event_.create_time;".format(self.id))
        r = cursor.fetchall()
        cursor.close()
        return [{'id': l[0], 'doctor': l[1], 'contents': l[2], 'tag': l[3], 'create_time': int(time.mktime(l[4].timetuple()))} for l in r]

    def post_room(self, **kwargs):
        if 'doctor' in kwargs:
            cursor = self.DB.cursor()
            cursor.execute("INSERT INTO room_permissions (doctor, room) VALUES ({}, {})".format(kwargs['doctor'], kwargs['id']))
            cursor.close()
            self.DB.commit()
            return ""
        elif 'activity' in kwargs:
            cursor = self.DB.cursor()
            cursor.execute("UPDATE room SET activity = {} WHERE _id = {}".format(kwargs['activity'], kwargs['id']))
            cursor.close()
            self.DB.commit()
            return ""
        elif 'event' in kwargs:
            # TODO
            pass

    def post_board(self, **kwargs):
        if 'doctor' in kwargs:
            cursor = self.DB.cursor()
            cursor.execute("INSERT INTO board_permissions (patient, doctor, board) VALUES ({}, {}, {})".format(self.patid, kwargs['doctor'], kwargs['id']))
            cursor.close()
            self.DB.commit()
            return ""

    def delete_room(self, **kwargs):
        cursor = self.DB.cursor()
        cursor.execute("DELETE FROM room_permissions WHERE doctor = {} AND room = {}".format(kwargs["doctor"], kwargs["id"]))
        cursor.close()
        self.DB.commit()
        return ""

    def delete_board(self, **kwargs):
        cursor = self.DB.cursor()
        cursor.execute("DELETE FROM board_permissions WHERE doctor = {} AND board = {} AND patient = {}".format(kwargs["doctor"], kwargs["id"], self.patid))
        self.DB.commit()
        return ""

    # Handle HTTP Requests
    def do_PUT(self):
        self.DB = mysql.connector.connect(
            host=DB_HOST,
            user='hacker',
            passwd='hacker2019',
            database='hack2019',
            auth_plugin='mysql_native_password',
        )

        self.patid = self.headers.get('patid')

        J = None

        if self.path.find('?') != -1:
            self.path, self.query_string = self.path.split('?', 1)
            params = {k: v for k, v in [x.split('=') for x in self.query_string.split('&')]}
            J = self.put_types[self.path](**params)


        return J

    def do_DELETE(self):
        self.DB = mysql.connector.connect(
            host=DB_HOST,
            user='hacker',
            passwd='hacker2019',
            database='hack2019',
            auth_plugin='mysql_native_password',
        )

        J = None

        if self.path.find('?') != -1:
            self.path, self.query_string = self.path.split('?', 1)
            params = {k: v for k, v in [x.split('=') for x in self.query_string.split('&')]}
            J = self.delete_types[self.path](**params)

        return J


    def do_GET(self):
        if self.path == '/favicon.ico':
            return '{}'
        self.DB = mysql.connector.connect(
            host=DB_HOST,
            user='hacker',
            passwd='hacker2019',
            database='hack2019',
            auth_plugin='mysql_native_password',
        )

        if self.path.find('?') != -1:
            self.path, self.query_string = self.path.split('?', 1)
            self.id = dict(cgi.parse_qsl(self.query_string))['id']
        else:
            self.query_string = None
        self.patid = self.headers.get('patid')
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