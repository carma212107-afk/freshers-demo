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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Sort
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

@Composable
fun SearchPage(
	onBack: () -> Unit = {},
	onPreferences: () -> Unit = {},
	onRideClick: (SearchRide) -> Unit = {},
	modifier: Modifier = Modifier,
) {
	var selectedFilter by remember { mutableStateOf("All trips") }
	var selectedDay by remember { mutableStateOf("Today") }
	val rides = remember { sampleSearchRides }

	LazyColumn(
		modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1),
		contentPadding = PaddingValues(bottom = 20.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
	) {
		item { SearchHeader(onBack, onPreferences, selectedDay, onDaySelected = { selectedDay = it }) }
		item {
			Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp), verticalAlignment = Alignment.CenterVertically) {
				Text("${rides.size} rides", color = Colours.LightModeText, fontSize = TextFormatting.Text2.size, fontWeight = FontWeight.Bold)
				Text(" found for <dd D Mon>", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size)
				Spacer(Modifier.weight(1f))
				Icon(Icons.Filled.Sort, contentDescription = "Sort rides", tint = Colours.LightModePrimary, modifier = Modifier.size(16.dp))
				Text(" Sort", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size)
			}
		}
		item {
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				listOf("All trips", "Planned", "Last-minute", "Female-only").forEach { filter ->
					SearchFilterChip(filter, selectedFilter == filter) { selectedFilter = filter }
				}
			}
		}
		items(rides) { ride ->
			SearchRideCard(ride, onClick = { onRideClick(ride) }, modifier = Modifier.padding(horizontal = 30.dp))
		}
	}
}

data class SearchRide(
	val startCity: String,
	val endCity: String,
	val date: String,
	val time: String,
	val price: String,
	val driverName: String,
	val university: String,
	val yearCourse: String,
	val rating: String,
	val seatsLeft: Int,
)

private val sampleSearchRides = listOf(
	SearchRide("<startC>", "<endC>", "<date>", "HH:mm", "~£13.20", "<FirstN>", "University of Exeter", "Year 2 • <BSc>", "4.50", 2),
	SearchRide("<startC>", "<endC>", "<date>", "HH:mm", "~£13.20", "<FirstN>", "University of Exeter", "Year 2 • <BSc>", "4.50", 2),
	SearchRide("<startC>", "<endC>", "<date>", "HH:mm", "~£13.20", "<FirstN>", "University of Exeter", "Year 2 • <BSc>", "4.50", 2),
)

@Composable
private fun SearchHeader(onBack: () -> Unit, onPreferences: () -> Unit, selectedDay: String, onDaySelected: (String) -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground).padding(horizontal = 15.dp, vertical = 18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Colours.DarkModeText, modifier = Modifier.size(40.dp).clickable(onClick = onBack).padding(8.dp))
			Text("Find a Ride", modifier = Modifier.weight(1f).padding(start = 12.dp), color = Colours.DarkModeText, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
			Text("Pref.", modifier = Modifier.border(1.dp, Colours.DarkModeText, RoundedCornerShape(20.dp)).clickable(onClick = onPreferences).padding(horizontal = 18.dp, vertical = 10.dp), color = Colours.DarkModeText, fontSize = TextFormatting.Button1.size, fontWeight = FontWeight.Bold)
		}
		SearchLocationField("From", "City")
		SearchLocationField("To", "City")
		Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
			listOf("Today", "Tomorrow").forEach { day ->
				Text(day, modifier = Modifier.border(1.dp, Colours.DarkModeText, RoundedCornerShape(20.dp)).clickable { onDaySelected(day) }.padding(horizontal = 18.dp, vertical = 9.dp), color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = if (selectedDay == day) FontWeight.Bold else FontWeight.Normal)
			}
			Box(modifier = Modifier.size(44.dp).border(1.dp, Colours.DarkModeText, RoundedCornerShape(15.dp)).padding(10.dp)) { Icon(Icons.Filled.CalendarMonth, contentDescription = "Choose date", tint = Colours.DarkModeText) }
		}
	}
}

@Composable
private fun SearchLocationField(label: String, placeholder: String) {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
		Text(label, color = Colours.DarkModeText, fontSize = TextFormatting.Text1.size)
		Row(modifier = Modifier.fillMaxWidth().height(38.dp).border(1.dp, Colours.DarkModeText, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
			Icon(Icons.Filled.LocationOn, contentDescription = null, tint = Colours.DarkModeText, modifier = Modifier.size(18.dp))
			Spacer(Modifier.width(8.dp))
			Text(placeholder, color = Colours.DarkModeText, fontSize = TextFormatting.Text1.size)
		}
	}
}

@Composable
private fun SearchFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
	Text(label, modifier = Modifier.border(1.dp, Colours.LightModeSecondary, RoundedCornerShape(18.dp)).background(if (selected) Colours.LightModePrimary else Colours.LightModeBackground2, RoundedCornerShape(18.dp)).clickable(onClick = onClick).padding(horizontal = 10.dp, vertical = 7.dp), color = if (selected) Colours.LightModeBackground1 else Colours.LightModeText, fontSize = 14.sp)
}

@Composable
private fun SearchRideCard(ride: SearchRide, onClick: () -> Unit, modifier: Modifier = Modifier) {
	Column(modifier = modifier.fillMaxWidth().border(1.dp, Colours.Accent, RoundedCornerShape(17.dp)).clickable(onClick = onClick).padding(horizontal = 14.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Text("${ride.startCity}  ", color = Colours.LightModeText, fontSize = 23.sp, fontWeight = FontWeight.Black)
			Text("->", color = Colours.Accent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
			Text("  ${ride.endCity}", color = Colours.LightModeText, fontSize = 23.sp, fontWeight = FontWeight.Black)
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			CalendarIcon(Modifier.size(14.dp)); Text(ride.date, color = Colours.LightModeText, fontSize = 12.sp)
			Spacer(Modifier.width(8.dp)); ClockIcon(Modifier.size(14.dp)); Text(ride.time, color = Colours.LightModeText, fontSize = 12.sp)
			Spacer(Modifier.weight(1f)); Text(ride.price, color = Colours.LightModeText, fontSize = 19.sp, fontWeight = FontWeight.Bold); Text("pp", color = Colours.LightModeText, fontSize = 11.sp)
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			ProfilePic(initials = "BE", modifier = Modifier.size(64.dp))
			Spacer(Modifier.width(10.dp))
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				Text(ride.driverName, color = Colours.LightModeText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
				Text(ride.university, color = Colours.LightModeText, fontSize = 12.sp)
				Text(ride.yearCourse, color = Colours.LightModeText, fontSize = 12.sp)
			}
			Text("* ${ride.rating}", color = Colours.LightModeText, fontSize = 14.sp)
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			Text("■▢▢ ${ride.seatsLeft} seats left", color = Colours.LightModeText, fontSize = 12.sp)
			Spacer(Modifier.weight(1f))
			Text("<filter>", modifier = Modifier.background(Colours.LightModeSecondary, RoundedCornerShape(15.dp)).padding(horizontal = 12.dp, vertical = 5.dp), color = Colours.LightModeText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
		}
	}
}
