import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ViewRidePage(
	ride: Ride? = null,
	modifier: Modifier = Modifier,
	onBack: () -> Unit = {},
	onJoinRide: () -> Unit = {},
	onLeaveRide: () -> Unit = {},
	onHome: () -> Unit = {},
	onSearch: () -> Unit = {},
	onAddRide: () -> Unit = {},
	onMyRides: () -> Unit = {},
	onProfile: () -> Unit = {},
) {
	val driver = ride?.driver
	val seatsLeft = ride?.getNoOfFreeSeats()?.toString() ?: "2"
	val breakdown = ride?.getCostBreakdown() ?: listOf("£36", "£12", "+ £1.20", "≈£13.20")

	Column(modifier = modifier.fillMaxSize().background(Colours.LightMode.Background1)) {
		ViewRideTopBar(onBack = onBack)
		Column(
			modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 30.dp, vertical = 15.dp),
			verticalArrangement = Arrangement.spacedBy(24.dp),
		) {
			ViewRideSection("Journey") {
				ViewRideRoute(ride)
			}
			ViewRideSection("Trip info") {
				Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
					ViewRideInfoBox("125 mi", "distance", Modifier.weight(1f))
					ViewRideInfoBox("$seatsLeft left", "seats available", Modifier.weight(1f))
				}
			}
			ViewRideSection("Driver's car") {
				ViewRideCarCard(ride)
			}
			ViewRideSection("Driver preferences") {
				ViewRideFilterGrid()
			}
			ViewRideSection("Notes for passengers") {
				Text(
					"Notes",
					modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.InputField.size,
					fontWeight = TextFormatting.InputField.weight,
				)
			}
			ViewRideCostBreakdown(breakdown)
			Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
				ViewRideActionButton("Join ride", filled = true, onClick = onJoinRide)
				ViewRideActionButton("Leave ride", filled = false, onClick = onLeaveRide)
			}
		}
		ViewRideBottomBar(onHome, onSearch, onAddRide, onMyRides, onProfile)
	}
}

