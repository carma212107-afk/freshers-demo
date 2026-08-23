import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val profileComponentGreen = Color(0xFF0A5C2E)
private val profileComponentMint = Color(0xFFB5DDC3)
private val profileComponentWhite = Color(0xFFF5FFF8)
private val profileComponentPurple = Color(0xFF8A38F5)

@Composable
fun ProfileComponents(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.spacedBy(20.dp),
		verticalAlignment = Alignment.Top,
	) {
		Column(
			modifier = Modifier.border(1.dp, profileComponentPurple).padding(5.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(5.dp),
		) {
			Text("Profile Pics", color = profileComponentPurple, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
			Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
				ProfileComponentPic(ProfilePicTheme.Light, unread = true)
				ProfileComponentPic(ProfilePicTheme.Light)
			}
			ProfileComponentPic(ProfilePicTheme.Dark)
		}
		Column(
			modifier = Modifier.width(150.dp).border(1.dp, profileComponentPurple).padding(5.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(5.dp),
		) {
			Text("Rating", color = profileComponentPurple, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
			NumberRating()
			StarRating(filled = 4, dark = false)
			StarRating(filled = 5, dark = true)
		}
	}
}

@Composable
fun ProfilePic(theme: Theme = Theme.Light, unread: Boolean = false, modifier: Modifier = Modifier, initials: String = "AB") {
	val large = theme == Theme.Dark && !unread
	val diameter = if (large) 75.dp else 30.dp
	Box(modifier = modifier.size(diameter), contentAlignment = Alignment.Center) {
		Canvas(Modifier.matchParentSize()) {
			drawCircle(if (large) Colours.Accent else Colours.LightModeSecondary, radius = size.minDimension / 2f)
			if (!large) drawCircle(Colours.LightModePrimary, radius = size.minDimension / 2f, style = Stroke(1f))
		}
		Text(
			initials,
			color = if (large) Colours.LightModeSecondary else Colours.LightModePrimary,
			fontSize = if (large) TextFormatting.Heading2.size else TextFormatting.Boxes1.size,
			fontWeight = if (large) TextFormatting.Heading2.weight else TextFormatting.Boxes1.weight,
		)
	}
}


enum class SocialProfile { Instagram, Snapchat }
private const val instagramLogo = "https://play-lh.googleusercontent.com/yHi59jmO_lVamcyJ1i3rM1_E8bAiAspShnGjjURq05ipQQSUksO3QVEsXTegRSqul038-4YNA7O644XAcx251Q=s48"
private const val snapchatLogo = "https://play-lh.googleusercontent.com/nOJWJoDAzuRSHmlFfpzHXAj8UbTJwk_JGYf0uAPTKPtiiIX_2Y7r5ATO1ZtxHRqaFTqfIDZ40PnBK4hukeJh=w240-h480-rw"
@Composable
fun SocialProfileButton(profile: SocialProfile = SocialProfile.Instagram, modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.border(1.dp, profileComponentMint, RoundedCornerShape(20.dp))
			.background(Color.White, RoundedCornerShape(20.dp))
			.padding(horizontal = 15.dp, vertical = 6.dp),
		horizontalArrangement = Arrangement.spacedBy(5.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		if (profile == SocialProfile.Instagram) AsyncImage(model = instagramLogo, contentDescription = "Instagram Logo", modifier = Modifier.size(25.dp)) else AsyncImage(model = snapchatLogo, contentDescription = "Snapchat Logo", modifier = Modifier.size(25.dp))
		Text(if (profile == SocialProfile.Instagram) "@instagram" else "@snapchat", color = profileComponentGreen, fontSize = 15.sp)
	}
}



@Composable
fun NumberRating(modifier: Modifier = Modifier) {
	Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
		StarGlyph(filled = true, color = profileComponentGreen, modifier = Modifier.size(20.dp))
		Text("4.5", color = profileComponentGreen, fontSize = 15.sp, fontWeight = FontWeight.Bold)
	}
}

@Composable
fun StarRating(filled: Int = 5, dark: Boolean = false, modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.background(if (dark) profileComponentGreen else Color.Transparent, RoundedCornerShape(20.dp))
			.padding(horizontal = if (dark) 5.dp else 0.dp, vertical = if (dark) 2.dp else 0.dp),
		horizontalArrangement = Arrangement.spacedBy(1.dp),
	) {
		repeat(5) { index ->
			StarGlyph(filled = index < filled, color = if (dark) profileComponentWhite else profileComponentGreen, modifier = Modifier.size(20.dp))
		}
	}
}

@Composable
private fun StarGlyph(filled: Boolean, color: Color, modifier: Modifier = Modifier) {
	Canvas(modifier) {
		val center = Offset(size.width / 2f, size.height / 2f)
		val outer = size.minDimension * 0.44f
		val inner = outer * 0.42f
		val star = Path()
		repeat(10) { index ->
			val radius = if (index % 2 == 0) outer else inner
			val angle = Math.toRadians(-90.0 + index * 36.0)
			val point = Offset(center.x + (kotlin.math.cos(angle) * radius).toFloat(), center.y + (kotlin.math.sin(angle) * radius).toFloat())
			if (index == 0) star.moveTo(point.x, point.y) else star.lineTo(point.x, point.y)
		}
		star.close()
		drawPath(star, color, style = if (filled) androidx.compose.ui.graphics.drawscope.Fill else Stroke(width = 1.5f, join = StrokeJoin.Round))
	}
}



@Composable
fun Review(
	modifier: Modifier = Modifier,
	review = Review,
) {
	Column(
		modifier = modifier
			.width(342.dp)
			.border(1.dp, profileComponentMint, RoundedCornerShape(20.dp))
			.background(Colours.LightModeBackground2, RoundedCornerShape(20.dp))
			.padding(horizontal = 15.dp, vertical = 10.dp),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.Top,
			horizontalArrangement = Arrangement.spacedBy(5.dp),
		) {
			ProfilePic(modifier = Modifier.size(30.dp), initials = review.reviewer.getInitials())
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
				Text(review.reviewer.getFormattedFirstName(), color = Colours.LightModeText, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
				Text("${review.ride.startCity}  →  ${review.ride.endCity}", color = Colours.LightModeText, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight)
			}
			StarRating(filled = 4, modifier = Modifier.width(100.dp))
		}
		Text(review.quote, color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
	}
}
