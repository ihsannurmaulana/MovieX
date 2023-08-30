import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.moviex.R
import com.example.moviex.api.Movie
import com.example.moviex.api.authorizationHeader
import com.example.moviex.api.movieApiService
import com.example.moviex.database.MovieFavEntity
import com.example.moviex.database.MyApplication
import com.example.moviex.screens.userLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DetailScreen(navController: NavController, itemId: String) {
    val context = LocalContext.current
    val isFavorite = remember { mutableStateOf(false) }
    val movieDao = (context.applicationContext as MyApplication).database.movieFavDao()
    var movieList by remember {
        mutableStateOf<List<MovieFavEntity>?>(null)
    }
    val movieDetail = remember {
        mutableStateOf<Movie?>(null)
    }

    LaunchedEffect(itemId) {
        val response = movieApiService.getDetailMovies(authorizationHeader, itemId)
        movieDetail.value = response

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                movieList = userLogin?.let {
                    movieDao.getAllByUserId(it.id)
                }

                val isMovieFavoriteList = movieList?.any { movieFav ->
                    movieFav.movie_id == (movieDetail.value?.id ?: 0)
                } ?: false

                if (isMovieFavoriteList) {
                    isFavorite.value = true
                }
            }
        }
    }

    val movie = movieDetail.value

    if (movie != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.img_back),
                            contentDescription = "Back",
                            tint = Color.Red,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            Color.Red, contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.play_button),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Play", style = MaterialTheme.typography.bodyLarge)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = {
                            val movieDao =
                                (context.applicationContext as MyApplication).database.movieFavDao()
                            val userDao =
                                (context.applicationContext as MyApplication).database.UserDao()

                            if (!isFavorite.value) {

                                CoroutineScope(Dispatchers.IO).launch {
                                    val user = userDao.getUserByLogin(true)

                                    user.let { nonNullUser ->
                                        val movieFavEntity = MovieFavEntity(
                                            movie_id = movie.id,
                                            title = movie.title,
                                            poster_path = movie.poster_path,
                                            overview = movie.overview,
                                            release_date = movie.release_date,
                                            user_id = nonNullUser.id
                                        )
                                        movieDao.insertMovie(movieFavEntity)
                                    }
                                }

                                isFavorite.value = true
                                Toast.makeText(context, "Add to favorite", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val user = userDao.getUserByLogin(true)
                                    val movies = movieDao.getAllByUserId(user.id)
                                    for (movieFav in movies) {
                                        if (movieFav.movie_id == movie.id) {
                                            movieDao.deleteMovieFav(movie.id)
                                        }
                                    }
                                }

                                isFavorite.value = false
                                Toast.makeText(context, "Delete from favorite", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "",
                                modifier = Modifier.size(50.dp),
                                tint = if (isFavorite.value) Color.Red else LocalContentColor.current
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // "Release date" and "Genres"
                Text(
                    text = "${
                        movie.release_date.substring(
                            0, 4
                        )
                    } - ${movie.genres?.joinToString(", ") { it.name }}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Overview
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(navController = rememberNavController(), itemId = "Preview")
}
