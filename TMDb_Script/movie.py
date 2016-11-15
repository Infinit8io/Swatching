class Movie:
    def __init__(self, id_mov, title, poster_url, genres):
        self.id = id_mov
        self.title = title
        self.poster_url = poster_url
        self.genres = genres

    def get_poster_url(self):
        return "http://image.tmdb.org/t/p/w154"+self.poster_url

    def set_movie_poster_b64(self, b64str):
        self.poster_b64  = str(b64str)[2:-1]
