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

private val profileGreen = Color(0xFF0A5C2E)
private val profileGreenLight = Color(0xFF2A7149)
private val profileMint = Color(0xFFB5DDC3)
private val profileBackground = Color(0xFFF5FFF8)

private const val circleAsset = "https://www.figma.com/api/mcp/asset/53e1e70e-7107-4d9d-9862-3751a27b6936.svg"
private const val instagramAsset = "https://www.figma.com/api/mcp/asset/1d10f5cd-9e71-45bf-8d3e-42dc236f24ef.png"


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
	Box(modifier = modifier.fillMaxSize().background(profileBackground)) {
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
			Text("Edit", modifier = Modifier.clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.DarkModeText, RoundedCornerShape(20.dp)).clickable(onClick = onEdit).padding(horizontal = 20.dp, vertical = 10.dp), color = Colours.DarkModeText, fontSize = TextFormatting.Button1.size, fontWeight = TextFormatting.Button1.weight, textAlign = TextAlign.Center)
		}
	}
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
				Text("University of Exeter", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				Text("Year 2", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				Text("<BSc>", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
					Text("$(user.rating)", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
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
		for (review in reviews) {
			ProfileSection {
				Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
					ProfilePic(initials = review.reviewer.getInitials())
					Spacer(Modifier.width(5.dp))
					Column(modifier = Modifier.weight(1f)) {
						Text("${review.reviewer.getFormattedFirstName()}", color = Colours.LightModeText, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
						Text("${review.startCity} → ${review.endCity}", color = Colours.LightModeText, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight)
					}
					StarRating(filled = review.rating)
				}
				Text("“${review.quote}”", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			}
		}
	}
}

@Composable
private fun ProfileSection(content: @Composable () -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.LightModeBorder, RoundedCornerShape(20.dp)).background(Colours.LightModeBackground2).padding(horizontal = 15.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp), content = content)
}

