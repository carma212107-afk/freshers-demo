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
	val rides = remember { listOfRides }

	Column(modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1)) {
		MyRidesHeader(query = query, onQueryChange = { query = it }, onBack = onBack)
		MyRidesTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
		LazyColumn(
			modifier = Modifier.weight(1f),
			contentPadding = PaddingValues(top = 14.dp, bottom = 20.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp),
		) {
			item {
				RideList(rides = rides.filter {
					( ride -> query.isBlank() || ride.startCity.contains(query, true) || ride.endCity.contains(query, true) )
					&& ride.isUpcoming == (selectedTab == MyRidesTab.Upcoming)
				}
			}
		}
	}
}

private enum class MyRidesTab { Upcoming, Past }

private val listOfRides = List<Rides> // this will contain actual rides later

@Composable
private fun MyRidesHeader(query: String, onQueryChange: (String) -> Unit, onBack: () -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground1).padding(start = 15.dp, end = 15.dp, top = 58.dp, bottom = 15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
		TopMenuBar("My Rides", description = "See all rides, past and future")
		Row(modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.DarkModeText, RoundedCornerShape(15.dp)).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
			SearchIcon()
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
	val background = if (selected) Colours.Buttons.Selected.background else Colours.Buttons.Unselected.background
	val border = if (selected) Colours.Buttons.Selected.border else Colours.Buttons.Unselected.border
	val textC = if (selected) Colours.Buttons.Selected.text else Colours.Buttons.Unselected.text
	val textS = if (selected) TextFormatting.Button1.size else TextFormatting.Button2.size
	val textW = if (selected) TextFormatting.Button1.weight else TextFormatting.Button2.weight
	Box(modifier = Modifier.weight(1f).height(46.dp).border(1.dp, Colours.Accent, RoundedCornerShape(15.dp)).background(background, RoundedCornerShape(15.dp)).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
		Text(label, color = textC, fontSize = textS, fontWeight = textW)
	}
}