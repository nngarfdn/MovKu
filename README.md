
# MovKu

An application that displays a list of popular movies and detail




## Concepts

![The Clean Architecture](https://miro.medium.com/max/1400/1*B7LkQDyDqLN3rRSrNYkETA.jpeg)

![The Clean Architecture](https://blog.kakaocdn.net/dn/AoLDH/btqE9uU0vXZ/bjdSIC9inFaiTKfhKzU7ok/img.png)


## Tech Stack

- [Kotlin](https://kotlinlang.org/) -A modern programming language that makes developers happier
- [Coroutines](https://developer.android.com/kotlin/coroutines) - A concurrency design pattern library
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection framework
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - View Binding
- [Retrofit](https://github.com/square/retrofit) - HTTP client
- [Glide](https://github.com/bumptech/glide) - Loading images
- [Gson](https://github.com/google/gson) - JSON library
- [Room](https://developer.android.com/training/data-storage/room) - Local database android
- [Material Components](https://github.com/material-components/material-components-android) - Material Design



## API Reference

#### This app consume TMDP API

```http
  GET /movie/popular
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /movie/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `api_key` | `string` | **Required**. Your API key        |
| `id`      | `string` | **Required**. Id of item to fetch |



## Authors

- [@nngarfdn](https://www.github.com/nngarfdn)

