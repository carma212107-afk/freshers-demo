import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx./'compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Remove
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
fun SearchPage(
	onBack: () -> Unit = {},
	onContinue: () -> Unit = {},
	onDiscard: () -> Unit = {},
	modifier: Modifier = Modifier,
) {
	var femaleOnly by remember { mutableStateOf(true) }
	var quietCar by remember { mutableStateOf(0) }
	var petsWelcome by remember { mutableStateOf(false) }
	var smokingAllowed by remember { mutableStateOf(false) }
	var frontSeatDesired by remember { mutableStateOf(false) }
	var extraLuggage by remember { mutableStateOf(false) }

	Column(
		modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1)
			.verticalScroll(rememberScrollState()).padding(horizontal = 30.dp, vertical = 60.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
			BackButton(onClick = onBack)
			Column {
				Text("Search Preferences", color = Colours.LightModeText, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
				Text("Edit your default search filters below", color = Colours.LightModeText, fontSize = TextFormatting.Text1.size, fontWeight = TextFormatting.Text1.weight)
			}
		}
		Spacer(Modifier.height(22.dp))
		Text("Filter options", color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		PreferenceCard("Female-only ride", "Only rides with all-female cars") {
			PreferenceSwitch(femaleOnly) { femaleOnly = it }
		}
		PreferenceCard("Quiet / Chatty ride", "Pick between peace and quiet,\nor music and a good chat") {
			QuietCarSelector(quietCar) { quietCar = it }
		}
		PreferenceCard("Pets welcome", "Bring your furry little friends along for\nthe ride") {
			PreferenceSwitch(petsWelcome) { petsWelcome = it }
		}
		PreferenceCard("Smoking or vaping allowed", "description") {
			PreferenceSwitch(smokingAllowed) { smokingAllowed = it }
		}
		PreferenceCard("Front seat desired", "For those with travel sickness\n(don't forget your tablets)") {
			PreferenceSwitch(frontSeatDesired) { frontSeatDesired = it }
		}
		PreferenceCard("Extra luggage", "Got more than just a carry-on?\nNeed space for a suitcase or two?") {
			PreferenceSwitch(extraLuggage) { extraLuggage = it }
		}
		Spacer(Modifier.height(12.dp))
		Button(
			onClick = onContinue,
			modifier = Modifier.fillMaxWidth().height(42.dp),
			shape = RoundedCornerShape(20.dp),
			colors = ButtonDefaults.buttonColors(containerColor = Colours.DarkModeBackground),
		) { Text("Save preferences", color = Colours.DarkModeText, fontSize = TextFormatting.Button1.size, fontWeight = TextFormatting.Button1.weight) }
		Button(
			onClick = onDiscard,
			modifier = Modifier.fillMaxWidth().height(42.dp),
			shape = RoundedCornerShape(20.dp),
			colors = ButtonDefaults.buttonColors(containerColor = Colours.LightModeBackground2),
		) { Text("Discard changes", color = Colours.LightModeText, fontSize = TextFormatting.Button2.size, fontWeight = TextFormatting.Button2.weight) }
	}
}

@Composable
private fun PreferenceCard(title: String, description: String, control: @Composable () -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightModeBorder, RoundedCornerShape(15.dp))
			.background(Colours.LightModeBackground2, RoundedCornerShape(15.dp)).padding(15.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Column(modifier = Modifier.weight(1f)) {
			Text(title, color = Colours.LightModeText, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
			Text(description, color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, lineHeight = 23.sp)
		}
		Spacer(Modifier.width(10.dp))
		control()
	}
}

@Composable
private fun PreferenceSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
	ToggleSwitch(switchedOn = checked, onClick = { onCheckedChange(!checked) })
}

@Composable
private fun QuietCarSelector(selected: Int, onSelected: (Int) -> Unit) {
	LevelSwitches(pref = selected, onSelected = onSelected)
}



@Composable
fun LevelSwitches(pref: QuietCarPreference = QuietCarPreference.None, onSelected: (Int) -> Unit = {}, modifier: Modifier = Modifier) {
	val background = when (pref) {
		Quiet -> Colours.RedBackground
		None -> Colours.LightModeSecondary
		Loud -> Colours.LightModePrimary
	}
	Row(
		modifier = modifier.background(background, RoundedCornerShape(20.dp)).padding(2.dp),
		horizontalArrangement = Arrangement.spacedBy(5.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		LevelOption(
			icon = if (pref == Quiet) Icons.Filled.Headphones else null,
			label = "N",
			selected = pref == Quiet,
			onClick = { onSelected(Quiet) },
		)
		LevelOption(
			icon = if (pref == None) Icons.Filled.Remove else null,
			label = "/",
			selected = pref == None,
			onClick = { onSelected(None) },
		)
		LevelOption(
			icon = if (pref == Loud) Icons.Filled.MusicNote else null,
			label = "Y",
			selected = pref == Loud,
			onClick = { onSelected(Loud) },
		)
	}
}

@Composable
private fun LevelOption(icon: androidx.compose.ui.graphics.vector.ImageVector?, label: String, selected: Boolean, onClick: () -> Unit) {
	Box(
		modifier = Modifier.size(20.dp).clickable(onClick = onClick)
			.background(if (selected) Colours.LightModeBackground1 else Color.Transparent, RoundedCornerShape(10.dp)),
		contentAlignment = Alignment.Center,
	) {
		if (icon != null) {
			Icon(icon, contentDescription = null, tint = Colours.LightModePrimary, modifier = Modifier.size(16.dp))
		} else {
			Text(label, color = Colours.LightModeBackground1, fontWeight = FontWeight.Bold)
		}
	}
}
