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
private const val homeAsset = "https://www.figma.com/api/mcp/asset/bf2e91af-77f1-43fd-a192-20ef560e14a3.svg"
private const val pinAsset = "https://www.figma.com/api/mcp/asset/d774975f-5f3a-4bf1-91cf-63330e1a3620.svg"
private const val carBodyAsset = "https://www.figma.com/api/mcp/asset/3cadbe92-c3b5-4af9-b981-8989ebb52a45.svg"
private const val carBackWheelAsset = "https://www.figma.com/api/mcp/asset/e3a8667f-5eae-4cb7-b968-82fbbd5868e9.svg"
private const val carFrontWheelAsset = "https://www.figma.com/api/mcp/asset/23493609-b260-4ca6-a63a-5de3df40df53.svg"
private const val profileBodyAsset = "https://www.figma.com/api/mcp/asset/4f7672c0-25f1-4d52-b60b-8f0fa33559b2.svg"
private const val profileHeadAsset = "https://www.figma.com/api/mcp/asset/8ab73d6d-6812-450d-a4c2-973d334dce6a.svg"

@Composable
fun MyProfileExamplePage(
	onBack: () -> Unit = {},
	onEdit: () -> Unit = {},
	onHome: () -> Unit = {},
	onSearch: () -> Unit = {},
	onAddRide: () -> Unit = {},
	onRides: () -> Unit = {},
	modifier: Modifier = Modifier,
) {
	Box(modifier = modifier.fillMaxSize().background(profileBackground)) {
		Column(modifier = Modifier.fillMaxSize()) {
			ProfileTopBar(onBack = onBack, onEdit = onEdit)
			LazyColumn(
				modifier = Modifier.weight(1f),
				contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 18.dp),
				verticalArrangement = Arrangement.spacedBy(17.dp),
			) {
				item { ProfileHeader() }
				item { AboutSection() }
				item { CarbonImpactSection() }
				item { ReviewSection() }
			}
			ProfileBottomBar(onHome, onSearch, onAddRide, onRides)
		}
	}
}

@Composable
private fun ProfileTopBar(onBack: () -> Unit, onEdit: () -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth().background(profileGreen).padding(start = 15.dp, end = 15.dp, top = 58.dp, bottom = 15.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(15.dp),
	) {
		Text("‹", modifier = Modifier.size(40.dp).border(1.dp, Color.White, CircleShape).clickable(onClick = onBack).padding(bottom = 4.dp), color = Color.White, fontSize = 38.sp, textAlign = TextAlign.Center)
		Text("Profile", modifier = Modifier.weight(1f), color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
		Text("Edit", modifier = Modifier.clip(RoundedCornerShape(20.dp)).border(1.dp, Color.White, RoundedCornerShape(20.dp)).clickable(onClick = onEdit).padding(horizontal = 20.dp, vertical = 10.dp), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
	}
}

@Composable
private fun ProfileHeader() {
	Column(modifier = Modifier.fillMaxWidth().background(profileGreen).padding(horizontal = 30.dp, vertical = 15.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(19.dp)) {
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
			Box(modifier = Modifier.size(140.dp), contentAlignment = Alignment.Center) {
				AsyncImage(circleAsset, "Profile picture", Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
				Text("BE", color = profileMint, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
			}
			Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
				Text("<FirstN LastN>", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
				Text("University of Exeter", color = Color.White, fontSize = 15.sp)
				Text("Year 2", color = Color.White, fontSize = 15.sp)
				Text("<BSc>", color = Color.White, fontSize = 15.sp)
				Text("4.5  ★★★★☆  (10)", color = Color.White, fontSize = 15.sp)
			}
		}
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			VerificationChip("✓ Verified student")
			VerificationChip("✓ Verified driver")
		}
		Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).border(1.dp, Color.White, RoundedCornerShape(20.dp)).background(profileGreenLight).padding(horizontal = 20.dp, vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
			Stat("98%", "positive")
			Stat("10", "rides")
			Stat("saved\n350kg\nCO2")
		}
	}
}

@Composable
private fun VerificationChip(label: String) {
	Text(label, modifier = Modifier.border(1.dp, Color.White, RoundedCornerShape(20.dp)).padding(horizontal = 15.dp, vertical = 6.dp), color = Color.White, fontSize = 15.sp)
}

@Composable
private fun Stat(value: String, label: String = "") {
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)
		if (label.isNotEmpty()) Text(label, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)
	}
}

