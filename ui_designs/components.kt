import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

enum class Theme { Light, Dark }

private enum class MainScreen { Home, Search, PostRide, MyRides, Profile }

@Composable
fun MainInterface(modifier: Modifier = Modifier) {
  var currentScreen by remember { mutableStateOf(MainScreen.Profile) }

  Column(modifier = modifier.fillMaxSize()) {
    when (currentScreen) {
      MainScreen.Home -> HomePage(modifier = Modifier.weight(1f))
      MainScreen.Search -> SearchPage(modifier = Modifier.weight(1f))
      MainScreen.PostRide -> PostRidePage(modifier = Modifier.weight(1f))
      MainScreen.MyRides -> MyRidesPage(modifier = Modifier.weight(1f))
      MainScreen.Profile -> MyProfileExamplePage(modifier = Modifier.weight(1f))
    }
    BottomNavigationBar(
      onHome = { currentScreen = MainScreen.Home },
      onSearch = { currentScreen = MainScreen.Search },
      onAddRide = { currentScreen = MainScreen.PostRide },
      onMyRides = { currentScreen = MainScreen.MyRides },
      onProfile = { currentScreen = MainScreen.Profile },
    )
  }
}


@Composable
private fun StatusBar(modifier: Modifier = Modifier, theme: Theme = Theme.Light) {
  const val timeAsset = "https://www.figma.com/api/mcp/asset/a3121e18-4222-405c-9eca-d748c5f06502.svg"
  const val rightSideAsset = "https://www.figma.com/api/mcp/asset/3042e646-380b-478e-a3ec-48dbd73ad777.svg"

  if (theme == Theme.Light) { colour = Color(0xFF0A5C2E) }
  else if (theme == Theme.Dark) { colour = Color(0xFFFFFFFF) }
  
  Box( // Status bar & Home indicator
    modifier = modifier
      .fillMaxSize()
      .background(colour),
  ) {
    Row(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .fillMaxWidth()
        .padding(horizontal = 30.dp, vertical = 20.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      AsyncImage(
        model = timeAsset,
        contentDescription = "Current time",
        modifier = Modifier.size(width = 54.dp, height = 21.dp),
      )
      AsyncImage(
        model = rightSideAsset,
        contentDescription = "Signal, Wi-Fi, and battery status",
        modifier = Modifier.size(width = 67.dp, height = 12.dp),
      )
    }
  }
}


// BottomNavigationBar component

@Composable
fun BottomNavigationBar(onHome: () -> Unit, onSearch: () -> Unit, onAddRide: () -> Unit, onMyRides: () -> Unit, onProfile: () -> Unit) {
	Row(modifier = Modifier.fillMaxWidth().navigationBarsPadding().border(BorderStroke(1.dp, Colours.Accent)).background(Colours.LightModeBackground1).padding(top = 7.dp, bottom = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
		BottomNavButton(homeIcon, "Home", onHome)
		BottomNavButton(pinIcon, "Search", onSearch)
		Text("+", modifier = Modifier.size(50.dp).clip(CircleShape).background(Colours.LightModePrimary).clickable(onClick = onAddRide).padding(bottom = 5.dp), color = Colours.LightModeBackground1, fontSize = 36.sp, textAlign = TextAlign.Center)
		BottomNavButton(carIcon(), "My Rides", onMyRides)
		BottomNavButton(profileIcon(), "Profile", onProfile)
	}
}

@Composable
private fun BottomNavButton(asset: String?, label: String, onClick: () -> Unit) {
	Column(modifier = Modifier.clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
		if (asset != null) AsyncImage(asset, label, Modifier.size(24.dp), contentScale = ContentScale.Fit)
		Text(label, color = Colours.LightModePrimary, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
	}
}


// TopMenuBar component

@Composable
private fun BackButton(onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
	Box(
		modifier = modifier.size(40.dp).background(Colours.DarkModePrimary, CircleShape)
			.border(1.dp, Colours.DarkModeBorder, CircleShape).clickable(onClick = onClick),
		contentAlignment = Alignment.Center,
	) {
		Canvas(Modifier.size(22.dp)) {
			drawLine(Colours.DarkModeSecondary, androidx.compose.ui.geometry.Offset(15f, 3f), androidx.compose.ui.geometry.Offset(7f, 11f), 2.5f, StrokeCap.Round)
			drawLine(Colours.DarkModeSecondary, androidx.compose.ui.geometry.Offset(7f, 11f), androidx.compose.ui.geometry.Offset(15f, 19f), 2.5f, StrokeCap.Round)
		}
	}
}
enum class MenuBarButton(
  val text: String
  val modifier: Modifier
  val colour: Color
) {}
fun TopMenuBar(title: String, description: String = null, rightButton: MenuBarButton = null, theme: Theme = Theme.Dark) {
	val backgroundColour = when (theme) {
		Light -> Colours.LightModeBackground1
		Dark -> Colours.DarkModeBackground
	}
	val textColour = when (theme) {
		Light -> Colours.LightModeText
		Dark -> Colours.DarkModeText
	}

	Row(
		modifier = Modifier.fillMaxWidth().background(backgroundColour).padding(start = 15.dp, end = 15.dp, top = 60.dp, bottom = 15.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(15.dp)
	) {
		BackButton(onClick = onBack, theme = theme)
		Column {
			Text(title, color = textColour, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
			if (description != null) { Text(description, color = textColour, fontSize = TextFormatting.Text1.size, fontWeight = TextFormatting.Text1.weight) }
		}
		if (rightButton != null) {
			Text(rightButton.text, modifier = rightButton.modifier, color = textColour, fontsize = TextFormatting.Button1.size, fontWeight = TextFormatting.Button1.weight, textAlign = TextAlign.Center)
		}
	}
}


Object Variables { }