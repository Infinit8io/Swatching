# Swatching
[![Join the chat at https://gitter.im/Infinit8io/Swatching](https://badges.gitter.im/Infinit8io/Swatching.svg)](https://gitter.im/Infinit8io/Swatching?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Application Android proposant des films en fonction des goûts de l'utilisateur. Lors de la première utilisation, il est demandé de choisir les films vu et aimés parmi une liste de films sélectionnés avec soin et appartenant à des genres variés. Cela permet de dresser un profil de préférences de base de l'utilisateur pour ensuite cibler correctement les films qui lui sont proposés. 

## Fonctionnalités

Sur la page de proposition d'un film, il suffit de secouer légèrement le téléphone pour se faire conseiller un autre film. Si un film donne envie à l'utilisateur, il peut cliquer "I will watch it!". Cela ajoutera ce film à sa liste "To Watch" et il pourra confirmer s'il l'a vu et aimé ou pas aimé par la suite.

Si le film proposé a déjà été vu par l'utilisateur, il a le choix entre "Seen and good!" ou "Seen and bad!"

## Liste des genres

```json
{
  "genres": [
    {
      "id": 28,
      "name": "Action"
    },
    {
      "id": 12,
      "name": "Adventure"
    },
    {
      "id": 16,
      "name": "Animation"
    },
    {
      "id": 35,
      "name": "Comedy"
    },
    {
      "id": 80,
      "name": "Crime"
    },
    {
      "id": 99,
      "name": "Documentary"
    },
    {
      "id": 18,
      "name": "Drama"
    },
    {
      "id": 10751,
      "name": "Family"
    },
    {
      "id": 14,
      "name": "Fantasy"
    },
    {
      "id": 36,
      "name": "History"
    },
    {
      "id": 27,
      "name": "Horror"
    },
    {
      "id": 10402,
      "name": "Music"
    },
    {
      "id": 9648,
      "name": "Mystery"
    },
    {
      "id": 10749,
      "name": "Romance"
    },
    {
      "id": 878,
      "name": "Science Fiction"
    },
    {
      "id": 10770,
      "name": "TV Movie"
    },
    {
      "id": 53,
      "name": "Thriller"
    },
    {
      "id": 10752,
      "name": "War"
    },
    {
      "id": 37,
      "name": "Western"
    }
  ]
}
```

Ou en français de la France:

```json
{
  "genres": [
    {
      "id":28,"name":"Action"
    },
    {
      "id":12,"name":"Aventure"
    },
    {
      "id":16,"name":"Animation"
    },
    {
      "id":35,"name":"Comédie"
    },
    {
      "id":80,"name":"Crime"
    },
    {
      "id":99,"name":"Documentaire"
    },
    {
      "id":18,"name":"Drame"
    },
    {
      "id":10751,"name":"Familial"
    },
    {
      "id":14,"name":"Fantastique"
    },
    {
      "id":36,"name":"Histoire"
    },
    {
      "id":27,"name":"Horreur"
    },
    {
      "id":10402,"name":"Musique"
    },
    {
      "id":9648,"name":"Mystère"
    },
    {
      "id":10749,"name":"Romance"
    },
    {
      "id":878,"name":"Science-Fiction"
    },
    {
      "id":10770,"name":"Téléfilm"
    },
    {
      "id":53,"name":"Thriller"
    },
    {
      "id":10752,"name":"Guerre"
    },
    {
      "id":37,"name":"Western"
    }
  ]
}
```
