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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun JoinRidePage(
	ride: Ride? = null,
	modifier: Modifier = Modifier,
	onPay: () -> Unit = {},
	onCancel: () -> Unit = {},
) {
	val routeStart = ride?.startCity ?: "<startC>"
	val routeEnd = ride?.endCity ?: "<endC>"
	val date = ride?.formatDate() ?: "<date>"
	val departure = ride?.formatTime() ?: "HH:mm"
	val seatsLeft = ride?.getNoOfFreeSeats()?.toString() ?: "2"
	val breakdown = ride?.getCostBreakdown() ?: listOf("£36", "£12", "+ £1.20", "≈£13.20")

	Box(
		modifier = modifier
			.fillMaxSize()
			.background(Colours.LightMode.Background1),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(start = 30.dp, top = 100.dp, end = 30.dp, bottom = 80.dp),
			verticalArrangement = Arrangement.spacedBy(35.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			JoinRideTitle()
			JoinRideInfoBlock(routeStart, routeEnd, date, departure, seatsLeft)
			JoinRideCostBreakdown(breakdown)
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(5.dp),
			) {
				JoinRideActionButton("Pay with Stripe", filled = true, onClick = onPay)
				JoinRideActionButton("Cancel", filled = false, onClick = onCancel)
			}
		}

		Box(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.navigationBarsPadding()
				.padding(bottom = 8.dp)
				.width(134.dp)
				.height(5.dp)
				.background(Colours.LightMode.Primary, RoundedCornerShape(100.dp)),
		)
	}
}

@Composable
private fun JoinRideTitle() {
	Column(
		modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Text(
			"Join Ride",
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Heading1.size,
			fontWeight = TextFormatting.Heading1.weight,
			textAlign = TextAlign.Center,
		)
		Text(
			"Your seat is only one step away!",
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Text1.size,
			fontWeight = TextFormatting.Text1.weight,
			textAlign = TextAlign.Center,
		)
		Text(
			"Pay now to reserve your seat\nand lock in your ride.",
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Text1.size,
			fontWeight = TextFormatting.Text1.weight,
			textAlign = TextAlign.Center,
		)
	}
}

@Composable
private fun JoinRideInfoBlock(
	startCity: String,
	endCity: String,
	date: String,
	departure: String,
	seatsLeft: String,
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp))
			.background(Colours.LightMode.Background2, RoundedCornerShape(15.dp))
			.padding(horizontal = 15.dp, vertical = 10.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(15.dp),
	) {
		Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
			JoinRideRouteText(startCity)
			Text("-->", color = Colours.Accent, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			JoinRideRouteText(endCity)
		}
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
			JoinRideInfoBox(date, "date", Modifier.weight(1f))
			JoinRideInfoBox(departure, "departure", Modifier.weight(1f))
			JoinRideInfoBox(seatsLeft, "seats available", Modifier.weight(1f))
		}
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			JoinRideFilter("<filter>")
			JoinRideFilter("<filter>")
		}
		JoinRideFilter("<filter>")
	}
}

@Composable
private fun JoinRideRouteText(value: String) {
	Text(value, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
}

@Composable
private fun JoinRideInfoBox(value: String, label: String, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier.background(Colours.LightMode.Secondary, RoundedCornerShape(5.dp)).padding(horizontal = 10.dp, vertical = 5.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text(value, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Text(label, color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, textAlign = TextAlign.Center)
	}
}

@Composable
private fun JoinRideFilter(label: String) {
	Text(
		label,
		modifier = Modifier.background(Colours.LightMode.Secondary, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 3.dp),
		color = Colours.LightMode.Text,
		fontSize = TextFormatting.SmallText1.size,
		fontWeight = TextFormatting.SmallText1.weight,
	)
}

@Composable
private fun JoinRideCostBreakdown(breakdown: List<String>) {
	Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text("Cost breakdown", color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Column(
			modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(20.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
			verticalArrangement = Arrangement.spacedBy(5.dp),
		) {
			JoinRideCostRow("Fuel cost (125 mi × 0 mi/gal)", breakdown.getOrElse(0) { "£36" })
			JoinRideCostRow("Split between 1 passengers", breakdown.getOrElse(1) { "£12" })
			JoinRideCostRow("Carma fee (10%)", breakdown.getOrElse(2) { "+ £1.20" })
			Box(Modifier.fillMaxWidth().height(1.dp).background(Colours.Accent))
			JoinRideCostRow("Your total", breakdown.getOrElse(3) { "≈£13.20" }, total = true)
		}
	}
}

@Composable
private fun JoinRideCostRow(label: String, value: String, total: Boolean = false) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
		Text(label, color = Colours.LightMode.Text, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
		Text(value, color = Colours.LightMode.Text, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
	}
}

@Composable
private fun JoinRideActionButton(label: String, filled: Boolean, onClick: () -> Unit) {
	val background = if (filled) Colours.DarkMode.Background1 else Colours.LightMode.Background2
	val border = if (filled) background else Colours.LightMode.Primary
	val text = if (filled) Colours.DarkMode.Text else Colours.LightMode.Text

	Box(
		modifier = Modifier.fillMaxWidth().border(1.dp, border, RoundedCornerShape(20.dp)).background(background, RoundedCornerShape(20.dp)).clickable(onClick = onClick).padding(horizontal = 15.dp, vertical = 10.dp),
		contentAlignment = Alignment.Center,
	) {
		Text(label, color = text, fontSize = TextFormatting.Button1.size, fontWeight = FontWeight.Bold)
	}
}
