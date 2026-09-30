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

enum class ConfirmationPageStatus { Pending, Success, Failure, }
data class ConfirmationPage(
    val status: ConfirmationPageStatus,
    val title: String,
    val description1: String,
    val description2: String? = null,
    val viewSummary: Boolean = true,
    val extraContent: @Composable () -> Unit = {},
    val continueButtonLabel: String,
    val backButtonLabel: String,
    val onContinue: () -> Unit = {},
    val onBack: () -> Unit = {},
)
object ConfirmationPages {
  object JoinRide {
    val Pending = ConfirmationPage(
        status = ConfirmationPageStatus.Pending,
        title = "Join Ride",
        description1 = "Your seat is only one step away!",
        description2 = "Pay now to reserve your seat\nand lock in your ride.",
        extraContent = { CostBreakdown(ride) },
        continueButtonLabel = "Pay with Stripe",
        backButtonLabel = "Cancel",
        onContinue = { /* TODO: Implement Stripe payment */ },
        onBack = { /* TODO: Implement back navigation */ },
    )
    val Success = ConfirmationPage(
        status = ConfirmationPageStatus.Success,
        title = "Seat booked!",
        description1 = "Your seat in ${ride.driver.firstName}'s ride is now confirmed."
        description2 = "Contact the driver to decide a pick-up\npoint and organise any extra needs.",
        continueButtonLabel = "View ride",
        backButtonLabel = "Back to home",
        onContinue = { /* TODO: Implement view ride navigation */ },
        onBack = { /* TODO: Implement back to home navigation */ },
    )
  }
  object LeaveRide {
    val Pending = ConfirmationPage(
        status = ConfirmationPageStatus.Pending,
        title = "Change of plans",
        description1 = "You can leave ${ride.driver.firstName}'s ride and free up\nyour seat for another student.",
        description2 = "Any applicable refunds will be processed automatically.",
        continueButtonLabel = "Leave ride",
        backButtonLabel = "Back to ride",
        onContinue = { /* TODO: Implement leave ride functionality */ },
        onBack = { /* TODO: Implement back to ride navigation */ },
    )
    val Success = ConfirmationPage(
        status = ConfirmationPageStatus.Success,
        title = "Ride left!",
        description1 = "Your booking has been cancelled."
        description2 = "Your seat is now available\nfor another student",
        viewSummary = false,
        continueButtonLabel = "View ride",
        backButtonLabel = "Back to home",
        onContinue = { /* TODO: Implement view ride navigation */ },
        onBack = { /* TODO: Implement back to home navigation */ },
    )
  }
  object CancelRide {
    val Pending = ConfirmationPage(
        status = ConfirmationPageStatus.Pending,
        title = "Plans changed?",
        description1 = "Cancelling this ride will notify everyone\nwho booked a seat and remove the ride\nfrom Carma.",
        extraContent = { PassengerList(ride) },
        continueButtonLabel = "Cancel ride",
        backButtonLabel = "Back to ride",
        onContinue = { /* TODO: Implement cancel ride functionality */ },
        onBack = { /* TODO: Implement back to ride navigation */ },
    )
    val Success = ConfirmationPage(
        status = ConfirmationPageStatus.Success,
        title = "Ride cancelled!",
        description1 = "Your ride is no longer available and\nall passengers with bookings have been\nnotified.",
        viewSummary = false,
        continueButtonLabel = "View your rides",
        backButtonLabel = "Back to home",
        onContinue = { /* TODO: Implement view your rides navigation */ },
        onBack = { /* TODO: Implement back to home navigation */ },
    )
  }
  object PostRide {
    val Success = ConfirmationPage(
        status = ConfirmationPageStatus.Success,
        title = "Ride posted!",
        description1 = "Your ride is now live.",
        description2 = "Students on this route will be notified\nand can book a seat instantly.",
        continueButtonLabel = "View my listing",
        backButtonLabel = "Back to home",
        onContinue = { /* TODO: Implement view my listing navigation */ },
        onBack = { /* TODO: Implement back to home navigation */ },
    )
}


@Composable
fun ConfirmationPage(
	ride: Ride,
    page: ConfirmationPages,
	modifier: Modifier = Modifier,
) {
    val status = page.status
    val title = page.title
    val description1 = page.description1
    val description2 = if (page.description2 != null) page.description2 else null
    val extraContent = if (page.extraContent != null) page.extraContent else null
    val continueButtonLabel = page.continueButtonLabel
    val backButtonLabel = page.backButtonLabel
    val onContinue = page.onContinue
    val onBack = page.onBack

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
            if status == ConfirmationPageStatus.Success { ConfirmationCheckmark() }
			TitleSection(title, description1, description2)
            if page.viewSummary { ViewRideSummary(ride) }
			if extraContent != null { extraContent() }
            ContinueButtons(continueLabel = continueButtonLabel, backLabel = backButtonLabel, onContinue = onContinue, onBack = onBack)
		}
	}
}

@Composable
private fun TitleSection(title: String, description1: String, description2: String? = null) {
	Column(
		modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Text(
			title,
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Heading1.size,
			fontWeight = TextFormatting.Heading1.weight,
			textAlign = TextAlign.Center,
		)
		Text(
			description1,
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Text1.size,
			fontWeight = TextFormatting.Text1.weight,
			textAlign = TextAlign.Center,
		)
		if (description2 != null) {
			Text(
				description2,
				color = Colours.LightMode.Text,
				fontSize = TextFormatting.Text1.size,
				fontWeight = TextFormatting.Text1.weight,
				textAlign = TextAlign.Center,
		)
	}
}