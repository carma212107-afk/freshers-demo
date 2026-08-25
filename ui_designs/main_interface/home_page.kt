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
	onRideClick: (HomeRide) -> Unit = {},
) {
	var selectedFilter by remember { mutableStateOf("All trips") }
	val rides = remember { sampleHomeRides }

	LazyColumn(
		modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1),
		contentPadding = PaddingValues(bottom = 24.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		item {
			HomeHeader(onSearch = onSearch)
		}
		item {
			HomeSection(title = "Your upcoming rides") {
				HomeRideCard(rides.first(), onClick = { onRideClick(rides.first()) })
			}
		}
		item {
			Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
				Text("Available rides", modifier = Modifier.padding(horizontal = 30.dp), color = Colours.LightModeText, fontSize = TextFormatting.Heading1.size, fontWeight = TextFormatting.Heading1.weight)
				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
					listOf("All trips", "Planned", "Last-minute", "Female-only").forEach { filter ->
						FilterChip(filter, selectedFilter == filter) { selectedFilter = filter }
					}
				}
			}
		}
		items(rides.drop(1)) { ride ->
				HomeRideCard(ride, onClick = { onRideClick(ride) }, modifier = Modifier.padding(horizontal = 49.dp))
		}
		item {
			CarbonImpactPanel()
		}
	}
}

data class HomeRide(
	val startCity: String,
	val endCity: String,
	val date: String,
	val time: String,
	val driverName: String,
	val university: String,
	val seatsLeft: Int,
	val rating: String,
)

private val sampleHomeRides = listOf(
	HomeRide("<startC>", "<endC>", "<date>", "HH:mm", "<FirstN>", "University of Exeter", 3, "4.50"),
	HomeRide("<startC>", "<endC>", "<date>", "HH:mm", "<FirstN>", "<uni>", 3, "4.50"),
	HomeRide("<startC>", "<endC>", "<date>", "HH:mm", "<FirstN>", "<uni>", 2, "4.50"),
	HomeRide("<startC>", "<endC>", "<date>", "HH:mm", "<FirstN>", "<uni>", 1, "4.50"),
)

@Composable
private fun HomeHeader(onSearch: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground).padding(horizontal = 24.dp, vertical = 18.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			Column(modifier = Modifier.weight(1f)) {
				Text("Good morning", color = Colours.DarkModeText, fontSize = TextFormatting.Text1.size)
				Text("Hey, <FirstN>", color = Colours.DarkModeText, fontSize = 30.sp, fontWeight = FontWeight.Black)
			}
			Box(modifier = Modifier.size(66.dp).background(Colours.Accent, CircleShape).border(1.dp, Colours.DarkModeSecondary, CircleShape), contentAlignment = Alignment.Center) {
				Text("BE", color = Colours.DarkModeSecondary, fontSize = 23.sp, fontWeight = FontWeight.Bold)
			}
		}
		Row(
			modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.DarkModeSecondary, RoundedCornerShape(15.dp)).clickable(onClick = onSearch).padding(horizontal = 12.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Icon(Icons.Filled.LocationOn, contentDescription = "Choose destination", tint = Colours.DarkModeSecondary, modifier = Modifier.size(20.dp))
			Spacer(Modifier.width(8.dp))
			Text("Where are you heading?", color = Colours.DarkModeSecondary, fontSize = TextFormatting.Text1.size)
		}
	}
}

@Composable
private fun HomeSection(title: String, content: @Composable () -> Unit) {
	Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
		Text(title, modifier = Modifier.padding(horizontal = 30.dp), color = Colours.LightModeText, fontSize = TextFormatting.Heading1.size, fontWeight = TextFormatting.Heading1.weight)
		content()
	}
}

@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
	Text(label, modifier = Modifier.clip(RoundedCornerShape(18.dp)).background(if (selected) Colours.LightModePrimary else Colours.LightModeBackground2).border(1.dp, Colours.LightModeSecondary, RoundedCornerShape(18.dp)).clickable(onClick = onClick).padding(horizontal = 10.dp, vertical = 7.dp), color = if (selected) Colours.LightModeBackground1 else Colours.LightModeText, fontSize = 14.sp)
}

@Composable
private fun HomeRideCard(ride: HomeRide, onClick: () -> Unit, modifier: Modifier = Modifier) {
	Column(modifier = modifier.fillMaxWidth().border(1.dp, Colours.Accent, RoundedCornerShape(17.dp)).background(Colours.LightModeBackground2, RoundedCornerShape(17.dp)).clickable(onClick = onClick).padding(horizontal = 12.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("${ride.startCity}  ->  ${ride.endCity}", color = Colours.LightModeText, fontSize = 24.sp, fontWeight = FontWeight.Black)
		Row(verticalAlignment = Alignment.CenterVertically) {
			ProfilePic(initials = "BE")
			Spacer(Modifier.width(6.dp))
			Column(modifier = Modifier.weight(1f)) {
				Text(ride.driverName, color = Colours.LightModeText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
				Text(ride.university, color = Colours.LightModeText, fontSize = 12.sp)
			}
			Text("* ${ride.rating}", color = Colours.LightModeText, fontSize = 14.sp)
		}
		Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
			CalendarIcon(Modifier.size(14.dp))
			Text(ride.date, color = Colours.LightModeText, fontSize = 12.sp)
			ClockIcon(Modifier.size(14.dp))
			Text(ride.time, color = Colours.LightModeText, fontSize = 12.sp)
			Spacer(Modifier.weight(1f))
			Text("${ride.seatsLeft} seats", color = Colours.LightModeText, fontSize = 12.sp)
		}
	}
}

@Composable
private fun CarbonImpactPanel() {
	Column(modifier = Modifier.padding(horizontal = 30.dp).fillMaxWidth().background(Colours.DarkModeBackground, RoundedCornerShape(18.dp)).padding(15.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("Your carbon impact", color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Text("LIVE CO2 TRACKER", color = Colours.DarkModeSecondary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
		Text("Our green journey so far...", color = Colours.DarkModeText, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
			ImpactStat("350", "kg CO2 saved", Modifier.weight(1f))
			ImpactStat("10", "rides shared", Modifier.weight(1f))
		}
	}
}

@Composable
private fun ImpactStat(value: String, label: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier.background(Colours.DarkModePrimary, RoundedCornerShape(15.dp)).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.DarkModeText, fontSize = 30.sp, fontWeight = FontWeight.Black)
		Text(label, color = Colours.DarkModeText, fontSize = 12.sp)
	}
}
