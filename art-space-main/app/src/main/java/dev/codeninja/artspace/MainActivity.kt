package dev.codeninja.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.codeninja.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ArtSpaceTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					ArtSpaceScreen()
				}
			}
		}
	}
}

// Data holder for each artwork
private data class Artwork(
	@DrawableRes val imageRes: Int,
	@StringRes   val titleRes: Int,
	@StringRes   val yearRes: Int
)

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
	// List of classic artworks
	val artworks = listOf(
		Artwork(R.drawable.mona_lisa,               R.string.mona_lisa,               R.string.mona_lisa_year),
		Artwork(R.drawable.starry_night,            R.string.starry_night,            R.string.starry_night_year),
		Artwork(R.drawable.girl_with_pearl_earring, R.string.girl_with_pearl_earring, R.string.girl_with_pearl_earring_year),
		Artwork(R.drawable.birth_of_venus,          R.string.birth_of_venus,          R.string.birth_of_venus_year)
	)

	var currentIndex by remember { mutableStateOf(0) }
	val (imageRes, titleRes, yearRes) = artworks[currentIndex]

	Column(
		modifier = modifier
			.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		ArtworkDisplay(
			currentArtwork = imageRes,
			description    = titleRes,
			modifier       = Modifier
				.fillMaxWidth()
				.padding(16.dp)
				.clip(RoundedCornerShape(12.dp))
		)

		Spacer(Modifier.height(16.dp))

		ArtworkTitle(titleRes, yearRes)

		Spacer(Modifier.height(24.dp))

		Row(
			modifier             = modifier.padding(horizontal = 16.dp),
			horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
		) {
			Button(
				onClick = {
					currentIndex = if (currentIndex - 1 < 0) artworks.lastIndex else currentIndex - 1
				},
				colors    = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.gray_900)),
				elevation = ButtonDefaults.elevation(defaultElevation = 1.dp)
			) {
				Text(
					text       = stringResource(R.string.previous),
					fontSize   = 16.sp,
					fontWeight = FontWeight.Medium,
					color      = colorResource(id = R.color.blue_300)
				)
			}

			Button(
				onClick = {
					currentIndex = (currentIndex + 1) % artworks.size
				},
				colors    = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.blue_300)),
				elevation = ButtonDefaults.elevation(defaultElevation = 1.dp)
			) {
				Text(
					text       = stringResource(R.string.next),
					fontSize   = 16.sp,
					fontWeight = FontWeight.Medium,
					color      = colorResource(id = R.color.gray_900)
				)
			}
		}
	}
}

@Composable
fun ArtworkDisplay(
	modifier: Modifier = Modifier,
	@DrawableRes currentArtwork: Int,
	@StringRes   description: Int
) {
	Image(
		painter           = painterResource(currentArtwork),
		contentDescription = stringResource(description),
		modifier          = modifier,
		contentScale      = ContentScale.FillWidth
	)
}

@Composable
fun ArtworkTitle(
	@StringRes title: Int,
	@StringRes year: Int
) {
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Text(
			text       = stringResource(id = title),
			fontWeight = FontWeight.Bold,
			color      = colorResource(id = R.color.blue_100),
			fontSize   = 32.sp
		)
		Text(
			text       = "— ${stringResource(id = year)} —",
			fontSize   = 16.sp,
			fontWeight = FontWeight.Medium,
			color      = colorResource(id = R.color.gray_300)
		)
	}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	ArtSpaceTheme {
		ArtSpaceScreen()
	}
}
