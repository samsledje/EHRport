from GlobalContext import GC

class Event:
    def __init__(self, tag, content, files = None):
        self.tag = tag
        self.board = GC.get_board(tag)
        self.content = content
        self.files = []