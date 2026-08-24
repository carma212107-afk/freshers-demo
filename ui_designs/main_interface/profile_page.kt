import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun MyProfileExamplePage(
	onBack: () -> Unit = {},
	onEdit: () -> Unit = {},
	onHome: () -> Unit = {},
	onSearch: () -> Unit = {},
	onAddRide: () -> Unit = {},
	onRides: () -> Unit = {},
	modifier: Modifier = Modifier,
	user: User, // defaults to current user, but can be set to any user for viewing other profiles
) {
	Box(modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1)) {
		Column(modifier = Modifier.fillMaxSize()) {
			ProfileTopBar(onBack = onBack, onEdit = onEdit)
			LazyColumn(
				modifier = Modifier.weight(1f),
				contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 18.dp),
				verticalArrangement = Arrangement.spacedBy(17.dp),
			) {
				item { ProfileHeader(user) }
				item { AboutSection(user.aboutMe) }
				item { CarbonImpactSection(user) }
				item { ReviewSection(user.reviews) }
			}
			ProfileBottomBar(onHome, onSearch, onAddRide, onRides)
		}
	}
}


// content

@Composable
private fun ProfileTopBar(onBack: () -> Unit, onEdit: () -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground).padding(start = 15.dp, end = 15.dp, top = 58.dp, bottom = 15.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(15.dp),
	) {
		BackButton(onClick = onBack)
		Text("Profile", modifier = Modifier.weight(1f), color = Colours.DarkModeText, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
		if (user.isCurrentUser) { // only show edit button if viewing own profile
			val editButton = MenuBarButton()
			editButton.text: String = "Edit"
			editButton.modifier: Modifier = Modifier.clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(20.dp)).clickable(onClick = onEdit).padding(horizontal = 20.dp, vertical = 10.dp)
			editButton.colour: Color = Colours.DarkModeText
		}
	}

	if (user.isCurrentUser) { // only show edit button if viewing own profile
		val editButton = MenuBarButton()
		editButton.text: String = "Edit"
		editButton.modifier: Modifier = Modifier.clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(20.dp)).clickable(onClick = onEdit).padding(horizontal = 20.dp, vertical = 10.dp)
	}
	TopMenuBar("Profile", rightButton = editButton)
}

@Composable
private fun ProfileHeader(user: User) {
	Column(modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground).padding(horizontal = 30.dp, vertical = 15.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(19.dp)) {
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
			Column (modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
				ProfilePic(modifier = Modifier.size(140.dp), contentAlignment = Alignment.Center, initials = user.getInitials(), size = 140.dp, theme = ProfilePicTheme.Dark)
				for (profile in user.socialProfiles) {
					SocialProfileButton(profile, modifier = Modifier.fillMaxWidth())
				}
			}
			Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
				Text("${user.getFullName()}", color = Colours.DarkModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight, textAlign = TextAlign.Center)
				Text("${user.university}", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				Text("${user.getFormattedUniYear()}", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				Text("${user.uniCourse}", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
					Text("${user.getFormattedRating()}", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
					StarRating(filled = user.rating, theme = Theme.Dark)
					Text("(${user.noOfRides})", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				}				
			}
		}
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			VerificationChip("✓ Verified student")
			VerificationChip("✓ Verified driver")
		}
		Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(20.dp)).background(Colours.DarkModePrimary).padding(horizontal = 20.dp, vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
			Stat("${user.getPositiveRatingPercentage()}%\npositive")
			Stat("${user.noOfRides()}\nrides")
			Stat("saved\n${user.carbonSaved}kg\nCO2")
		}
	}
}

@Composable
private fun AboutSection(bio: String = null) {
	if (bio != null) {
		ProfileSection {
			Text("ABOUT ME", color = Colours.LightModeText, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
			Text(bio, color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, lineHeight = 21.sp)
		}
	}
}

@Composable
private fun CarbonImpactSection(user: User) {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).clip(RoundedCornerShape(20.dp)).background(Colours.DarkModeBackground).padding(15.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("YOUR CARBON IMPACT", color = Colours.LightModeSecondary, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Row(verticalAlignment = Alignment.Bottom) {
			Text("${user.carbonSaved}", color = Colours.DarkModeText, fontSize = TextFormatting.Figures1.size, fontWeight = TextFormatting.Figures1.weight)
			Text(" kg CO2 saved", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, modifier = Modifier.padding(bottom = 4.dp))
		}
		Text("Equivalent to planting ${user.treesPlanted} trees", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
	}
}

@Composable
private fun ReviewSection(reviews: List<Review>) {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("Recent reviews", color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		for (r in reviews) { IndividualReview(r) }
	}
}


// components used only in the profile page

@Composable
private fun ProfileSection(content: @Composable () -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.LightModeBorder, RoundedCornerShape(20.dp)).background(Colours.LightModeBackground2).padding(horizontal = 15.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp), content = content)
}

enum class SocialProfile { Instagram, Snapchat }
private const val instagramLogo = "https://play-lh.googleusercontent.com/yHi59jmO_lVamcyJ1i3rM1_E8bAiAspShnGjjURq05ipQQSUksO3QVEsXTegRSqul038-4YNA7O644XAcx251Q=s48"
private const val snapchatLogo = "https://play-lh.googleusercontent.com/nOJWJoDAzuRSHmlFfpzHXAj8UbTJwk_JGYf0uAPTKPtiiIX_2Y7r5ATO1ZtxHRqaFTqfIDZ40PnBK4hukeJh=w240-h480-rw"
@Composable
private fun SocialProfileButton(profile: SocialProfile = SocialProfile.Instagram, modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.border(1.dp, Colours.DarkModePrimary, RoundedCornerShape(20.dp))
			.background(Colours.LightModeBackground2, RoundedCornerShape(20.dp))
			.padding(horizontal = 15.dp, vertical = 6.dp),
		horizontalArrangement = Arrangement.spacedBy(5.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		if (profile == SocialProfile.Instagram) AsyncImage(model = instagramLogo, contentDescription = "Instagram Logo", modifier = Modifier.size(25.dp)) else AsyncImage(model = snapchatLogo, contentDescription = "Snapchat Logo", modifier = Modifier.size(25.dp))
		Text(if (profile == SocialProfile.Instagram) "${user.socialProfiles[SocialProfile.Instagram]}" else "${user.socialProfiles[SocialProfile.Snapchat]}", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
		// Add click functionality to open the respective social media profile in a web browser or app
	}
}

@Composable
private fun VerificationChip(label: String) {
	Text(label, modifier = Modifier.border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(20.dp)).padding(horizontal = 15.dp, vertical = 6.dp), color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, textAlign = TextAlign.Center)
}

@Composable
private fun Stat(value: String, label: String = "") {
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, textAlign = TextAlign.Center)
		if (label.isNotEmpty()) Text(label, color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, textAlign = TextAlign.Center)
	}
}