import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
@Composable
fun HomePage(
	modifier: Modifier = Modifier,
	onSearch: () -> Unit = {},
	onRideClick: (Ride) -> Unit = {},
	currentUser: User, // how to default to current user??
) {
	var selectedFilter by remember { mutableStateOf("All trips") }
	val searchedRides = remember { searchResults }

	LazyColumn(
		modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1),
		contentPadding = PaddingValues(bottom = 24.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		item {
			HomeHeader(onSearch = onSearch, currentUser = currentUser)
		}
		item {
			HomeSection(title = "Your upcoming rides") {
				RideList(rides = currentUser.rides.filter {ride.isUpcoming() == true})
			}
		}
		item {
			Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
				Text("Available rides", modifier = Modifier.padding(horizontal = 30.dp), color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
					listOf("All trips", "Planned", "Last-minute", "Female-only").forEach { filter ->
						FilterChip(filter, selectedFilter == filter) { selectedFilter = filter }
					}
				}
			}
		}
		item {
			RideList(rides = searchedRides) // onClick -> ride
		}
		item {
			CarbonImpactPanel()
		}
	}
}

private val searchResults = List<Rides> // this will contain actual rides later

@Composable
private fun HomeHeader(onSearch: () -> Unit, currentUser) {
	Column(
		modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground).padding(horizontal = 24.dp, vertical = 18.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			Column(modifier = Modifier.weight(1f)) {
				Text("Good morning!", color = Colours.DarkModeText, fontSize = TextFormatting.Text1.size, fontWeight = TextFormatting.Text1.weight)
				Text("Hey, ${currentUser.getFormattedFirstName()}", color = Colours.DarkModeText, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
			}
			currentUser.getProfilePic(theme = Theme.Dark)
		}
		Row(
			modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(15.dp)).background(Colours.DarkModePrimary).clickable(onClick = onSearch).padding(horizontal = 12.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			pinIcon()
			Spacer(Modifier.width(8.dp))
			Text("Where are you heading?", color = Colours.DarkModeText, fontSize = TextFormatting.SearchBox1.size, fontWeight = TextFormatting.SearchBox1.weight)
		}
	}
}

@Composable
private fun HomeSection(title: String, content: @Composable () -> Unit) {
	Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
		Text(title, modifier = Modifier.padding(horizontal = 30.dp), color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		content()
	}
}

@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
	Text(label, modifier = Modifier.clip(RoundedCornerShape(18.dp)).background(if (selected) Colours.LightModePrimary else Colours.LightModeBackground2).border(1.dp, if (selected) Colours.DarkModeBackground else Colours.LightModeSecondary, RoundedCornerShape(18.dp)).clickable(onClick = onClick).padding(horizontal = 10.dp, vertical = 7.dp), color = if (selected) Colours.DarkModeText else Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
}

@Composable
private fun CarbonImpactPanel(user) {
	Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("Your carbon impact", color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Column(modifier = Modifier.padding(horizontal = 30.dp).fillMaxWidth().background(Colours.DarkModeBackground, RoundedCornerShape(18.dp)).padding(15.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
			Text("LIVE CO2 TRACKER", color = Colours.LightModeSecondary, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
			Text("Our green journey so far...", color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				ImpactStat("${currentUser.carbonSaved}kg", "CO2 saved", Modifier.weight(1f))
				ImpactStat(currentUser.getNoOfRides(), "rides shared", Modifier.weight(1f))
			}
		}
	}
}

@Composable
private fun ImpactStat(value: String, label: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier.background(Colours.DarkModePrimary, RoundedCornerShape(15.dp)).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.DarkModeText, fontSize = TextFormatting.Figures1.size, fontWeight = TextFormatting.Figures1.weight)
		Text(label, color = Colours.DarkModeText, fontSize = TextFormatting.Figures2.size, fontWeight = TextFormatting.Figures2.weight)
	}
}
