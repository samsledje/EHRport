class GC:
    self.boards = {tags: Board()}
    self.doctors = set(Doctor())
    self.rooms = set()

    def get_board(tag):
        return self.boards[tag]