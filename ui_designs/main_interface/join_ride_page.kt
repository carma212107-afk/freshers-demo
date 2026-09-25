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
	ride: Ride,
	modifier: Modifier = Modifier,
	onPay: () -> Unit = {},
	onCancel: () -> Unit = {},
) {
	val routeStart = ride.startCity
	val routeEnd = ride.endCity
	val date = ride.formatDate()
	val departure = ride.formatTime()
	val seatsLeft = ride.getNoOfFreeSeats().toString()
	val costBreakdown = ride.getCostBreakdown()

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
			CostBreakdown(ride)
            ContinueButtons(continueLabel = "Pay with Stripe", backLabel = "Cancel", onContinue = onPay, onBack = onCancel)
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
			Text(startCity, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			Text("-->", color = Colours.Accent, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			Text(endCity, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
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
private fun JoinRideInfoBox(value: String, label: String, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier.background(Colours.LightMode.Secondary, RoundedCornerShape(5.dp)).padding(horizontal = 10.dp, vertical = 5.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text(value, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight, textAlign = TextAlign.Center)
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
