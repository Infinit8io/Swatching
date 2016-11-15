import requests
import base64
import json
import sys
from movie import Movie
import urllib.request
from config import TMDB_KEY

movies = []
movies_dicts = []

def get_movie_data(id_mov):
    r = requests.get("https://api.themoviedb.org/3/movie/"+id_mov+"?api_key="+TMDB_KEY)
    if r.status_code == 200:
        return json.loads(r.text)

def get_movie_poster_as_b64(url):
    with urllib.request.urlopen(url) as urll:
        s = urll.read()
    return base64.b64encode(s)


if __name__ == "__main__":
    if len(sys.argv) == 1:
        print("Not enough arguments")
        exit()

    for arg in sys.argv[1:]:
        data = get_movie_data(arg)
        movies.append(Movie(int(arg), data.get("title"), data.get("poster_path"), data.get("genres")))

    for movie in movies:
        movie.set_movie_poster_b64(get_movie_poster_as_b64(movie.get_poster_url()))

    str_return = ""

    for movie in movies:
        movies_dicts.append(movie.__dict__)


    with open("movies.json", "w") as f:
        f.write(json.dumps(movies_dicts))