@Composable
private fun AboutSection() {
	ProfileSection {
		Text("ABOUT ME", color = profileGreen, fontSize = 20.sp, fontWeight = FontWeight.Bold)
		Text("I drive to London to visit my girlfriend on the first weekend of every month.\nI am a big fan of pop and indie music, so I hope you’re down to join in on my road trip karaoke!", color = profileGreen, fontSize = 15.sp, lineHeight = 21.sp)
	}
}

@Composable
private fun CarbonImpactSection() {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).clip(RoundedCornerShape(20.dp)).background(profileGreen).padding(15.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("YOUR CARBON IMPACT", color = profileMint, fontSize = 20.sp, fontWeight = FontWeight.Bold)
		Row(verticalAlignment = Alignment.Bottom) {
			Text("350", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
			Text(" kg CO2 saved", color = Color.White, fontSize = 15.sp, modifier = Modifier.padding(bottom = 4.dp))
		}
		Text("Equivalent to planting 15 trees", color = Color.White, fontSize = 15.sp)
	}
}

@Composable
private fun ReviewSection() {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("Recent reviews", color = profileGreen, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
		ProfileSection {
			Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
				Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
					AsyncImage(circleAsset, null, Modifier.fillMaxSize())
					Text("BE", color = profileGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
				}
				Spacer(Modifier.width(5.dp))
				Column(modifier = Modifier.weight(1f)) {
					Text("<FirstN>", color = profileGreen, fontSize = 15.sp, fontWeight = FontWeight.Bold)
					Text("<startC> → <endC>", color = profileGreen, fontSize = 14.sp)
				}
				Text("★★★★☆", color = profileGreen, fontSize = 20.sp)
			}
			Text("“<quote>”", color = profileGreen, fontSize = 15.sp)
		}
	}
}

@Composable
private fun ProfileSection(content: @Composable () -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).clip(RoundedCornerShape(20.dp)).border(1.dp, profileMint, RoundedCornerShape(20.dp)).background(Color.White).padding(horizontal = 15.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp), content = content)
}

@Composable
private fun ProfileBottomBar(onHome: () -> Unit, onSearch: () -> Unit, onAddRide: () -> Unit, onRides: () -> Unit) {
	Row(modifier = Modifier.fillMaxWidth().navigationBarsPadding().border(BorderStroke(1.dp, Color(0xFF1A9E52))).background(profileBackground).padding(top = 7.dp, bottom = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
		BottomItem(homeAsset, "Home", onHome)
		BottomItem(pinAsset, "Search", onSearch)
		Text("+", modifier = Modifier.size(50.dp).clip(CircleShape).background(profileGreen).clickable(onClick = onAddRide).padding(bottom = 5.dp), color = profileBackground, fontSize = 36.sp, textAlign = TextAlign.Center)
		BottomItem(null, "My Rides", onRides, showCar = true)
		BottomItem(null, "Profile", {})
	}
}

@Composable
private fun BottomItem(asset: String?, label: String, onClick: () -> Unit, showCar: Boolean = false) {
	Column(modifier = Modifier.clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
		if (showCar) {
			Box(Modifier.size(width = 42.dp, height = 21.dp)) {
				AsyncImage(carBackWheelAsset, null, Modifier.size(7.dp).align(Alignment.BottomEnd).padding(end = 6.dp))
				AsyncImage(carFrontWheelAsset, null, Modifier.size(7.dp).align(Alignment.BottomStart).padding(start = 7.dp))
				AsyncImage(carBodyAsset, label, Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
			}
		} else if (asset != null) AsyncImage(asset, label, Modifier.size(24.dp), contentScale = ContentScale.Fit)
		else Box(Modifier.size(24.dp)) {
			AsyncImage(profileBodyAsset, null, Modifier.size(20.dp).align(Alignment.BottomCenter))
			AsyncImage(profileHeadAsset, null, Modifier.size(9.dp).align(Alignment.TopCenter))
		}
		Text(label, color = profileGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
	}
}
