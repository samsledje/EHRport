class Event:
    def __init__(self, _id, doctor, room, contents, create_time):
        self._id = _id
        self.doctor = doctor
        self.room = room
        self.contents = contents
        self.create_time = create_time

    def dump(self):
        pass