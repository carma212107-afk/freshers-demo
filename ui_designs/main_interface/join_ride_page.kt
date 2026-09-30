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
            ViewRideSummary(ride)
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