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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PostRidePage(
    modifier: Modifier = Modifier,
    ride: Ride? = null,
    onBack: () -> Unit = {},
    onPostRide: () -> Unit = {},
    onDiscard: () -> Unit = {},
    onHome: () -> Unit = {},
    onSearch: () -> Unit = {},
    onAddRide: () -> Unit = {},
    onMyRides: () -> Unit = {},
    onProfile: () -> Unit = {},
) {
    var femaleOnly by remember { mutableStateOf(true) }
    var selectedSeats by remember { mutableStateOf(3) }

    Column(
        modifier = modifier.fillMaxSize().background(Colours.LightMode.Background1),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Colours.DarkMode.Background1).padding(horizontal = 15.dp, vertical = 15.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                BackButton(onClick = onBack)
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Post Ride", color = Color.White, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
                    Text("Fill in your journey details below", color = Color.White, fontSize = TextFormatting.Text1.size, fontWeight = TextFormatting.Text1.weight)
                }
            }
        }

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 30.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            FormSection("Your route") {
                RoutePreview(
                    startCity = ride?.startCity ?: "<startC>",
                    endCity = ride?.endCity ?: "<endC>",
                    duration = ride?.calculateDuration() ?: "HH:mm",
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DateTimeField("Date", "<dd D Mon>", Modifier.weight(1f), date = true)
                DateTimeField("Time", "<12hr time>", Modifier.weight(1f), date = false)
            }

            FormSection("Trip type") {
                Row(
                    modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("Female-only ride", color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
                        Text("Only female passengers can book", color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
                    }
                    ToggleSwitch(switchedOn = femaleOnly, onClick = { femaleOnly = !femaleOnly })
                }
            }

            FormSection("Car type") {
                SelectorField("Select car")
            }

            FormSection("Available seats") {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    (1..4).forEach { seat ->
                        SeatButton(seat, selected = seat == selectedSeats) { selectedSeats = seat }
                    }
                }
            }

            FormSection("Your preferences") {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        PreferenceChip("<filter>")
                        PreferenceChip("<filter>")
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        PreferenceChip("<filter>")
                        PreferenceChip("<filter>")
                    }
                }
            }

            FormSection("Notes for passengers") {
                InputField("Notes")
            }

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                ActionButton("Post Ride", filled = true, onClick = onPostRide)
                ActionButton("Discard", filled = false, onClick = onDiscard)
            }
        }

        PostRideBottomBar(
            onHome = onHome,
            onSearch = onSearch,
            onAddRide = onAddRide,
            onMyRides = onMyRides,
            onProfile = onProfile,
        )
    }
}

@Composable
private fun FormSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
        content()
    }
}

@Composable
private fun RoutePreview(startCity: String, endCity: String, duration: String) {
    Row(
        modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(0.dp)) {
            RouteMarker(colour = Colours.Accent)
            Box(Modifier.width(1.dp).height(25.dp).background(Colours.LightMode.Primary))
            RouteMarker(colour = Colours.LightMode.Primary)
        }
        Column(modifier = Modifier.weight(1f).padding(start = 10.dp), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text("Departing from", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight)
            Text(startCity, color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
            Spacer(Modifier.height(8.dp))
            Text("Arriving at", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight)
            Text(endCity, color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
        }
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Spacer(Modifier.height(20.dp))
            Text("Est. duration", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight)
            Text(duration, color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
        }
    }
}

@Composable
private fun DateTimeField(label: String, value: String, modifier: Modifier = Modifier, date: Boolean) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(label, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.LightMode.Border, RoundedCornerShape(8.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(8.dp)).padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(value, modifier = Modifier.weight(1f), color = Colours.LightMode.InputText, fontSize = TextFormatting.InputField.size, fontWeight = TextFormatting.InputField.weight)
            if (date) CalendarIcon(Modifier.size(17.dp)) else ClockIcon(Modifier.size(17.dp))
        }
    }
}

@Composable
private fun SelectorField(value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.LightMode.Border, RoundedCornerShape(8.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(8.dp)).padding(start = 12.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(value, modifier = Modifier.weight(1f), color = Colours.LightMode.InputText, fontSize = TextFormatting.InputField.size, fontWeight = TextFormatting.InputField.weight)
        Icon(Icons.Filled.ExpandMore, contentDescription = "Select car", tint = Colours.LightMode.Text)
    }
}

@Composable
private fun SeatButton(seat: Int, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(width = 30.dp, height = 25.dp).background(if (selected) Colours.LightMode.Primary else Colours.LightMode.Background2, RoundedCornerShape(10.dp)).border(1.dp, Colours.LightMode.Border, RoundedCornerShape(10.dp)).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text("$seat", color = if (selected) Color.White else Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
    }
}

@Composable
private fun PreferenceChip(label: String) {
    Text(label, modifier = Modifier.border(1.dp, Colours.LightMode.Border, RoundedCornerShape(20.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(20.dp)).padding(horizontal = 15.dp, vertical = 3.dp), color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
}

@Composable
private fun InputField(value: String) {
    Row(modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.LightMode.Border, RoundedCornerShape(8.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(value, color = Colours.LightMode.InputText, fontSize = TextFormatting.InputField.size, fontWeight = TextFormatting.InputField.weight)
    }
}

@Composable
private fun ActionButton(label: String, filled: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(if (filled) Colours.LightMode.Primary else Colours.LightMode.Background2, RoundedCornerShape(20.dp)).border(1.dp, Colours.LightMode.Primary, RoundedCornerShape(20.dp)).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        Text(label, color = if (filled) Color.White else Colours.LightMode.Text, fontSize = TextFormatting.Button1.size, fontWeight = if (filled) TextFormatting.Button1.weight else TextFormatting.Button2.weight)
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    Box(modifier = Modifier.size(40.dp).border(1.dp, Color.White, CircleShape).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        Text("‹", color = Color.White, fontSize = 34.sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun PostRideBottomBar(onHome: () -> Unit, onSearch: () -> Unit, onAddRide: () -> Unit, onMyRides: () -> Unit, onProfile: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().navigationBarsPadding().border(1.dp, Colours.Accent).background(Colours.LightMode.Background1).padding(top = 7.dp, bottom = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        BottomNavItem({ homeIcon() }, "Home", onHome)
        BottomNavItem({ pinIcon(theme = Theme.Light) }, "Search", onSearch)
        Box(modifier = Modifier.size(50.dp).background(Colours.LightMode.Primary, CircleShape).clickable(onClick = onAddRide), contentAlignment = Alignment.Center) {
            Text("+", color = Colours.LightMode.Background1, fontSize = 36.sp)
        }
        BottomNavItem({ carIcon() }, "My Rides", onMyRides)
        BottomNavItem({ profileIcon() }, "Profile", onProfile)
    }
}

@Composable
private fun BottomNavItem(icon: @Composable () -> Unit, label: String, onClick: () -> Unit) {
    Column(modifier = Modifier.width(50.dp).clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Box(Modifier.height(24.dp), contentAlignment = Alignment.Center) { icon() }
        Text(label, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight, textAlign = TextAlign.Center)
    }
}