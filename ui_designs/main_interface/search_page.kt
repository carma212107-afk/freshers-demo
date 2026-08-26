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
	onRideClick: (Ride) -> Unit = {},
	modifier: Modifier = Modifier,
) {
	var selectedFilter by remember { mutableStateOf("All trips") }
	var selectedDay by remember { mutableStateOf("Today") }
	val rides = remember { searchResults }

	LazyColumn(
		modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1),
		contentPadding = PaddingValues(bottom = 20.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
	) {
		item { SearchHeader(onBack, onPreferences, selectedDay, onDaySelected = { selectedDay = it }) }
		item {
			Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp), verticalAlignment = Alignment.CenterVertically) {
				Text("${rides.size} rides", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text2.weight)
				Text(" found for ${selectedDay}", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
				Spacer(Modifier.weight(1f))
				Icon(Icons.Filled.Sort, contentDescription = "Sort rides", tint = Colours.LightModeText, modifier = Modifier.size(16.dp))
				Text(" Sort", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			}
		}
		item {
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				listOf("All trips", "Planned", "Last-minute", "Female-only").forEach { filter ->
					SearchFilterChip(filter, selectedFilter == filter) { selectedFilter = filter }
				}
			}
		}
		item { RideList(rides = rides, searchResults = true) }
	}
}

private val searchResults = List<Rides> // this will contain actual rides later

@Composable
private fun SearchHeader(onBack: () -> Unit, onPreferences: () -> Unit, selectedDay: String, onDaySelected: (String) -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground1).padding(horizontal = 15.dp, vertical = 18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
		TopMenuBar("Find a Ride", rightButton = MenuBarButton("Pref."))
		Row(modifer = Modifer.fillMaxWidth().border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(15.dp)).background(Colours.DarkModeBackground2), verticalArrangement = Arrangement.spacedBy(10.dp)) {
			Column(modifer = Modifer.fillMaxHeight()) {
				SearchLocationField("From", "City")
				Spacer(Modifier.weight(1f))
				SearchLocationField("To", "City")
			}
			Column(modifer = Modifer.fillMaxHeight()) {
				Spacer(Modifier.weight(1f))
				RouteMarker(colour = Colour.Accent)
				RouteMarker(colour = Colour.DarkModePrimary, line = true)
				RouteMarker(colour = Colour.DarkModePrimary)
			}
		}
		Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
			listOf("Today", "Tomorrow").forEach { day ->
				Text(day, modifier = Modifier.border(1.dp, Colours.DarkModeText, RoundedCornerShape(20.dp)).clickable { onDaySelected(day) }.padding(horizontal = 18.dp, vertical = 9.dp), color = Colours.DarkModeText, fontSize = TextFormatting.SearchBox2.size, fontWeight = if (selectedDay == day) FontWeight.Bold else TextFormatting.SearchBox2.weight)
			}
			Box(modifier = Modifier.size(44.dp).border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(15.dp)).padding(10.dp)) { Icon(Icons.Filled.CalendarMonth, contentDescription = "Choose date", tint = Colours.DarkModeText) }
		}
	}
}

@Composable
private fun SearchLocationField(label: String, placeholder: String) {
	Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
		Text(label, color = Colours.DarkModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
		Row(modifier = Modifier.fillMaxWidth().height(38.dp).border(1.dp, Colours.DarkModeBorder, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
			pinIcon()
			Spacer(Modifier.width(8.dp))
			Text(placeholder, color = Colours.DarkModeText, fontSize = TextFormatting.SearchBox1.size, fontWeight = TextFormatting.SearchBox1.weight)
		}
	}
}

@Composable
private fun SearchFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
	Text(label, modifier = Modifier.border(1.dp, Colours.LightModeSecondary, RoundedCornerShape(18.dp)).background(if (selected) Colours.LightModePrimary else Colours.LightModeBackground2, RoundedCornerShape(18.dp)).clickable(onClick = onClick).padding(horizontal = 10.dp, vertical = 7.dp), color = if (selected) Colours.LightModeBackground1 else Colours.LightModeText, fontSize = 14.sp)
}