@Composable
private fun ViewRideTopBar(onBack: () -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth().background(Colours.DarkMode.Background1).padding(start = 15.dp, top = 60.dp, end = 15.dp, bottom = 15.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(15.dp),
	) {
		ViewRideBackButton(onClick = onBack)
		Text("Ride details", color = Colours.DarkMode.Text, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
	}
}

@Composable
private fun ViewRideBackButton(onClick: () -> Unit) {
	Box(
		modifier = Modifier.size(40.dp).background(Colours.DarkMode.Background2, CircleShape).clickable(onClick = onClick),
		contentAlignment = Alignment.Center,
	) {
		Canvas(Modifier.size(22.dp)) {
			drawLine(Colours.DarkMode.Primary, Offset(15f, 3f), Offset(7f, 11f), 2.5f, StrokeCap.Round)
			drawLine(Colours.DarkMode.Primary, Offset(7f, 11f), Offset(15f, 19f), 2.5f, StrokeCap.Round)
		}
	}
}

@Composable
private fun ViewRideSection(title: String, content: @Composable () -> Unit) {
	Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text(title, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		content()
	}
}

@Composable
private fun ViewRideRoute(ride: Ride?) {
	val start = ride?.startCity ?: "<startC>"
	val end = ride?.endCity ?: "<endC>"
	val departure = ride?.formatTime() ?: "HH:mm"
	val duration = ride?.calculateDuration()?.ifBlank { "HH:mm" } ?: "HH:mm"

	Row(
		modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Column(modifier = Modifier.width(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
			ViewRideRouteMarker(Colours.Accent)
			Box(Modifier.width(1.dp).height(20.dp).background(Colours.LightMode.Primary))
			ViewRideRouteMarker(Colours.LightMode.Primary)
		}
		Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(0.dp)) {
			Text("Departing from", color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			Text(start, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
			Spacer(Modifier.height(20.dp))
			Text("Arriving at", color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			Text(end, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		}
		Column(horizontalAlignment = Alignment.End) {
			Text("Time", color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			Text(departure, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
			Spacer(Modifier.height(20.dp))
			Text("Est. duration", color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			Text(duration, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		}
	}
}

@Composable
private fun ViewRideRouteMarker(color: Color) {
	Canvas(Modifier.size(15.dp)) { drawCircle(color, radius = size.minDimension / 2f) }
}

@Composable
private fun ViewRideInfoBox(value: String, label: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier.background(Colours.LightMode.Secondary, RoundedCornerShape(5.dp)).padding(horizontal = 10.dp, vertical = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Text(label, color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight, textAlign = TextAlign.Center)
	}
}

@Composable
private fun ViewRideCarCard(ride: Ride?) {
	val car = ride?.car
	val make = car?.makeModel?.getOrNull(0) ?: "<make>"
	val model = car?.makeModel?.getOrNull(1) ?: "<model>"
	Column(
		modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 10.dp, vertical = 5.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text("$make $model", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
		Text("Car Reg. hidden until 24hrs before departure", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight, textAlign = TextAlign.Center)
	}
}

@Composable
private fun ViewRideFilterGrid() {
	Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			ViewRideFilter("<filter>")
			ViewRideFilter("<filter>")
		}
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			ViewRideFilter("<filter>")
			ViewRideFilter("<filter>")
		}
	}
}

@Composable
private fun ViewRideFilter(label: String) {
	Text(label, modifier = Modifier.background(Colours.LightMode.Secondary, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 3.dp), color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
}

@Composable
private fun ViewRideCostBreakdown(breakdown: List<String>) {
	Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("Cost breakdown", color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Column(modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(20.dp)).padding(horizontal = 15.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
			ViewRideCostRow("Fuel cost (125 mi x 0 mi/gal)", breakdown.getOrElse(0) { "£36" })
			ViewRideCostRow("Split between 1 passengers", breakdown.getOrElse(1) { "£12" })
			ViewRideCostRow("Carma fee (10%)", breakdown.getOrElse(2) { "+ £1.20" })
			Box(Modifier.fillMaxWidth().height(1.dp).background(Colours.Accent))
			ViewRideCostRow("Your total", breakdown.getOrElse(3) { "≈£13.20" }, total = true)
		}
	}
}

@Composable
private fun ViewRideCostRow(label: String, value: String, total: Boolean = false) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
		Text(label, color = Colours.LightMode.Text, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
		Text(value, color = Colours.LightMode.Text, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
	}
}

@Composable
private fun ViewRideActionButton(label: String, filled: Boolean, onClick: () -> Unit) {
	val background = if (filled) Colours.DarkMode.Background1 else Colours.LightMode.Background2
	val border = if (filled) background else Colours.LightMode.Primary
	val text = if (filled) Colours.DarkMode.Text else Colours.LightMode.Text
	Box(modifier = Modifier.fillMaxWidth().border(1.dp, border, RoundedCornerShape(20.dp)).background(background, RoundedCornerShape(20.dp)).clickable(onClick = onClick).padding(horizontal = 15.dp, vertical = 10.dp), contentAlignment = Alignment.Center) {
		Text(label, color = text, fontSize = TextFormatting.Button1.size, fontWeight = if (filled) TextFormatting.Button1.weight else TextFormatting.Button2.weight)
	}
}

@Composable
private fun ViewRideBottomBar(onHome: () -> Unit, onSearch: () -> Unit, onAddRide: () -> Unit, onMyRides: () -> Unit, onProfile: () -> Unit) {
	Row(modifier = Modifier.fillMaxWidth().border(1.dp, Colours.Accent).background(Colours.LightMode.Background1).padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
		ViewRideNavItem("⌂", "Home", onHome)
		ViewRideNavItem("⌖", "Search", onSearch)
		Box(modifier = Modifier.size(50.dp).background(Colours.LightMode.Primary, CircleShape).clickable(onClick = onAddRide), contentAlignment = Alignment.Center) {
			Text("+", color = Colours.LightMode.Background1, fontSize = TextFormatting.IntroTitle.size, textAlign = TextAlign.Center)
		}
		ViewRideNavItem("▱", "My Rides", onMyRides)
		ViewRideNavItem("○", "Profile", onProfile)
	}
}

@Composable
private fun ViewRideNavItem(icon: String, label: String, onClick: () -> Unit) {
	Column(modifier = Modifier.size(width = 50.dp, height = 50.dp).clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
		Text(icon, color = Colours.LightMode.Primary, fontSize = TextFormatting.Heading2.size)
		Text(label, color = Colours.LightMode.Primary, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight, textAlign = TextAlign.Center)
	}
}
