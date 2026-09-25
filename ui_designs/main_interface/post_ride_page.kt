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
    editMode: Boolean = false,
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

    pageTitle = if (editMode) "Edit Ride" else "Post Ride"
    continueLabel = if (editMode) "Save changes" else "Post Ride"

    Column(
        modifier = modifier.fillMaxSize().background(Colours.LightMode.Background1),
    ) {        
        TopMenuBar(title = pageTitle, description = "Fill in your journey details below")

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 30.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            FormSection("Your route") {
                RideRoute(edit = true, ride = ride)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DateTimeField("Date", "<dd D Mon>", Modifier.weight(1f), date = true) // INPUT FIELD
                DateTimeField("Time", "<12hr time>", Modifier.weight(1f), date = false) // INPUT FIELD
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
                selectedCar = SelectorField("Select car") // INPUT FIELD (dropdown) returns car
            }

            FormSection("Available seats") {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    (1..selectedCar.noOfSeats).forEach { seat ->
                        SeatButton(seat, selected = seat == selectedSeats) { selectedSeats = seat }
                    }
                }
            }

            FormSection("Your preferences") {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        PreferenceChip("<filter>") // selectable filter buttons
                        PreferenceChip("<filter>")
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        PreferenceChip("<filter>")
                        PreferenceChip("<filter>")
                    }
                }
            }

            FormSection("Notes for passengers") {
                InputField("Notes") // INPUT FIELD
            }

            ContinueButtons(continueLabel = continueLabel, backLabel = "Discard", onContinue = onPostRide, onBack = onDiscard)
        }

        BottomNavigationBar()
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
private fun SelectorField(value: String) { // car dropdown menu
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
    val scheme = ColourScheme(
        background = if (selected) Colours.DarkMode.Background1 else Colours.LightMode.Background2
        border = if (selected) Colours.DarkMode.Background1 else Colours.LightMode.Border
        text = if (selected) Colours.DarkMode.Text else Colours.LightMode.Text
    )

    Box(
        modifier = Modifier.size(width = 30.dp, height = 25.dp).background(scheme.background, RoundedCornerShape(10.dp)).border(1.dp, scheme.border, RoundedCornerShape(10.dp)).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text("$seat", color = scheme.text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
    }
}

@Composable
private fun PreferenceChip(label: String) {
    val scheme = ColourScheme(
        background = if (selected) Colours.DarkMode.Background1 else Colours.LightMode.Background2
        border = if (selected) Colours.DarkMode.Background1 else Colours.LightMode.Border
        text = if (selected) Colours.DarkMode.Text else Colours.LightMode.Text
    )

    Text(label, modifier = Modifier.border(1.dp, Colours.LightMode.Border, RoundedCornerShape(20.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(20.dp)).padding(horizontal = 15.dp, vertical = 3.dp), color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
}

@Composable
private fun InputField(value: String) {
    Row(modifier = Modifier.fillMaxWidth().height(40.dp).border(1.dp, Colours.LightMode.Border, RoundedCornerShape(8.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(value, color = Colours.LightMode.InputText, fontSize = TextFormatting.InputField.size, fontWeight = TextFormatting.InputField.weight)
    }
}