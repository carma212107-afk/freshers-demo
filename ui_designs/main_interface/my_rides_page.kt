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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyRidesPage(
	onBack: () -> Unit = {},
	onRideClick: (MyRide) -> Unit = {},
	modifier: Modifier = Modifier,
) {
	var selectedTab by remember { mutableStateOf(MyRidesTab.Upcoming) }
	var query by remember { mutableStateOf("") }
	val rides = remember { sampleMyRides }

	Column(modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1)) {
		MyRidesHeader(query = query, onQueryChange = { query = it }, onBack = onBack)
		MyRidesTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
		LazyColumn(
			modifier = Modifier.weight(1f),
			contentPadding = PaddingValues(top = 14.dp, bottom = 20.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp),
		) {
			items(rides.filter { ride -> query.isBlank() || ride.startCity.contains(query, true) || ride.endCity.contains(query, true) }) { ride ->
				MyRideCard(ride = ride, onClick = { onRideClick(ride) }, modifier = Modifier.padding(horizontal = 30.dp))
			}
		}
	}
}

private enum class MyRidesTab { Upcoming, Past }

data class MyRide(
	val startCity: String,
	val endCity: String,
	val date: String,
	val time: String,
	val driverName: String,
	val university: String,
	val car: String,
	val rating: String,
	val seatsLeft: Int,
)

private val sampleMyRides = listOf(
	MyRide("<startC>", "<endC>", "<date>", "HH:mm", "<FirstN>", "University of Exeter", "<make> <model> - <AB12 CDE>", "4.50", 3),
	MyRide("<startC>", "<endC>", "<date>", "HH:mm", "<FirstN>", "University of Exeter", "<make> <model> - <AB12 CDE>", "4.50", 3),
)

@Composable
private fun MyRidesHeader(query: String, onQueryChange: (String) -> Unit, onBack: () -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground).padding(start = 15.dp, end = 15.dp, top = 58.dp, bottom = 15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Colours.DarkModeText, modifier = Modifier.size(40.dp).clickable(onClick = onBack).padding(7.dp))
			Column(modifier = Modifier.padding(start = 14.dp)) {
				Text("My Rides", color = Colours.DarkModeText, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
				Text("See all rides, past and future", color = Colours.DarkModeText, fontSize = TextFormatting.Text1.size)
			}
		}
		Row(modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.DarkModeText, RoundedCornerShape(15.dp)).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
			Icon(Icons.Filled.Search, contentDescription = "Search rides", tint = Colours.DarkModeText, modifier = Modifier.size(19.dp))
			Spacer(Modifier.width(8.dp))
			Text(query.ifBlank { "Search for a ride" }, color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size)
		}
	}
}

@Composable
private fun MyRidesTabs(selectedTab: MyRidesTab, onTabSelected: (MyRidesTab) -> Unit) {
	Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 28.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
		RideTab("Upcoming", selectedTab == MyRidesTab.Upcoming) { onTabSelected(MyRidesTab.Upcoming) }
		RideTab("Past", selectedTab == MyRidesTab.Past) { onTabSelected(MyRidesTab.Past) }
	}
}

@Composable
private fun RideTab(label: String, selected: Boolean, onClick: () -> Unit) {
	Box(modifier = Modifier.weight(1f).height(46.dp).border(1.dp, Colours.Accent, RoundedCornerShape(15.dp)).background(if (selected) Colours.LightModeSecondary else Colours.LightModeBackground2, RoundedCornerShape(15.dp)).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
		Text(label, color = if (selected) Colours.LightModePrimary else Colours.Accent, fontSize = TextFormatting.Text2.size, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
	}
}

@Composable
private fun MyRideCard(ride: MyRide, onClick: () -> Unit, modifier: Modifier = Modifier) {
	Column(modifier = modifier.fillMaxWidth().border(1.dp, Colours.Accent, RoundedCornerShape(17.dp)).background(Colours.LightModeBackground2, RoundedCornerShape(17.dp)).clickable(onClick = onClick).padding(horizontal = 14.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("${ride.startCity}  ->  ${ride.endCity}", color = Colours.LightModeText, fontSize = 24.sp, fontWeight = FontWeight.Black)
		Row(verticalAlignment = Alignment.CenterVertically) {
			ProfilePic(initials = "BE", modifier = Modifier.size(40.dp))
			Spacer(Modifier.width(6.dp))
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				Text(ride.driverName, color = Colours.LightModeText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
				Text(ride.university, color = Colours.LightModeText, fontSize = 12.sp)
				Text(ride.car, color = Colours.LightModeText, fontSize = 12.sp)
			}
			Column(horizontalAlignment = Alignment.End) {
				Text("* ${ride.rating}", color = Colours.LightModeText, fontSize = 14.sp)
				Text("- - -", color = Colours.LightModeText, fontSize = 13.sp)
			}
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			Icon(Icons.Filled.CalendarMonth, contentDescription = "Date", tint = Colours.LightModePrimary, modifier = Modifier.size(14.dp))
			Text(ride.date, color = Colours.LightModeText, fontSize = 12.sp)
			Spacer(Modifier.width(8.dp))
			Icon(Icons.Filled.Clock, contentDescription = "Time", tint = Colours.LightModePrimary, modifier = Modifier.size(14.dp))
			Text(ride.time, color = Colours.LightModeText, fontSize = 12.sp)
			Spacer(Modifier.weight(1f))
			Text("<filter>", modifier = Modifier.background(Colours.LightModeSecondary, RoundedCornerShape(15.dp)).padding(horizontal = 12.dp, vertical = 5.dp), color = Colours.LightModeText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
		}
	}
}
