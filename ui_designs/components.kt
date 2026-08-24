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



// Stage indicator components

@Composable
fun StageIndicator(modifier: Modifier = Modifier, currentStage: Int = 1, noStages: Int = 5, primaryColour: Color = Colours.LightModePrimary, secondaryColour: Color = Colours.LightModeSecondary) {
  Column(

    // determine position of current stage indicator based on int parameter
    noStages = noStages
    currentStage = currentStage,
    noStagesBefore = currentStage - 1,
    noStagesAfter = noStages - currentStage,

    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    androidx.compose.foundation.layout.Row(
      horizontalArrangement = Arrangement.spacedBy(5.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      repeat(noStagesBefore) {
        AsyncImage(
          model = IndividualStageIndicator(isCurrentStage = false, color = secondaryColour),
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      }
      AsyncImage(
          model = IndividualStageIndicator(isCurrentStage = true, color = primaryColour),
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      repeat(noStagesAfter) {
        AsyncImage(
          model = IndividualStageIndicator(isCurrentStage = false, color = secondaryColour),
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      }
    }
  }
}

@Composable
private fun IndividualStageIndicator(modifier: Modifier = Modifier, isCurrentStage: Boolean = false, colour: Color = Colours.LightModeSecondary) {
  if (isCurrentStage) {
    Box(
      modifier = Modifier
        .size(width = 25.dp, height = 10.dp)
        .background(color = primaryColour, shape = RoundedCornerShape(size = 20.dp)),
    )
  } else {
    Ellipse(
      modifer = Modifier
        .padding(1.dp)
        .size(width=10.dp, height=10.dp)
        .background(color = secondaryColour)
    )
  }
}

Object Variables { }